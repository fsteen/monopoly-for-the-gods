package edu.brown.cs32.MFTG.tournament;

public class Settings {
	public final int NUM_GAMES_PER_ROUND=10;
	public final int NUM_ROUNDS=10;
	
	/**
	 * Returns the number of games to be played per round
	 * (players choose new heuristics every round)
	 * @return
	 */
	public int getNumGamesPerRound(){
		return NUM_GAMES_PER_ROUND;
	}
	
	/**
	 * Stores the number of rounds to be played
	 * @return
	 */
	public int getNumRounds(){
		return NUM_ROUNDS;
	}
}
