package edu.brown.cs32.MFTG.tournament.data;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PropertyDataReport {

	public final String propertyName;
	public final int ownerID;
	public final double accNumHouses;
	public final double accTotalRevenueWithHouses;
	public final double accTotalRevenueWithoutHouses;
	public final double accMortgaged;
	public final int numDataPoints;
	
	@JsonCreator
	public PropertyDataReport(@JsonProperty("propertyName") String propertyName, 
							  @JsonProperty("ownderID") int ownerID,
							  @JsonProperty("accNumHouses") double accNumHouses,
							  @JsonProperty("accTotalRevenueWithHouses") double accTotalRevenueWithHouses, 
							  @JsonProperty("accTotalRevenueWithoutHouses") double accTotalRevenueWithoutHouses,
							  @JsonProperty("accMortgaged") double accMortgaged, 
							  @JsonProperty("numDataPoints") int numDataPoints){
		
		this.ownerID = ownerID;
		this.propertyName = propertyName;
		this.accNumHouses = accNumHouses;
		this.accTotalRevenueWithHouses = accTotalRevenueWithHouses;
		this.accTotalRevenueWithoutHouses = accTotalRevenueWithoutHouses;
		this.accMortgaged = accMortgaged;
		this.numDataPoints = numDataPoints;
	}
	
	@Override
	public String toString(){
		return String.format("{%s, ID: %d, houses: %f, revW: %f, revWO: %f, mort: %f, #data: %d}",
				propertyName,ownerID,accNumHouses,accTotalRevenueWithHouses,
				accTotalRevenueWithoutHouses,accMortgaged,numDataPoints);
	}
	
	@Override
	public boolean equals(Object o){
		if (o == null || !(o instanceof PropertyDataReport))
			return false;
		
		PropertyDataReport that = (PropertyDataReport) o;
		
		return Objects.equals(this.propertyName, that.propertyName) 
			   && (this.ownerID == that.ownerID)
			   && (this.accNumHouses == that.accNumHouses) 
			   && (this.accTotalRevenueWithHouses == that.accTotalRevenueWithHouses)
			   && (this.accTotalRevenueWithoutHouses == that.accTotalRevenueWithoutHouses)
			   && (this.accMortgaged == that.accMortgaged)
			   && (this.numDataPoints == that.numDataPoints);
	}
}
