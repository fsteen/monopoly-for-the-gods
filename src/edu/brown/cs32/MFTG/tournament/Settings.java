package edu.brown.cs32.MFTG.tournament;

public class Settings {
<<<<<<< HEAD
	public enum Turns {
		STAGGERED, BUNCHED
	}
	public enum WinningCondition {
		MOST_GAMES_WON
	}
	
	private final int NUM_GAMES_PER_ROUND;
	private final int NUM_ROUNDS;
	
	public Settings(int numGamesPerRound, int numRounds){
		NUM_GAMES_PER_ROUND = numGamesPerRound;
		NUM_ROUNDS = numRounds;
	}
=======
	public final int NUM_GAMES_PER_ROUND=10;
	public final int NUM_ROUNDS=10;
>>>>>>> branch 'master' of https://github.com/fsteen/monopoly-for-the-gods.git
	
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
