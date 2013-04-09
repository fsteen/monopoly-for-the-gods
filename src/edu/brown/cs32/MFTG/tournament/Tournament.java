package edu.brown.cs32.MFTG.tournament;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.NetworkingInterfaceFrontend;

//there is no threading in tournament, tournament simply interfaces with the networking interface
public class Tournament {
	
	List<NetworkingInterfaceFrontend> _playerNetworks;
	List<Player> _playerPreferences;
	private int _numGames;
	
	/**
	 * Creates a tournament for the specified number of players and games
	 * @param numPlayers
	 * @param numGames
	 */
	public Tournament(int numPlayers, int numGames){
		_playerNetworks = new ArrayList<>();
		_numGames = numGames;
		
		for(int i = 0; i < numPlayers; i++){
			//Instantiate network, add network to list
			_playerNetworks.add(new Client());
		}
	}
	
	public void play(){
		//get heuristics
		for(NetworkingInterfaceFrontend n : _playerNetworks){
			_playerPreferences.add(n.getPlayerHeuristics()); //collect all player info
		}
		
		//rounds number of games played to next multiple of _playerNetworks.size()
		int gamesPerNetwork = (int) Math.ceil(_numGames/_playerNetworks.size());
		for(NetworkingInterfaceFrontend n : _playerNetworks){
			n.playGames(_playerPreferences, gamesPerNetwork);
		}
	}

}
