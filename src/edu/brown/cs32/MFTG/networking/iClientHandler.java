package edu.brown.cs32.MFTG.networking;

import java.util.List;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;

public interface iClientHandler {
	
	/**
	 * Gets the player associated with this object
	 * @return
	 * @throws ClientCommunicationException 
	 * @throws ClientLostException 
	 * @throws InvalidResponseException 
	 */
	public Player getPlayer() throws ClientCommunicationException, ClientLostException, InvalidResponseException;
	
	/**
	 * Sends an encoding of the players to the client and a request to play numGame games
	 * @param players
	 * @param numGames
	 * @return the GameData collected from playing the round of games
	 * @throws InvalidResponseException 
	 */
	public List<GameData> playGames(List<Player> players, List<Long> seeds) throws ClientCommunicationException,
																				   ClientLostException, 
																				   InvalidResponseException;
	/**
	 * Gives GameData to the iClientHandler to deal with 
	 * @param aggregatedData
	 */
	public void setGameData(GameData aggregatedData) throws ClientCommunicationException, ClientLostException;
	
}
