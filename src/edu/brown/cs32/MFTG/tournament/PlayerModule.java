package edu.brown.cs32.MFTG.tournament;

import java.util.List;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.NetworkingInterfaceFrontend;

public class PlayerModule implements iClientHandler{
	
//	GUI gui;
	
	/**
	 * Gets the player associated with this object
	 * @return
	 */
	public Player getPlayer(){
		//TODO calls method in the GUI to get player
		return null;
	}
	
	/**
	 * Sends an encoding of the players to the client and a request to play numGame games
	 * @param players
	 * @param numGames
	 */
	public void playGames(List<Player> players, Settings settings, int numGames){
		//TODO implement
		//set up a data structure for collecting game data
		//create a thread pool for playing the games
		//
	}
	
	public List<GameData> getGameData(){
		//TODO
		return null;
	}

	@Override
	public void sendGameData(List<GameData> combinedData) {
		// TODO Auto-generated method stub
		
	}
	

}
