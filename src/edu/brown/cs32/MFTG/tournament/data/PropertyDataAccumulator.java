package edu.brown.cs32.MFTG.tournament.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	
	public PlayerPropertyData get(int playerOwnerID){
		PlayerPropertyData data = playerPropertyData.get(playerOwnerID);
		if(data == null){
			data = new PlayerPropertyData(playerOwnerID);
			playerPropertyData.put(playerOwnerID, data);
		}
		return data;
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

		public PropertyData toPropertyData(){
			//TODO i cannot report mortgage info at the moment
			return new PropertyData(
					propertyName,playerOwnerID,
					Math.round(playerNumHouses/numDataPoints),
					Math.round(playerPersonalRevenueWithHouses/numDataPoints),
					Math.round(playerPersonalRevenueWithoutHouses/numDataPoints),
					0,0,false);
		}
	}
	
	public List<PropertyData> toPropertyData(){
		//TODO i cannot report mortgage info at the moment
		List<PropertyData> data = new ArrayList<>();
		data.add(new PropertyData(propertyName,-1,
				Math.round(accNumHouses/numDataPoints),0,0,
				Math.round(accTotalRevenueWithHouses/numDataPoints),
				Math.round(accTotalRevenueWithoutHouses/numDataPoints),false));
		
		for(PlayerPropertyData p : playerPropertyData.values()){
			data.add(p.toPropertyData());
		}
		
		return data;
	}
}
