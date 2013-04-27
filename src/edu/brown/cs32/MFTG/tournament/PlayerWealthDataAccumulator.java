package edu.brown.cs32.MFTG.tournament;

import edu.brown.cs32.MFTG.monopoly.PlayerWealthData;

public class PlayerWealthDataAccumulator{
	public final int ownerID;
	public int accCash;
	public int accTotalWealth;
	public int numDataPoints;

	public PlayerWealthDataAccumulator(int ownerID) {
		this.ownerID = ownerID;
		accCash = 0;
		accTotalWealth = 0;
		numDataPoints = 0;
	}
	
	public PlayerWealthData toPlayerWealthData(){
		//TODO must currently round the averages if i want to use PlayerWealthData class
		return new PlayerWealthData(
				ownerID,
				Math.round(accCash/numDataPoints),
				Math.round(accTotalWealth/numDataPoints));
	}
}