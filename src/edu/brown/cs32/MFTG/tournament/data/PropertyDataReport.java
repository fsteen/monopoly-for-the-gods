package edu.brown.cs32.MFTG.tournament.data;

public class PropertyDataReport {

	public final String propertyName;
	public final int ownerID;
	public final int accNumHouses;
	public final int accTotalRevenueWithHouses;
	public final int accTotalRevenueWithoutHouses;
	public final int accMortgaged;
	public final int numDataPoints;
	
	public PropertyDataReport(String propertyName, int ownerID, int accNumHouses,
			int accTotalRevenueWithHouses, int accTotalRevenueWithoutHouses,
			int accMortgaged, int numDataPoints){
		
		this.ownerID = ownerID;
		this.propertyName = propertyName;
		this.accNumHouses = accNumHouses;
		this.accTotalRevenueWithHouses = accTotalRevenueWithHouses;
		this.accTotalRevenueWithoutHouses = accTotalRevenueWithoutHouses;
		this.accMortgaged = accMortgaged;
		this.numDataPoints = numDataPoints;
	}
}
