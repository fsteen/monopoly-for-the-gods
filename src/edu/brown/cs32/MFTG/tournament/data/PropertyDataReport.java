package edu.brown.cs32.MFTG.tournament.data;

import java.util.Map;

public class PropertyDataReport {

	public final String propertyName;
	public final int accNumHouses;
	public final int accTotalRevenueWithHouses;
	public final int accTotalRevenueWithoutHouses;
	public final int accMortgaged;
	public final int numDataPoints;
	public final Map<Integer,PlayerPropertyDataReport> playerPropertyData;
	
	public PropertyDataReport(String propertyName, int accNumHouses,
			int accTotalRevenueWithHouses, int accTotalRevenueWithoutHouses,
			int accMortgaged, int numDataPoints,
			Map<Integer,PlayerPropertyDataReport> playerPropertyData){
		
		this.propertyName = propertyName;
		this.accNumHouses = accNumHouses;
		this.accTotalRevenueWithHouses = accTotalRevenueWithHouses;
		this.accTotalRevenueWithoutHouses = accTotalRevenueWithoutHouses;
		this.accMortgaged = accMortgaged;
		this.numDataPoints = numDataPoints;
		this.playerPropertyData = playerPropertyData;
	}
}
