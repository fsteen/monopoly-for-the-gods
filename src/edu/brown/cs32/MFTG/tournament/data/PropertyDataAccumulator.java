package edu.brown.cs32.MFTG.tournament.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.brown.cs32.MFTG.monopoly.PropertyData;

public class PropertyDataAccumulator{
	
	public final String propertyName;
	public int accNumHouses;
	public int accTotalRevenueWithHouses;
	public int accTotalRevenueWithoutHouses;
	public int accMortgaged;
	public int numDataPoints;
	private Map<Integer,PlayerPropertyData> playerPropertyData;

	public PropertyDataAccumulator(String propertyName) {
		this.propertyName = propertyName;
		accNumHouses = 0;
		accTotalRevenueWithHouses = 0;
		accTotalRevenueWithoutHouses = 0;
		accMortgaged = 0;
		numDataPoints = 0;
		playerPropertyData = new HashMap<>();
	}
	
	public void reset(){
		accNumHouses = 0;
		accTotalRevenueWithHouses = 0;
		accTotalRevenueWithoutHouses = 0;
		accMortgaged = 0;
		numDataPoints = 0;
		for(PlayerPropertyData p : playerPropertyData.values()){
			p.reset();
		}
	}
	
	public PlayerPropertyData get(int playerOwnerID){
		PlayerPropertyData data = playerPropertyData.get(playerOwnerID);
		if(data == null){
			data = new PlayerPropertyData(playerOwnerID);
			playerPropertyData.put(playerOwnerID, data);
		}
		return data;
	}
	
	public List<PlayerPropertyData> getAll(){
		return new ArrayList<PlayerPropertyData>(playerPropertyData.values());
	}
	
	public List<PropertyDataReport> toPlayerPropertyDataReport(){
		List<PropertyDataReport> playerData = new ArrayList<>();
		
		for(PlayerPropertyData p : playerPropertyData.values()){
			playerData.add(p.toPropertyDataReport());
		}
		
		playerData.add(new PropertyDataReport(propertyName, -1, accNumHouses/numDataPoints,
				accTotalRevenueWithHouses/numDataPoints, accTotalRevenueWithoutHouses/numDataPoints,
				accMortgaged/numDataPoints, numDataPoints));
		
		return playerData;
	}
	
	public class PlayerPropertyData {
		public final int playerOwnerID;
		public int playerNumHouses;
		public int playerPersonalRevenueWithHouses;
		public int playerPersonalRevenueWithoutHouses;
		public int playerMortgaged;
		public int numDataPoints;
		
		PlayerPropertyData(int playerOwnerID){
			this.playerOwnerID = playerOwnerID;
			playerNumHouses = 0;
			playerPersonalRevenueWithHouses = 0;
			playerPersonalRevenueWithoutHouses = 0;
			playerMortgaged = 0;
			numDataPoints = 0;
		}
		
		public void reset(){
			playerNumHouses = 0;
			playerPersonalRevenueWithHouses = 0;
			playerPersonalRevenueWithoutHouses = 0;
			playerMortgaged = 0;
			numDataPoints = 0;
		}

		public PropertyDataReport toPropertyDataReport(){
			//TODO i cannot report mortgage info at the moment			
			return new PropertyDataReport(
					propertyName,playerOwnerID,
					Math.round(playerNumHouses/numDataPoints),
					Math.round(playerPersonalRevenueWithHouses/numDataPoints),
					Math.round(playerPersonalRevenueWithoutHouses/numDataPoints),
					Math.round(playerMortgaged/numDataPoints),
					numDataPoints);
		}
	}
}
