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
	 */
	public void playGames(List<Player> players, Settings settings, int numGames);
	
	/**
	 * Returns GameData to the requesting object
	 * @return
	 */
	public List<GameData> getGameData();
	
	/**
	 * Gives GameData to the iClientHandler to deal with 
	 * @param combinedData
	 */
	public void setGameData(List<GameData> combinedData); //TODO figure out what this method should take
	
}