package edu.brown.cs32.MFTG.tournament.data;

public class PlayerWealthDataReport {
	public final int ownerID;
	public final int accCash;
	public final int accTotalWealth;
	public final int numDataPoints;
	
	public PlayerWealthDataReport(int ownerID, int accCash, int accTotalWealth, int numDataPoints){
		this.ownerID = ownerID;
		this.accCash = accCash;
		this.accTotalWealth = accTotalWealth;
		this.numDataPoints = numDataPoints;
	}
}
