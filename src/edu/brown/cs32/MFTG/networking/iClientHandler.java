package edu.brown.cs32.MFTG.networking;

import java.util.List;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.tournament.data.GameDataReport;

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
	public void setGameData(GameDataReport aggregatedData) throws ClientCommunicationException;
	
	/**
	 * Requests that an error message is displayed
	 * @param errorMessage the message to display
	 * @throws ClientCommunicationException
	 */
	public void sendErrorMessage(String errorMessage);
	
	/**
	 * Sends the id to the client
	 */
	public void sendID() throws ClientCommunicationException;
}
