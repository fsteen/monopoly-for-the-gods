package edu.brown.cs32.MFTG.tournament;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.brown.cs32.MFTG.monopoly.PlayerWealthData;
import edu.brown.cs32.MFTG.monopoly.PropertyData;
import edu.brown.cs32.MFTG.monopoly.TimeStamp;
import edu.brown.cs32.MFTG.tournament.PropertyDataAccumulator.PlayerPropertyData;

public class TimeStampAccumulator {

	private Map<String, PropertyDataAccumulator> _propertyData;
	private Map<Integer, PlayerWealthDataAccumulator> _wealthData;
	public final int _time;
	
	public TimeStampAccumulator(int time){
		_time=time;
		_propertyData = new HashMap<>();
		_wealthData = new HashMap<>();
	}
	
	public PropertyDataAccumulator getPropertyData(String propertyName){
		return _propertyData.get(propertyName);
	}
	
	public PlayerWealthDataAccumulator getPlayerWealthData(int playerID){
		return _wealthData.get(playerID);	
	}
	
	public void putPropertyData(PropertyData data){
		//add everything together so that in the end you can average it
		PropertyDataAccumulator accData = _propertyData.get(data.propertyName);
		if(accData == null){
			accData = new PropertyDataAccumulator(data.propertyName);
		}
		
		accData.accMortgaged += data.mortgaged ? 1 : 0;
		accData.accNumHouses += data.numHouses;
		accData.accTotalRevenueWithHouses += data.totalRevenueWithHouses;
		accData.accTotalRevenueWithoutHouses += data.totalRevenueWithoutHouses;
		accData.numDataPoints += 1;
		
		PlayerPropertyData playerData = accData.get(data.ownerID);
		playerData.playerMortgaged += data.mortgaged ? 1 : 0;
		playerData.playerNumHouses += data.numHouses;
		playerData.playerPersonalRevenueWithHouses += data.personalRevenueWithHouses;
		playerData.playerPersonalRevenueWithoutHouses += data.personalRevenueWithoutHouses;
		playerData.numDataPoints += 1;
	}
	
	public void putWealthData(PlayerWealthData data){
		//add everything together so that in the end you can average it
		PlayerWealthDataAccumulator accData = _wealthData.get(data.ownerID);
		if(accData == null){
			accData = new PlayerWealthDataAccumulator(data.ownerID);
		}
		
		accData.accCash += data.cash;
		accData.accTotalWealth += data.totalWealth;
		accData.numDataPoints += 1;
	}
	
	public List<PropertyDataAccumulator> getAllPropertyData(){
		return new ArrayList<PropertyDataAccumulator>(_propertyData.values());
	}
	
	public List<PlayerWealthDataAccumulator> getAllPlayerWealthData(){
		return new ArrayList<PlayerWealthDataAccumulator>(_wealthData.values());
	}
	
	/**
	 * Converts a TimeStampAccumulator to a TimeStamp
	 * @return
	 */
	public TimeStamp toTimeStamp(){
		TimeStamp t = new TimeStamp(_time);
		
		ArrayList<PropertyData> pData = new ArrayList<>();
		ArrayList<PlayerWealthData> wData = new ArrayList<>();
		
		for(PropertyDataAccumulator p : _propertyData.values()){
			pData.addAll(p.toPropertyData());
		}
		
		for(PlayerWealthDataAccumulator w : _wealthData.values()){
			wData.add(w.toPlayerWealthData());
		}
		
		t.setWealthData(wData);
		t.setPropertyData(pData);

		return t;
	}
}