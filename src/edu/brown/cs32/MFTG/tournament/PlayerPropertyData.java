package edu.brown.cs32.MFTG.tournament;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import edu.brown.cs32.MFTG.tournament.data.PropertyDataReport;

public class PlayerPropertyData {
	public final String propertyName;
	public final int playerOwnerID;
	public double playerNumHouses;
	public double playerPersonalRevenueWithHouses;
	public double playerPersonalRevenueWithoutHouses;
	public double playerMortgaged;
	public int numDataPoints;
	
	@JsonCreator
	public PlayerPropertyData(@JsonProperty("propertyName") String propertyName, @JsonProperty("playerOwnerID") int playerOwnerID){
		this.propertyName = propertyName;
		this.playerOwnerID = playerOwnerID;
		playerNumHouses = 0;
		playerPersonalRevenueWithHouses = 0;
		playerPersonalRevenueWithoutHouses = 0;
		playerMortgaged = 0;
		numDataPoints = 0;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof PlayerPropertyData))
			return false;
		
		PlayerPropertyData that = (PlayerPropertyData) o;
		
		return Objects.equal(this.propertyName, that.propertyName)
			&& this.playerOwnerID == that.playerOwnerID 
			&& this.playerNumHouses == that.playerNumHouses
			&& this.playerPersonalRevenueWithHouses == that.playerPersonalRevenueWithHouses
			&& this.playerPersonalRevenueWithoutHouses == that.playerPersonalRevenueWithoutHouses
			&& this.playerMortgaged == that.playerMortgaged
			&& this.numDataPoints == that.numDataPoints;
	}

	public void reset(){
		playerNumHouses = 0;
		playerPersonalRevenueWithHouses = 0;
		playerPersonalRevenueWithoutHouses = 0;
		playerMortgaged = 0;
		numDataPoints = 0;
	}

	public PropertyDataReport toPropertyDataReport(){
		//TODO i cannot report mortgage info at the moment			
		return new PropertyDataReport(
				propertyName,playerOwnerID,
				Math.round(playerNumHouses/numDataPoints),
				Math.round(playerPersonalRevenueWithHouses/numDataPoints),
				Math.round(playerPersonalRevenueWithoutHouses/numDataPoints),
				Math.round(playerMortgaged/numDataPoints),
				numDataPoints);
	}
	
	/**
	 * 
	 * @return this.playerNumHouses
	 */
	public double getPlayerNumHouses() {
		return playerNumHouses;
	}

	/**
	 * Setter for playerNumHouses
	 * @param playerNumHouses
	 */
	public void setPlayerNumHouses(int playerNumHouses) {
		this.playerNumHouses = playerNumHouses;
	}

	/**
	 * 
	 * @return this.playerPersonRevenueWithHouses
	 */
	public double getPlayerPersonalRevenueWithHouses() {
		return playerPersonalRevenueWithHouses;
	}

	/**
	 * Getter for playerPersonRevenueWithHouses
	 * @param playerPersonalRevenueWithHouses
	 */
	public void setPlayerPersonalRevenueWithHouses(
			int playerPersonalRevenueWithHouses) {
		this.playerPersonalRevenueWithHouses = playerPersonalRevenueWithHouses;
	}

	/**
	 * 
	 * @return this.playerPersonRevenueWithoutHouses
	 */
	public double getPlayerPersonalRevenueWithoutHouses() {
		return playerPersonalRevenueWithoutHouses;
	}

	/**
	 * playerPersonRevenueWithoutHouses
	 * @param playerPersonalRevenueWithoutHouses
	 */
	public void setPlayerPersonalRevenueWithoutHouses(
			int playerPersonalRevenueWithoutHouses) {
		this.playerPersonalRevenueWithoutHouses = playerPersonalRevenueWithoutHouses;
	}

	/**
	 * 
	 * @return this.playerMortgaged
	 */
	public double getPlayerMortgaged() {
		return playerMortgaged;
	}

	/**
	 * Setter for playerMortgaged
	 * @param playerMortgaged
	 */
	public void setPlayerMortgaged(int playerMortgaged) {
		this.playerMortgaged = playerMortgaged;
	}

	/**
	 * 
	 * @return this.numDataPoints
	 */
	public int getNumDataPoints() {
		return numDataPoints;
	}

	/**
	 * Setter for numDataPoints
	 * @param numDataPoints
	 */
	public void setNumDataPoints(int numDataPoints) {
		this.numDataPoints = numDataPoints;
	}
}