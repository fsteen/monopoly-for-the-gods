package edu.brown.cs32.MFTG.networking;

import java.util.List;

import edu.brown.cs32.MFTG.monopoly.Player;

public interface NetworkingInterfaceFrontend {

	/**
	 * Gets the playerHeuristics associated with this object
	 * @return
	 */
	public Player getPlayerHeuristics();
	
	/**
	 * Plays numGames between players
	 * @param players
	 * @param numGames
	 */
	public void playGames(List<Player> players, int numGames);
	
}
