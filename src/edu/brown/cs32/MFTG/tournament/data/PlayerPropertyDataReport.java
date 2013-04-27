package edu.brown.cs32.MFTG.tournament.data;

public class PlayerPropertyDataReport {
	public final int playerOwnerID;
	public int playerNumHouses;
	public int playerPersonalRevenueWithHouses;
	public int playerPersonalRevenueWithoutHouses;
	public int playerMortgaged;
	public int numDataPoints;
	
	PlayerPropertyDataReport(int playerOwnerID, int playerNumHouses, 
			int playerPersonalRevenueWithHouses, int playerPersonalRevenueWithoutHouses,
			int playerMortgaged, int numDataPoints){
		
		this.playerOwnerID = playerOwnerID;
		this.playerNumHouses = playerNumHouses;
		this.playerPersonalRevenueWithHouses = playerPersonalRevenueWithHouses;
		this.playerPersonalRevenueWithoutHouses = playerPersonalRevenueWithoutHouses;
		this.playerMortgaged = playerMortgaged;
		this.numDataPoints = numDataPoints;
	}
}