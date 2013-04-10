package edu.brown.cs32.MFTG.tournament;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.NetworkingInterfaceFrontend;

//there is no threading in tournament, tournament simply interfaces with the networking interface
public class Tournament {
	
	List<iClientHandler> _clients;
	List<Player> _players;
	private int _numGames;
	private Settings settings;
	
	/**
	 * Creates a tournament for the specified number of players and games
	 * @param numPlayers
	 * @param numGames
	 */
	public Tournament(List<Player> players, List<iClientHandler> clients, Settings settings){
		_clients = clients;
		_numGames = settings.getNumGames();
		_players = players;
	}
	
	public void play(){		
		//rounds number of games played to next multiple of _playerNetworks.size()
		int gamesPerNetwork = (int) Math.ceil(_numGames/_clients.size());
		for(iClientHandler c : _clients){
			c.playGames(_players, settings, gamesPerNetwork);
		}
	}

}
