package edu.brown.cs32.MFTG.tournament;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.ClientCommunicationException;
import edu.brown.cs32.MFTG.networking.ClientHandler;
import edu.brown.cs32.MFTG.networking.ClientLostException;
import edu.brown.cs32.MFTG.networking.InvalidResponseException;
import edu.brown.cs32.MFTG.networking.PlayGamesCallable;
import edu.brown.cs32.MFTG.networking.getPlayerCallable;
import edu.brown.cs32.MFTG.networking.iClientHandler;
import edu.brown.cs32.MFTG.tournament.data.DataProcessor;
import edu.brown.cs32.MFTG.tournament.data.GameDataReport;

public class Tournament implements Runnable{
	private final int _numPlayers;
	
	public static final double CONFIRMATION_PERCENTAGE = 0.1; //confirm 10% of games
	public static final int NUM_DATA_POINTS = 50;
	private List<iClientHandler> _clients;
	private ServerSocket _socket;
	private Settings _settings;
	private ExecutorService _executor; 
	/**
	 * Creates a tournament for the specified number of players and games
	 * @param numPlayers
	 * @param numGames
	 * @throws IOException 
	 */
	public Tournament(int numPlayers, Settings settings, int port) throws IOException{
		if (port <= 1024){
			throw new IllegalArgumentException("Ports under 1024 are reserved!");
		}
		
		_numPlayers = numPlayers;
		_socket = new ServerSocket(port);
		_clients = new ArrayList<>();
		_settings = settings;
		_executor = Executors.newFixedThreadPool(numPlayers);
	}
	
	public void run() {
		int gamesPerModule = (int)Math.ceil(_settings.getNumGamesPerRound()/_clients.size());
		List<Player> players;
		List<List<GameData>> data;
		List<Integer> confirmationIndices;
		
		for(int i = 0; i < _settings.getNumRounds(); i++){
			
			// request a Player from each client
			try {
				players = getNewPlayers();
			} catch (InvalidResponseException e1) {
				// TODO handle
				return;
			} catch (ClientLostException e1) {
				// TODO handle
				return;
			} catch (ClientCommunicationException e1) {
				// TODO handle
				return;
			}
			
			// generate which seeds will be used for integrity validation
			confirmationIndices = DataProcessor.generateConfirmationIndices(gamesPerModule, CONFIRMATION_PERCENTAGE);
			
			// play a round of games
			try {
				data = playRoundOfGames(players, DataProcessor.generateSeeds(gamesPerModule, players.size(), confirmationIndices));
			} catch (ClientLostException e1) {
				// TODO handle
				return;
			} catch (InvalidResponseException e1) {
				// TODO handle
				return;
			} catch (ClientCommunicationException e1) {
				// TODO handle
				return;
			}
			
			// make sure nobody cheated
			if(DataProcessor.isCorrupted(data, confirmationIndices)){
				System.out.println("WOOHOO ... SOMEONE IS CHEATING!!!!!"); //TODO change this
			}
			
			// attempt to send the data about the game to the clients
			try {
				List<GameData> dataToSend = new ArrayList<>();
				for(List<GameData> d : data){
					dataToSend.addAll(d);
				}
				sendEndOfRoundData(DataProcessor.aggregate(dataToSend, NUM_DATA_POINTS));
			} catch (ClientCommunicationException e) {
				// TODO figure out how this will be handled
			}
		}
		sendEndOfGameData();
	}
	
	/**
	 * Takes in a throwable that was received from a future and rethrows it as the appropriate method
	 * @param cause the throwable to convert to a thrown Exception
	 * @throws ClientLostException
	 * @throws InvalidResponseException
	 * @throws ClientCommunicationException
	 */
	private void rethrowExecutionException(Throwable cause) throws ClientLostException, InvalidResponseException, ClientCommunicationException{
		if (cause instanceof ClientLostException){
			throw new ClientLostException();
		} else if (cause instanceof InvalidResponseException){
			throw (InvalidResponseException) cause;
		} else {
			throw new ClientCommunicationException();
		}
	}
	
	/**
	 * Get rid of the old Players and get new Players from each client
	 * @throws InvalidResponseException 
	 * @throws ClientLostException 
	 * @throws ClientCommunicationException 
	 */
	private List<Player> getNewPlayers() throws InvalidResponseException, ClientLostException, ClientCommunicationException{
		List<Future<Player>> playerFutures = new ArrayList<>();
		List<Player> players = new ArrayList<>();
		
		for(iClientHandler c : _clients){
			Callable<Player> worker = new getPlayerCallable(c);
			Future<Player> future = _executor.submit(worker);
			playerFutures.add(future);
		}
		
		for (Future<Player> f : playerFutures){
			try {
				players.add(f.get());
			} catch (InterruptedException e) {
				// TODO handle this
			} catch (ExecutionException e) {
				rethrowExecutionException(e.getCause());
			}
		}
		
		return players;
	}
	
	/**
	 * Splits the games between each of the clients and has them play
	 * rounds to next lowest multiple of # of clients
	 * @param players the players who will be playing the game
	 * @param seeds the seeds which will be used to play the games
	 * @return the GameData from the games played
	 * @throws ClientCommunicationException 
	 * @throws InvalidResponseException 
	 * @throws ClientLostException 
	 */
	private List<List<GameData>> playRoundOfGames(List<Player> players, List<List<Long>> seeds) throws ClientLostException, InvalidResponseException, ClientCommunicationException{
		List<Future<List<GameData>>> gameDataFutures = new ArrayList<>();
		List<List<GameData>> gameData = new ArrayList<>();
		
		if (_clients.size() != seeds.size()){
			// throw an error or something
		}
		
		for (int i = 0; i < _clients.size(); i++){
			Callable<List<GameData>> worker = new PlayGamesCallable(_clients.get(i), players, seeds.get(i));
			Future<List<GameData>> future = _executor.submit(worker);
			gameDataFutures.add(future);
		}
		
		for (Future<List<GameData>> f : gameDataFutures){
			try {
				gameData.add(f.get());
			} catch (InterruptedException e) {
				// TODO handle this
			} catch (ExecutionException e) {
				rethrowExecutionException(e.getCause());
			}
		}
		
		return gameData;
	}

	private void sendEndOfRoundData(GameDataReport aggregatedData) throws ClientCommunicationException {
		for(iClientHandler c : _clients){
			c.setGameData(aggregatedData);
		}
	}
	
	private void sendEndOfGameData(){
		//TODO implement
	}

	/**
	 * Adds _numPlayers connections to the client handler list. This method does not return
	 * until after numPlayers connections are made over the socket
	 * @param numPlayers
	 * @throws IOException
	 */
	public void getPlayerConnections() throws IOException{
		int connectionsMade = 0;
		
		while(connectionsMade <= _numPlayers){
			Socket clientConnection = _socket.accept();
			iClientHandler cHandler = new ClientHandler(clientConnection);
			_clients.add(cHandler);
			connectionsMade++;
		}
	}
}
