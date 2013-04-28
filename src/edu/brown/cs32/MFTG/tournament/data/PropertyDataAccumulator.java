package edu.brown.cs32.MFTG.tournament.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.brown.cs32.MFTG.tournament.PlayerPropertyData;

public class PropertyDataAccumulator{
	
	public final String propertyName;
	public int accNumHouses;
	public int accTotalRevenueWithHouses;
	public int accTotalRevenueWithoutHouses;
	public int accMortgaged;
	public int numDataPoints;
	private Map<Integer,PlayerPropertyData> playerPropertyData;

	@JsonCreator
	public PropertyDataAccumulator(@JsonProperty("propertyName") String propertyName) {
		this.propertyName = propertyName;
		accNumHouses = 0;
		accTotalRevenueWithHouses = 0;
		accTotalRevenueWithoutHouses = 0;
		accMortgaged = 0;
		numDataPoints = 0;
		playerPropertyData = new HashMap<>();
	}
	
	public void reset(){
		accNumHouses = 0;
		accTotalRevenueWithHouses = 0;
		accTotalRevenueWithoutHouses = 0;
		accMortgaged = 0;
		numDataPoints = 0;
		for(PlayerPropertyData p : playerPropertyData.values()){
			p.reset();
		}
	}
	
	public PlayerPropertyData get(int playerOwnerID){
		PlayerPropertyData data = playerPropertyData.get(playerOwnerID);
		if(data == null){
			data = new PlayerPropertyData(propertyName, playerOwnerID);
			playerPropertyData.put(playerOwnerID, data);
		}
		return data;
	}
	
	public List<PlayerPropertyData> getAll(){
		return new ArrayList<PlayerPropertyData>(playerPropertyData.values());
	}
	
	public List<PropertyDataReport> toPlayerPropertyDataReport(){
		List<PropertyDataReport> playerData = new ArrayList<>();
		
		for(PlayerPropertyData p : playerPropertyData.values()){
			playerData.add(p.toPropertyDataReport());
		}
		
		playerData.add(new PropertyDataReport(propertyName, -1, accNumHouses/numDataPoints,
				accTotalRevenueWithHouses/numDataPoints, accTotalRevenueWithoutHouses/numDataPoints,
				accMortgaged/numDataPoints, numDataPoints));
		
		return playerData;
	}
	
	// getters and setters for serialization
	/**
	 * 
	 * @return this.accNumHouses
	 */
	public int getAccNumHouses() {
		return accNumHouses;
	}

	/**
	 * Setter for accNumHouses
	 * @param accNumHouses
	 */
	public void setAccNumHouses(int accNumHouses) {
		this.accNumHouses = accNumHouses;
	}

	/**
	 * 
	 * @return this.accTotalRevenueWithHouses
	 */
	public int getAccTotalRevenueWithHouses() {
		return accTotalRevenueWithHouses;
	}

	/**
	 * Setter for accTotalRevenueWithHouses
	 * @param accTotalRevenueWithHouses
	 */
	public void setAccTotalRevenueWithHouses(int accTotalRevenueWithHouses) {
		this.accTotalRevenueWithHouses = accTotalRevenueWithHouses;
	}

	/**
	 * 
	 * @return this.accTotalRevenueWithoutHouses
	 */
	public int getAccTotalRevenueWithoutHouses() {
		return accTotalRevenueWithoutHouses;
	}

	/**
	 * Setter for accTotalRevenueWithoutHouses
	 * @param accTotalRevenueWithoutHouses
	 */
	public void setAccTotalRevenueWithoutHouses(int accTotalRevenueWithoutHouses) {
		this.accTotalRevenueWithoutHouses = accTotalRevenueWithoutHouses;
	}

	/**
	 * 
	 * @return accMortgaged
	 */
	public int getAccMortgaged() {
		return accMortgaged;
	}

	/**
	 * Setter for accMortgaged
	 * @param accMortgaged
	 */
	public void setAccMortgaged(int accMortgaged) {
		this.accMortgaged = accMortgaged;
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

	/**
	 * Getter for playerPropertyData
	 * @return
	 */
	public Map<Integer, PlayerPropertyData> getPlayerPropertyData() {
		return playerPropertyData;
	}

	/**
	 * Setter for playerPropertyData
	 * @param playerPropertyData
	 */
	public void setPlayerPropertyData(Map<Integer, PlayerPropertyData> playerPropertyData) {
		this.playerPropertyData = playerPropertyData;
	}
}
