package edu.brown.cs32.MFTG.tournament.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerWealthDataReport {
	public final int ownerID;
	public final int accCash;
	public final int accTotalWealth;
	public final int numDataPoints;
	
	@JsonCreator
	public PlayerWealthDataReport(@JsonProperty("ownderID") int ownerID, 
								  @JsonProperty("accCash") int accCash,
								  @JsonProperty("accTotalWealth") int accTotalWealth,
								  @JsonProperty("numDataPoints") int numDataPoints){
		this.ownerID = ownerID;
		this.accCash = accCash;
		this.accTotalWealth = accTotalWealth;
		this.numDataPoints = numDataPoints;
	}

	@Override
	public boolean equals(Object o){
		if (o == null || !(o instanceof PlayerWealthDataReport))
			return false;
		
		PlayerWealthDataReport that = (PlayerWealthDataReport) o;
		
		return (this.ownerID == that.ownerID) && (this.accCash == that.accCash) 
				&& (this.accTotalWealth == that.accTotalWealth) && (this.numDataPoints == that.numDataPoints);
	}
}
