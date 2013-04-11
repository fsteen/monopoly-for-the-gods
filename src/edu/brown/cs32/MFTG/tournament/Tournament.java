package edu.brown.cs32.MFTG.tournament;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.iClientHandler;

public class Tournament implements Runnable{
	
	List<iClientHandler> _clients;
	List<Player> _players;
	private Settings _settings;
	List<GameData> _roundData;
	
	/**
	 * Creates a tournament for the specified number of players and games
	 * @param numPlayers
	 * @param numGames
	 */
	public Tournament(List<Player> players, List<iClientHandler> clients, Settings settings){
		_clients = clients;
		_settings = settings;
		_players = players;
		_roundData = new ArrayList<>();
	}
	
	@Override
	public void run() {
		for(int i = 0; i < _settings.getNumRounds(); i++){
			_roundData.clear();
			getNewPlayers();
			playRoundOfGames();
			displayEndOfRoundData();
		}
		displayEndOfGameData();
	}
	
	/**
	 * Get rid of the old Players and get new Players from each client
	 */
	private void getNewPlayers(){
		_players.clear();
		for(iClientHandler c : _clients){
			_players.add(c.getPlayer());
		}
	}
	
	/**
	 * Splits the games between each of the clients and has them play
	 * rounds to next lowest multiple of # of clients
	 */
	private void playRoundOfGames(){
		int gamesPerNetwork = (int) Math.ceil(_settings.getNumGamesPerRound()/_clients.size());
		for(iClientHandler c : _clients){
			_roundData.addAll(c.playGames(_players, _settings, gamesPerNetwork));
		}
	}

	private void displayEndOfRoundData(){
		//TODO implement
	}
	
	private void displayEndOfGameData(){
		//TODO implement
	}
}