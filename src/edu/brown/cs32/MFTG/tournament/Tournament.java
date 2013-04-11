package edu.brown.cs32.MFTG.tournament;

import java.util.List;

import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.iClientHandler;

//there is no threading in tournament, tournament simply interfaces with the networking interface
public class Tournament implements Runnable{
	
	List<iClientHandler> _clients;
	List<Player> _players;
	private Settings _settings;
	
	/**
	 * Creates a tournament for the specified number of players and games
	 * @param numPlayers
	 * @param numGames
	 */
	public Tournament(List<Player> players, List<iClientHandler> clients, Settings settings){
		_clients = clients;
		_settings = settings;
		_players = players;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < _settings.getNumRounds(); i++){
			getPlayers();
			playRoundOfGames();
			displayEndOfRoundData();
		}
	}
	
	/**
	 * Get rid of the old Players and get new Players from each client
	 */
	private void getPlayers(){
		_players.clear();
		for(iClientHandler c : _clients){
			_players.add(c.getPlayer());
		}
	}
	
	/**
	 * Splits the games between each of the clients and has them play
	 */
	private void playRoundOfGames(){
		//rounds number of games played to next multiple of _playerNetworks.size()
		int gamesPerNetwork = (int) Math.ceil(_settings.getNumGamesPerRound()/_clients.size());
		for(iClientHandler c : _clients){
			c.playGames(_players, _settings, gamesPerNetwork);
		}
	}

	private void displayEndOfRoundData(){
		//TODO implement
		//collect end of round data and send it back to the clients for display 
	}
}
