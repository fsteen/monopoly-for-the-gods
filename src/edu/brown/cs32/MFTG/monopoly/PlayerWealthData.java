package edu.brown.cs32.MFTG.monopoly;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * class to hold all data for a owner at a given time
 * @author Jschvime
 *
 */
public class PlayerWealthData{
	public final int ownerID;
	public final double cash;
	public final double totalWealth;
	
	@JsonCreator
	public PlayerWealthData(@JsonProperty("ownerID") int ownerID, @JsonProperty("cash") double cash, 
							@JsonProperty("totalWealth") double totalWealth){
		this.ownerID=ownerID;
		this.cash=cash;
		this.totalWealth=totalWealth;
	}
	
	@Override
	public boolean equals(Object o){
		if (o == null || !(o instanceof PlayerWealthData))
			return false;
		
		PlayerWealthData that = (PlayerWealthData) o;
		
		return (this.ownerID == that.ownerID) && (this.cash == that.cash) && (this.totalWealth == that.totalWealth);  
	}
}
