package edu.brown.cs32.MFTG.tournament.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class PropertyDataAccumulator{

	public final String propertyName;
	public double accNumHouses;
	public double accTotalRevenueWithHouses;
	public double accTotalRevenueWithoutHouses;
	public double accMortgaged;
	public int numDataPoints;
	private Map<Integer,PlayerPropertyDataAccumulator> playerPropertyData;

	@JsonCreator
	public PropertyDataAccumulator(@JsonProperty("propertyName") String propertyName) {
		this.propertyName = propertyName;
		accNumHouses = 0.0;
		accTotalRevenueWithHouses = 0.0;
		accTotalRevenueWithoutHouses = 0.0;
		accMortgaged = 0.0;
		numDataPoints = 0;
		playerPropertyData = new HashMap<>();
	}

	public void reset(){
		accNumHouses = 0.0;
		accTotalRevenueWithHouses = 0.0;
		accTotalRevenueWithoutHouses = 0.0;
		accMortgaged = 0.0;
		numDataPoints = 0;
		for(PlayerPropertyDataAccumulator p : playerPropertyData.values()){
			p.reset();
		}
	}

	public PlayerPropertyDataAccumulator get(int playerOwnerID){
		PlayerPropertyDataAccumulator data = playerPropertyData.get(playerOwnerID);
		if(data == null){
			data = new PlayerPropertyDataAccumulator(propertyName, playerOwnerID);
			playerPropertyData.put(playerOwnerID, data);
		}
		return data;
	}

	public List<PlayerPropertyDataAccumulator> getAll(){
		return new ArrayList<PlayerPropertyDataAccumulator>(playerPropertyData.values());
	}

	public List<PropertyDataReport> toPlayerPropertyDataReport(int totalDataPoints){
		List<PropertyDataReport> playerData = new ArrayList<>();

		for(PlayerPropertyDataAccumulator p : playerPropertyData.values()){
			playerData.add(p.toPropertyDataReport(totalDataPoints));
		}
		return playerData;
	}
	
	public PropertyDataReport toPropertyDataReport(){
		int divideBy = numDataPoints == 0 ? 1 : numDataPoints;
		return new PropertyDataReport(propertyName, -1, accNumHouses/divideBy,
				accTotalRevenueWithHouses/divideBy, accTotalRevenueWithoutHouses/divideBy,
				accMortgaged/divideBy, -1,numDataPoints);
	}

	// getters and setters for serialization
	/**
	 * 
	 * @return this.accNumHouses
	 */
	public double getAccNumHouses() {
		return accNumHouses;
	}

	/**
	 * Setter for accNumHouses
	 * @param accNumHouses
	 */
	public void setAccNumHouses(double accNumHouses) {
		this.accNumHouses = accNumHouses;
	}

	/**
	 * 
	 * @return this.accTotalRevenueWithHouses
	 */
	public double getAccTotalRevenueWithHouses() {
		return accTotalRevenueWithHouses;
	}

	/**
	 * Setter for accTotalRevenueWithHouses
	 * @param accTotalRevenueWithHouses
	 */
	public void setAccTotalRevenueWithHouses(double accTotalRevenueWithHouses) {
		this.accTotalRevenueWithHouses = accTotalRevenueWithHouses;
	}

	/**
	 * 
	 * @return this.accTotalRevenueWithoutHouses
	 */
	public double getAccTotalRevenueWithoutHouses() {
		return accTotalRevenueWithoutHouses;
	}

	/**
	 * Setter for accTotalRevenueWithoutHouses
	 * @param accTotalRevenueWithoutHouses
	 */
	public void setAccTotalRevenueWithoutHouses(double accTotalRevenueWithoutHouses) {
		this.accTotalRevenueWithoutHouses = accTotalRevenueWithoutHouses;
	}

	/**
	 * 
	 * @return accMortgaged
	 */
	public double getAccMortgaged() {
		return accMortgaged;
	}

	/**
	 * Setter for accMortgaged
	 * @param accMortgaged
	 */
	public void setAccMortgaged(double accMortgaged) {
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
	public Map<Integer, PlayerPropertyDataAccumulator> getPlayerPropertyData() {
		return playerPropertyData;
	}

	/**
	 * Setter for playerPropertyData
	 * @param playerPropertyData
	 */
	public void setPlayerPropertyData(Map<Integer, PlayerPropertyDataAccumulator> playerPropertyData) {
		this.playerPropertyData = playerPropertyData;
	}
}
