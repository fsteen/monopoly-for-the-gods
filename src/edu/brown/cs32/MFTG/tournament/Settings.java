package edu.brown.cs32.MFTG.tournament;

import java.util.Timer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Settings {

	public enum Turns {
		STAGGERED, BUNCHED
	}
	public enum WinningCondition {
		MOST_SETS_WON, LAST_SET_WON, MOST_MONEY_MIDWAY
	}
	
	public final int NUM_GAMES_PER_ROUND;
	public final int NUM_ROUNDS;
	public final Turns TURN_FLOW;
	public final WinningCondition WIN_TYPE;
	public final Timer TIMING; //not sure if this is best
	public final int MAX_NUM_TURNS;
	public final boolean DOUBLE_ON_GO;
	public final boolean FREE_PARKING;
	public final boolean AUCTIONS;
	
	//TODO no longer Json compatible ... ...
	
	@JsonCreator
	public Settings(@JsonProperty("numGamesPerRound") int numGamesPerRound,
			@JsonProperty("numRounds") int numRounds,
			int maxNumTurns,
			boolean doubleOnGo,
			boolean freeParking,
			boolean auctions,
			Turns turnFlow, WinningCondition winType, Timer timing){
		NUM_GAMES_PER_ROUND = numGamesPerRound;
		NUM_ROUNDS = numRounds;
		TURN_FLOW = turnFlow;
		WIN_TYPE = winType;
		TIMING = timing;
		MAX_NUM_TURNS = maxNumTurns;
		DOUBLE_ON_GO = doubleOnGo;
		FREE_PARKING = freeParking;
		AUCTIONS = auctions;
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
