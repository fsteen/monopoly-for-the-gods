package edu.brown.cs32.MFTG.tournament.data;

public class PropertyDataAccumulator{
	public final String propertyName;
	public final int ownerID;
	public double accNumHouses;
	public double accTotalRevenueWithHouses;
	public double accTotalRevenueWithoutHouses;
	public double accMortgaged;
	public double accTimeOwned;
	public int numDataPoints;

	public PropertyDataAccumulator(String propertyName, int ownerID) {
		this.propertyName = propertyName;
		this.ownerID = ownerID;
		reset();
	}

	public void reset(){
		accNumHouses = 0.0;
		accTotalRevenueWithHouses = 0.0;
		accTotalRevenueWithoutHouses = 0.0;
		accMortgaged = 0.0;
		accTimeOwned = 0.0;
		numDataPoints = 0;
	}

	public void average(){
		int divideBy = numDataPoints == 0 ? 1 : numDataPoints;
		accNumHouses= accNumHouses/divideBy;
		accTotalRevenueWithHouses = accTotalRevenueWithHouses/divideBy;
		accTotalRevenueWithoutHouses = accTotalRevenueWithoutHouses/divideBy;
		accMortgaged = accMortgaged/divideBy;
		accTimeOwned = accTimeOwned/divideBy;
	}
	
	/**
	 * Assumes everything is already averaged ... so we must unaverage first
	 * @param p
	 */
	public void averageWith(PropertyDataAccumulator p){
		if(p != null && numDataPoints != 0 && p.numDataPoints != 0){
			int newNumDataPoints = p.numDataPoints + numDataPoints;
			accNumHouses = ((accNumHouses * numDataPoints) + (p.accNumHouses * p.numDataPoints))/newNumDataPoints;
			accTotalRevenueWithHouses = ((accTotalRevenueWithHouses * numDataPoints) + (p.accTotalRevenueWithHouses * p.numDataPoints))/newNumDataPoints;
			accTotalRevenueWithoutHouses = ((accTotalRevenueWithoutHouses * numDataPoints) + (p.accTotalRevenueWithoutHouses * p.numDataPoints))/newNumDataPoints;
			accMortgaged = ((accMortgaged * numDataPoints) + (p.accMortgaged * p.numDataPoints))/newNumDataPoints;
			accTimeOwned = ((accTimeOwned * numDataPoints) + (p.accTimeOwned * p.numDataPoints))/newNumDataPoints;
			numDataPoints = newNumDataPoints;
		}
	}
	
	public PropertyDataReport toPropertyDataReport(){
		return new PropertyDataReport(propertyName, ownerID, accNumHouses,
				accTotalRevenueWithHouses, accTotalRevenueWithoutHouses,
				accMortgaged, accTimeOwned,numDataPoints);
	}
}
