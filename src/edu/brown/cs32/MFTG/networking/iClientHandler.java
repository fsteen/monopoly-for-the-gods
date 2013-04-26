package edu.brown.cs32.MFTG.networking;

import java.util.List;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.tournament.Settings;

public interface iClientHandler {
	
	/**
	 * Gets the player associated with this object
	 * @return
	 */
	public Player getPlayer();
	
	/**
	 * Sends an encoding of the players to the client and a request to play numGame games
	 * @param players
	 * @param numGames
	 * @return the GameData collected from playing the round of games
	 */
	public List<GameData> playGames(List<Player> players, List<Long> seeds);
	
//	/**
//	 * Returns GameData to the requesting object
//	 * @return
//	 */
//	public List<GameData> getGameData();
//	
	/**
	 * Gives GameData to the iClientHandler to deal with 
	 * @param aggregatedData
	 */
	public void setGameData(GameData aggregatedData);
	
}