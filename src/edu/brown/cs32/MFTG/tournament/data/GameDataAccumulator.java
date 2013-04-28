package edu.brown.cs32.MFTG.tournament.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ArrayListMultimap;

import edu.brown.cs32.MFTG.monopoly.PropertyData;
import edu.brown.cs32.MFTG.tournament.PlayerPropertyData;

public class GameDataAccumulator {

	public List<TimeStampAccumulator> data;
	public Map<String, PropertyDataAccumulator> entireGameData;
	private Map<String, PropertyDataAccumulator> currentMaxValues;
	private Map<Integer, Integer> _playerWins;
	
	public GameDataAccumulator(int numTimeStamps){
		_playerWins = new HashMap<>();
		entireGameData = new HashMap<>();
		currentMaxValues = new HashMap<>();
		data = new ArrayList<>();

		for(int i = 0; i < numTimeStamps; i++){
			data.add(new TimeStampAccumulator(i));
		}
	}
	
	public void gameFinished(){
		//puts all the current max values into the entireGameData ...
		PropertyDataAccumulator entireGameTemp;
		
		for(PropertyDataAccumulator p : currentMaxValues.values()){
			entireGameTemp = entireGameData.get(p.propertyName);
			
			if(entireGameTemp == null){
				entireGameTemp = new PropertyDataAccumulator(p.propertyName);
				entireGameData.put(p.propertyName, entireGameTemp);
			}
			
			entireGameTemp.accNumHouses += p.accNumHouses;
			entireGameTemp.accTotalRevenueWithHouses += p.accTotalRevenueWithHouses;
			entireGameTemp.accTotalRevenueWithoutHouses += p.accTotalRevenueWithoutHouses;
			entireGameTemp.numDataPoints += 1;
			
			PlayerPropertyData temp;
			for(PlayerPropertyData d : p.getAll()){
				temp = entireGameTemp.get(d.playerOwnerID);
				temp.playerNumHouses += d.playerNumHouses;
				temp.playerPersonalRevenueWithHouses += d.playerPersonalRevenueWithHouses;
				temp.playerPersonalRevenueWithoutHouses += d.playerPersonalRevenueWithoutHouses;
			}
			
			p.reset(); //reset the currentMaxes
		}
	}
	
	public void putPropertyData(PropertyData data){
		//finds the max so far
		PropertyDataAccumulator accData = currentMaxValues.get(data.propertyName);
		if(accData == null){
			accData = new PropertyDataAccumulator(data.propertyName);
			currentMaxValues.put(data.propertyName, accData);
		}
		
		accData.accNumHouses = Math.max(accData.accNumHouses, data.numHouses);
		accData.accTotalRevenueWithHouses = Math.max(accData.accTotalRevenueWithHouses, data.totalRevenueWithHouses);
		accData.accTotalRevenueWithoutHouses = Math.max(accData.accTotalRevenueWithoutHouses, data.totalRevenueWithoutHouses);
		
		PlayerPropertyData playerData = accData.get(data.ownerID);
		playerData.playerNumHouses = Math.max(playerData.playerNumHouses, data.numHouses);
		playerData.playerPersonalRevenueWithHouses = Math.max(playerData.playerPersonalRevenueWithHouses, data.personalRevenueWithHouses);
		playerData.playerPersonalRevenueWithoutHouses = Math.max(playerData.playerPersonalRevenueWithoutHouses, data.personalRevenueWithoutHouses);
	}
	
	public void addPlayerWin(int playerID){
		Integer wins = _playerWins.get(playerID);
		if(wins == null){
			wins = 0;
		}
		_playerWins.put(playerID, ++wins);
	}
	
	private int getPlayerWithMostWins(){
		int playerID = -1;
		int maxWins = -1;
		
		for(Entry<Integer, Integer> current : _playerWins.entrySet()){
			if(current.getValue() > maxWins){
				playerID = current.getKey();
				maxWins = current.getValue();
			}
		}
		return playerID;
	}
	
	/**
	 * Converts a GameDataAccumulator to a GameData
	 * @return
	 */
	public GameDataReport toGameDataReport(){
		List<TimeStampReport> times = new ArrayList<>();
		
		for(TimeStampAccumulator t : data){
			times.add(t.toTimeStampReport());
		}
		
		ArrayListMultimap<String,PropertyDataReport> gameData = ArrayListMultimap.create();
				for(PropertyDataAccumulator p : entireGameData.values()){
			gameData.putAll(p.propertyName, p.toPlayerPropertyDataReport());
		}
		
		return new GameDataReport(times, getPlayerWithMostWins(), gameData);		
	}
}