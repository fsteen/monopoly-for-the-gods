package edu.brown.cs32.MFTG.tournament.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ArrayListMultimap;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.PropertyData;

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
	
	public void gameFinished(GameData specific){
		//puts all the current max values into the entireGameData ...
		PropertyDataAccumulator entireGameTemp;
		
		int i = 0;
		for(PropertyDataAccumulator p : currentMaxValues.values()){ //for all of the current maxes
			entireGameTemp = entireGameData.get(p.propertyName); //add to the overall property data
			
			if(entireGameTemp == null){
				entireGameTemp = new PropertyDataAccumulator(p.propertyName);
				entireGameData.put(p.propertyName, entireGameTemp);
			}
			
			entireGameTemp.accNumHouses += p.accNumHouses;
			entireGameTemp.accTotalRevenueWithHouses += p.accTotalRevenueWithHouses;
			entireGameTemp.accTotalRevenueWithoutHouses += p.accTotalRevenueWithoutHouses;
			entireGameTemp.numDataPoints += 1;
			
			PlayerPropertyDataAccumulator temp;
			for(PlayerPropertyDataAccumulator d : p.getAll()){ //for all the player specific max data
				
				temp = entireGameTemp.get(d.playerOwnerID);
				temp.playerNumHouses += d.playerNumHouses;
				temp.playerPersonalRevenueWithHouses += d.playerPersonalRevenueWithHouses;
				temp.playerPersonalRevenueWithoutHouses += d.playerPersonalRevenueWithoutHouses;
				int divideBy = p.numDataPoints == 0 ? 1 : p.numDataPoints;
				temp.playerTimeOwned += d.numDataPoints/divideBy;
				temp.numDataPoints += 1;
			}
			
//			if(p.numDataPoints == 0){
//				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//				specific.printData();
////				System.out.println(String.format("%d h:%f, rW:%f, rWO:%f", 
////						i++,p.accNumHouses , p.accTotalRevenueWithHouses, p.accTotalRevenueWithoutHouses));
//			}

			
			p.reset(); //reset the currentMaxes
		}
	}
	
	public void putPropertyData(PropertyData data){ //this goes through all time stamps in a game
		//finds the max so far
		PropertyDataAccumulator accData = currentMaxValues.get(data.propertyName);
		if(accData == null){
			accData = new PropertyDataAccumulator(data.propertyName);
			currentMaxValues.put(data.propertyName, accData);
		}
		
		accData.accNumHouses = Math.max(accData.accNumHouses, data.numHouses);
		accData.accTotalRevenueWithHouses = Math.max(accData.accTotalRevenueWithHouses, data.totalRevenueWithHouses);
		accData.accTotalRevenueWithoutHouses = Math.max(accData.accTotalRevenueWithoutHouses, data.totalRevenueWithoutHouses);
		accData.numDataPoints += 1;
		
		PlayerPropertyDataAccumulator playerData = accData.get(data.ownerID);
		playerData.playerNumHouses = Math.max(playerData.playerNumHouses, data.numHouses);
		playerData.playerPersonalRevenueWithHouses = Math.max(playerData.playerPersonalRevenueWithHouses, data.personalRevenueWithHouses);
		playerData.playerPersonalRevenueWithoutHouses = Math.max(playerData.playerPersonalRevenueWithoutHouses, data.personalRevenueWithoutHouses);
		playerData.numDataPoints += 1;
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
		
		Map<String,List<PropertyDataReport>> overallPlayerPropertyData = new HashMap<>();
		Map<String,PropertyDataReport> overallPropertyData = new HashMap<>();
		for(PropertyDataAccumulator p : entireGameData.values()){
			overallPlayerPropertyData.put(p.propertyName, p.toPlayerPropertyDataReport(p.numDataPoints));
			overallPropertyData.put(p.propertyName, p.toPropertyDataReport());
		}
		
		return new GameDataReport(times, getPlayerWithMostWins(), overallPlayerPropertyData, overallPropertyData);		
	}
}