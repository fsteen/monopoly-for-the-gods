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
import edu.brown.cs32.MFTG.networking.ClientHandler;
import edu.brown.cs32.MFTG.networking.PlayGamesCallable;
import edu.brown.cs32.MFTG.networking.getPlayerCallable;
import edu.brown.cs32.MFTG.networking.iClientHandler;

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
			players = getNewPlayers();
			confirmationIndices = DataProcessor.generateConfirmationIndices(gamesPerModule, CONFIRMATION_PERCENTAGE);
			data = playRoundOfGames(players, DataProcessor.generateSeeds(gamesPerModule, players.size(), confirmationIndices));
			
			if(DataProcessor.isCorrupted(data, confirmationIndices)){
				System.out.println("WOOHOO ... SOMEONE IS CHEATING!!!!!"); //TODO change this
			}
			sendEndOfRoundData(DataProcessor.aggregate(data, NUM_DATA_POINTS));
		}
		sendEndOfGameData();
	}
	
	/**
	 * Get rid of the old Players and get new Players from each client
	 */
	private List<Player> getNewPlayers(){
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
				// TODO handle this
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
	 */
	private List<List<GameData>> playRoundOfGames(List<Player> players, List<List<Long>> seeds){
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
				// TODO handle this
			}
		}
		
		return gameData;
	}

	private void sendEndOfRoundData(GameData aggregatedData){
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
