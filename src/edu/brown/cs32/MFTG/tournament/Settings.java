package edu.brown.cs32.MFTG.tournament;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Settings {

	public enum WinningCondition {
		MOST_SETS_WON, //win a round of games by winning the most games in that round, win the entire game by winning the most rounds
		LAST_SET_WON, //win the entire game by winning the most games in the last round
		MOST_MONEY //win a round of games by having the greatest average wealth at the last timestamp, with the entire game by winning the most rounds
	}
	
	public final int gamesPerRound, numRounds, beginningTimeout, duringTimeout, freeParking; //all implemented
	public final boolean doubleOnGo, auctions; //all implemented
	public final WinningCondition winType; //implemented
	
	@JsonCreator
	public Settings(@JsonProperty("numGamesPerRound") int gamesPerRound,
					@JsonProperty("numRounds") int numRounds,
					@JsonProperty("doubleOnGo") boolean doubleOnGo,
					@JsonProperty("freeParking") int freeParking,
					@JsonProperty("auctions") boolean auctions,
					@JsonProperty("winType") WinningCondition winType, 
					@JsonProperty("beginningTimeout") int beginningTimeout, 
					@JsonProperty("duringTimeout") int duringTimeout){
		
		this.gamesPerRound = gamesPerRound;
		this.numRounds = numRounds;
		this.winType = winType;
		this.beginningTimeout = beginningTimeout;
		this.duringTimeout = duringTimeout;
		this.doubleOnGo = doubleOnGo;
		this.freeParking = freeParking;
		this.auctions = auctions;
	}
	
	/**
	 * Returns the number of games to be played per round
	 * (players choose new heuristics every round)
	 * @return
	 */
	@JsonIgnore
	public int getNumGamesPerRound(){
		return this.gamesPerRound;
	}
	
	/**
	 * Stores the number of rounds to be played
	 * @return
	 */
	@JsonIgnore
	public int getNumRounds(){
		return this.numRounds;
	}
	
	/**
	 * 
	 * @return the number of games that will be played each round
	 */
	@JsonIgnore
	public int getNumGames(){
		return this.gamesPerRound * this.numRounds;
	}
	
	@Override
	public boolean equals(Object o){
		if (o == null || !(o instanceof Settings))
			return false;
		
		Settings that = (Settings) o;
		
		return (this.gamesPerRound == that.gamesPerRound)
			&& (this.numRounds == that.numRounds)
			&& (this.winType == that.winType)
			&& (this.beginningTimeout == that.beginningTimeout)
			&& (this.duringTimeout == that.duringTimeout)
			&& (this.doubleOnGo == that.doubleOnGo)
			&& (this.freeParking == that.freeParking)
			&& (this.auctions == that.auctions);
	}
}