package edu.brown.cs32.MFTG.tournament.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerWealthDataReport {
	public final int ownerID;
	public final double accCash;
	public final double accTotalWealth;
	public final int numDataPoints;
	
	@JsonCreator
	public PlayerWealthDataReport(@JsonProperty("ownderID") int ownerID, 
								  @JsonProperty("accCash") double accCash,
								  @JsonProperty("accTotalWealth") double accTotalWealth,
								  @JsonProperty("numDataPoints") int numDataPoints){
		this.ownerID = ownerID;
		this.accCash = accCash;
		this.accTotalWealth = accTotalWealth;
		this.numDataPoints = numDataPoints;
	}
	
	/**
	 * Converts the report to an accumulator
	 * @return
	 */
	public PlayerWealthDataAccumulator toPlayerWealthDataAccumulator(){
		PlayerWealthDataAccumulator p = new PlayerWealthDataAccumulator(ownerID);
		p.accCash = accCash;
		p.accTotalWealth = accTotalWealth;
		p.numDataPoints = numDataPoints;
		return p;
	}

	@Override
	public boolean equals(Object o){
		if (o == null || !(o instanceof PlayerWealthDataReport))
			return false;
		
		PlayerWealthDataReport that = (PlayerWealthDataReport) o;

		return (this.ownerID == that.ownerID) && Math.round(this.accCash) == Math.round(that.accCash) && 
				Math.round(this.accTotalWealth) == Math.round(that.accTotalWealth) && (this.numDataPoints == that.numDataPoints);
	}
	
	@Override
	public String toString(){
		return String.format("{ID: %d, cash: %f, wealth: %f, #data: %d}", 
				ownerID, accCash, accTotalWealth, numDataPoints);
	}
}