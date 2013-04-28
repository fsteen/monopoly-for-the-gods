package edu.brown.cs32.MFTG.tournament;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Settings {

	public enum Turns {
		STAGGERED, BUNCHED
	}
	public enum WinningCondition {
		MOST_GAMES_WON
	}
	
	public final int NUM_GAMES_PER_ROUND;
	public final int NUM_ROUNDS;
	
	@JsonCreator
	public Settings(@JsonProperty("numGamesPerRound") int numGamesPerRound, @JsonProperty("numRounds") int numRounds){
		NUM_GAMES_PER_ROUND = numGamesPerRound;
		NUM_ROUNDS = numRounds;
	}

	
	/**
	 * Returns the number of games to be played per round
	 * (players choose new heuristics every round)
	 * @return
	 */
	@JsonIgnore
	public int getNumGamesPerRound(){
		return NUM_GAMES_PER_ROUND;
	}
	
	/**
	 * Stores the number of rounds to be played
	 * @return
	 */
	@JsonIgnore
	public int getNumRounds(){
		return NUM_ROUNDS;
	}
	
	@Override
	public boolean equals(Object o){
		if (o == null || !(o instanceof Settings))
			return false;
		
		Settings that = (Settings) o;
		
		return (this.NUM_GAMES_PER_ROUND == that.getNumGamesPerRound() && this.NUM_ROUNDS == that.getNumRounds());
	}
}
