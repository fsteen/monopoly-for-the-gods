package edu.brown.cs32.MFTG.tournament.data;

public class PropertyDataReport {

	public final String propertyName;
	public final int ownerID;
	public final double accNumHouses;
	public final double accTotalRevenueWithHouses;
	public final double accTotalRevenueWithoutHouses;
	public final double accMortgaged;
	public final int numDataPoints;
	
	public PropertyDataReport(String propertyName, int ownerID, double accNumHouses,
			double accTotalRevenueWithHouses, double accTotalRevenueWithoutHouses,
			double accMortgaged, int numDataPoints){
		
		this.ownerID = ownerID;
		this.propertyName = propertyName;
		this.accNumHouses = accNumHouses;
		this.accTotalRevenueWithHouses = accTotalRevenueWithHouses;
		this.accTotalRevenueWithoutHouses = accTotalRevenueWithoutHouses;
		this.accMortgaged = accMortgaged;
		this.numDataPoints = numDataPoints;
	}
	
	@Override
	public String toString(){
		return String.format("{%s, ID: %d, houses: %f, revW: %f, revWO: %f, mort: %f, #data: %d}",
				propertyName,ownerID,accNumHouses,accTotalRevenueWithHouses,
				accTotalRevenueWithoutHouses,accMortgaged,numDataPoints);
	}
}
