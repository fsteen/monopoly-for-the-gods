package edu.brown.cs32.MFTG.tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.PlayGamesCallable;
import edu.brown.cs32.MFTG.networking.getPlayerCallable;
import edu.brown.cs32.MFTG.networking.iClientHandler;

public class Tournament implements Runnable{
	
	private List<iClientHandler> _clients;
	private Settings _settings;
	private ExecutorService _executor; 
	/**
	 * Creates a tournament for the specified number of players and games
	 * @param numPlayers
	 * @param numGames
	 */
	public Tournament(List<iClientHandler> clients, Settings settings){
		_clients = clients;
		_settings = settings;
		_executor = Executors.newFixedThreadPool(_clients.size());
	}
	
	@Override
	public void run() {
		int gamesPerModule = (int)Math.ceil(_settings.getNumGamesPerRound()/_clients.size());
		List<Player> players;
		List<List<GameData>> data;
		List<Integer> confirmationIndices;
		
		for(int i = 0; i < _settings.getNumRounds(); i++){
			players = getNewPlayers();
			confirmationIndices = DataProcessor.generateConfirmationIndices(gamesPerModule, .1);
			data = playRoundOfGames(players, DataProcessor.generateSeeds(gamesPerModule, players.size(), confirmationIndices));
			
			if(DataProcessor.isCorrupted(data, confirmationIndices)){
				System.out.println("SOMEONE IS CHEATING!!!!!");
			}
//			displayEndOfRoundData(data);
		}
		displayEndOfGameData();
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

	private void displayEndOfRoundData(GameData aggregatedData){
		//TODO implement
		for(iClientHandler c : _clients){
			c.setGameData(aggregatedData);
		}
	}
	
	private void displayEndOfGameData(){
		//TODO implement
	}
	
	public void getPlayerConnections(){
		//listen to connect with players
	}
}