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
	
	public final int NUM_GAMES_PER_ROUND,NUM_ROUNDS,BEGINNING_TIMEOUT,DURING_TIMEOUT,FREE_PARKING;
	public final boolean DOUBLE_ON_GO, AUCTIONS;
	public final Turns TURN_FLOW;
	public final WinningCondition WIN_TYPE;
	
	//TODO no longer Json compatible ... ...
	
	@JsonCreator
	public Settings(@JsonProperty("numGamesPerRound") int numGamesPerRound,
			@JsonProperty("numRounds") int numRounds,
			boolean doubleOnGo,
			int freeParking,
			boolean auctions,
			Turns turnFlow, WinningCondition winType, int beginningTimeout, int duringTimeout){
		NUM_GAMES_PER_ROUND = numGamesPerRound;
		NUM_ROUNDS = numRounds;
		TURN_FLOW = turnFlow;
		WIN_TYPE = winType;
		BEGINNING_TIMEOUT = beginningTimeout;
		DURING_TIMEOUT = duringTimeout;
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