package edu.brown.cs32.MFTG.tournament.data;

public class PlayerWealthDataReport {
	public final int ownerID;
	public final double accCash;
	public final double accTotalWealth;
	public final int numDataPoints;
	
	public PlayerWealthDataReport(int ownerID, double accCash, double accTotalWealth, int numDataPoints){
		this.ownerID = ownerID;
		this.accCash = accCash;
		this.accTotalWealth = accTotalWealth;
		this.numDataPoints = numDataPoints;
	}
	
	@Override
	public String toString(){
		return String.format("{ID: %d, cash: %f, wealth: %f, #data: %d}", 
				ownerID, accCash, accTotalWealth, numDataPoints);
	}
}
