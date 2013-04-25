package edu.brown.cs32.MFTG.tournament;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.iClientHandler;

public class Tournament implements Runnable{
	
	List<iClientHandler> _clients;
	private Settings _settings;
	
	/**
	 * Creates a tournament for the specified number of players and games
	 * @param numPlayers
	 * @param numGames
	 */
	public Tournament(List<iClientHandler> clients, Settings settings){
		_clients = clients;
		_settings = settings;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < _settings.getNumRounds(); i++){
			List<Player> players = getNewPlayers();
			List<List<GameData>> data = playRoundOfGames(players, null);
			displayEndOfRoundData();
		}
		displayEndOfGameData();
	}
	
	/**
	 * Get rid of the old Players and get new Players from each client
	 */
	private List<Player> getNewPlayers(){
		List<Player> players = new ArrayList<>();
		for(iClientHandler c : _clients){
			players.add(c.getPlayer());
		}
		return players;
	}
	
	/**
	 * Splits the games between each of the clients and has them play
	 * rounds to next lowest multiple of # of clients
	 */
//	private List<GameData> playRoundOfGames(){
//		List<GameData> roundData = new ArrayList<>();
//		int gamesPerModule = (int) Math.ceil(_settings.getNumGamesPerRound()/_clients.size());
//		
//		playGames();
////		for(iClientHandler c : _clients){
//////			roundData.addAll(c.playGames(_players, _settings, gamesPerModule));
////		}
//		return roundData;
//	}
	
	//return all of the gameData
	private List<List<GameData>> playRoundOfGames(List<Player> players, List<List<Long>> seeds){
		//Alex will implement
		return null;
	}

	private void displayEndOfRoundData(){
		//TODO implement
	}
	
	private void displayEndOfGameData(){
		//TODO implement
	}
	
	public void getPlayerConnections(){
		//listen to connect with players
	}
}