package edu.brown.cs32.MFTG.monopoly;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * class to hold all data for a given property at any given time
 * @author Jschvime
 *
 */
public class PropertyData{
	public final String propertyName;
	public final int ownerID;
	public final double numHouses;
	public final double personalRevenueWithHouses;
	public final double personalRevenueWithoutHouses;
	public final double totalRevenueWithHouses;
	public final double totalRevenueWithoutHouses;
	public final boolean mortgaged;
	
	@JsonCreator
	public PropertyData(@JsonProperty("propertyName") String propertyName, 
						@JsonProperty("ownerID") int ownerID,
						@JsonProperty("numHouses") double numHouses, 
						@JsonProperty("personalRevenueWithHouses") double personalRevenueWithHouses, 
						@JsonProperty("personalRevenueWIthoutHouses") double personalRevenueWithoutHouses,
						@JsonProperty("totalRevenueWithHouses") double totalRevenueWithHouses,
						@JsonProperty("totalRevenueWithoutHouses") double totalRevenueWithoutHouses,
						@JsonProperty("mortgaged") boolean mortgaged){
		
		this.propertyName=propertyName;
		this.ownerID=ownerID;
		this.numHouses=numHouses;
		this.personalRevenueWithHouses=personalRevenueWithHouses;
		this.personalRevenueWithoutHouses=personalRevenueWithoutHouses;
		this.totalRevenueWithHouses=totalRevenueWithHouses;
		this.totalRevenueWithoutHouses=totalRevenueWithoutHouses;
		this.mortgaged=mortgaged;
	}
	
	@Override
	public boolean equals(Object o){
		if (o == null || !(o instanceof PropertyData))
			return false;
		
		PropertyData that = (PropertyData) o;
		
		return (this.propertyName == that.propertyName)
			&& (this.ownerID == that.ownerID)
			&& (this.numHouses == that.numHouses)
			&& (this.personalRevenueWithHouses == that.personalRevenueWithHouses)
			&& (this.personalRevenueWithoutHouses == that.personalRevenueWithoutHouses)
			&& (this.totalRevenueWithHouses == that.totalRevenueWithHouses)
			&& (this.totalRevenueWithoutHouses == that.totalRevenueWithoutHouses)
			&& (this.mortgaged == that.mortgaged);
	}
}