package edu.brown.cs32.MFTG.tournament;

import java.util.List;

import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.NetworkingInterfaceFrontend;

public class Client implements NetworkingInterfaceFrontend{
	
	@Override
	public void playGames(List<Player> players, int numGames){
		//TODO implement
		//set up a data structure for collecting game data
		//create a thread pool for playing the games
		//
	}
	
	public void displayData(){
		//TODO implement
	}

	@Override
	public Player getPlayerHeuristics() {
		// TODO Auto-generated method stub
		return null;
	}

}
