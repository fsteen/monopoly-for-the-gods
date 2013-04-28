package edu.brown.cs32.MFTG.tournament.data;

public class PlayerWealthDataAccumulator{
	public final int ownerID;
	public double accCash;
	public double accTotalWealth;
	public int numDataPoints;

	public PlayerWealthDataAccumulator(int ownerID) {
		this.ownerID = ownerID;
		accCash = 0;
		accTotalWealth = 0;
		numDataPoints = 0;
	}
	
	public PlayerWealthDataReport toPlayerWealthDataReport(){
		//TODO must currently round the averages if i want to use PlayerWealthData class
		
		int divideBy = numDataPoints == 0 ? 1 : numDataPoints;
		return new PlayerWealthDataReport(
				ownerID,
				accCash/divideBy,
				accTotalWealth/divideBy,
				numDataPoints);
	}
}