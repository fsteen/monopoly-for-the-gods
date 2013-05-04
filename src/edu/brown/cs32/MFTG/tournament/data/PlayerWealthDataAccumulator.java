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
	
	public void average(){
		int divideBy = numDataPoints == 0 ? 1 : numDataPoints;
		accCash = accCash/divideBy;
		accTotalWealth = accTotalWealth/divideBy;
	}
	
	public void averageWith(PlayerWealthDataAccumulator p){
		if(numDataPoints != 0 && p.numDataPoints != 0){
			int newNumDataPoints = p.numDataPoints + numDataPoints;
			accCash = ((accCash * numDataPoints) + (p.accCash * p.numDataPoints))/newNumDataPoints;
			accTotalWealth = ((accTotalWealth * numDataPoints) + (p.accTotalWealth * p.numDataPoints))/newNumDataPoints;
			numDataPoints = newNumDataPoints;
		}
	}
	
	public PlayerWealthDataReport toPlayerWealthDataReport(){
		return new PlayerWealthDataReport(
				ownerID,
				accCash,
				accTotalWealth,
				numDataPoints);
	}
}