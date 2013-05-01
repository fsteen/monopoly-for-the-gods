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
	
	public Map<String, Map<Integer,PropertyDataAccumulator>> playerEntireGameData;
	private Map<String, Map<Integer,PropertyDataAccumulator>> playerCurrentMaxValues;
	private Map<Integer, Integer> _playerWins;
	
//	public final List<TimeStampReport> _timeStamps; //the average wealth data for players over these time stamps
//	public final Map<String, List<PropertyDataReport>> _overallPlayerPropertyData; //the 
//	public final Map<String, PropertyDataReport> _overallPropertyData;
//	public final int _winner;
	
	public GameDataAccumulator(int numTimeStamps){
		_playerWins = new HashMap<>();
		entireGameData = new HashMap<>();
		currentMaxValues = new HashMap<>();
		playerEntireGameData = new HashMap<>();
		playerCurrentMaxValues = new HashMap<>();
		data = new ArrayList<>();

		for(int i = 0; i < numTimeStamps; i++){
			data.add(new TimeStampAccumulator(i));
		}
	}
	
	public void averageWith(GameDataAccumulator g){
		if(g.data.size() != data.size()){
			System.out.println("cannot merge gamedata with different numbers of timestamps");
		}
		for(int i = 0; i < data.size(); i++){
			data.get(i).averageWith(g.data.get(i));
		}
		
		for(PropertyDataAccumulator p : entireGameData.values()){
			p.averageWith(g.entireGameData.get(p.ownerID));
		}
		
		PropertyDataAccumulator temp;
		for(Map<Integer,PropertyDataAccumulator> m : playerEntireGameData.values()){
			for(PropertyDataAccumulator p : m.values()){
				temp = g.playerEntireGameData.get(p.propertyName).get(p.ownerID);
				if(temp != null){
					p.averageWith(temp);
				}
			}
		}
		
		for(Map<Integer,PropertyDataAccumulator> m : g.playerEntireGameData.values()){
			for(PropertyDataAccumulator p : m.values()){
				temp = playerEntireGameData.get(p.propertyName).get(p.ownerID);
				if(temp != null){
					playerEntireGameData.get(p.propertyName).put(p.ownerID, temp); //add any player data that may not have been in the first bit
				}
			}
		}
	}
	
	public void gameFinished(GameData specific){
		//puts all the current max values into the entireGameData ...
		PropertyDataAccumulator entireGameTemp;
		Map<Integer,PropertyDataAccumulator> playerEntireGameTemp;
		PropertyDataAccumulator playerTemp;
		
		for(PropertyDataAccumulator p : currentMaxValues.values()){ //for all of the current maxes
			entireGameTemp = entireGameData.get(p.propertyName); //add to the overall property data
			
			if(entireGameTemp == null){
				entireGameTemp = new PropertyDataAccumulator(p.propertyName,-1);
				entireGameData.put(p.propertyName, entireGameTemp);
			}
			
			entireGameTemp.accNumHouses += p.accNumHouses;
			entireGameTemp.accTotalRevenueWithHouses += p.accTotalRevenueWithHouses;
			entireGameTemp.accTotalRevenueWithoutHouses += p.accTotalRevenueWithoutHouses;
			entireGameTemp.numDataPoints += 1;
			
//			PlayerPropertyDataAccumulator temp;
//			for(PlayerPropertyDataAccumulator d : p.getAll()){ //for all the player specific max data
//				
//				temp = entireGameTemp.get(d.playerOwnerID);
//				temp.playerNumHouses += d.playerNumHouses;
//				temp.playerPersonalRevenueWithHouses += d.playerPersonalRevenueWithHouses;
//				temp.playerPersonalRevenueWithoutHouses += d.playerPersonalRevenueWithoutHouses;
//				int divideBy = p.numDataPoints == 0 ? 1 : p.numDataPoints;
//				temp.playerTimeOwned += d.numDataPoints/divideBy;
//				temp.numDataPoints += 1;
//			}
			
//			if(p.numDataPoints == 0){
//				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//				specific.printData();
////				System.out.println(String.format("%d h:%f, rW:%f, rWO:%f", 
////						i++,p.accNumHouses , p.accTotalRevenueWithHouses, p.accTotalRevenueWithoutHouses));
//			}
			
			p.reset(); //reset the currentMaxes
		}
		
		for(Map<Integer,PropertyDataAccumulator> m : playerCurrentMaxValues.values()){
			playerEntireGameTemp = null;
			for(PropertyDataAccumulator p : m.values()){
				
				if(playerEntireGameTemp == null){
					playerEntireGameTemp = playerEntireGameData.get(p.propertyName);
					if(playerEntireGameTemp == null){
						playerEntireGameTemp = new HashMap<>();
						playerEntireGameData.put(p.propertyName, playerEntireGameTemp);
					}
				}
				
				playerTemp = playerEntireGameTemp.get(p.ownerID);
				if(playerTemp == null){
					playerTemp = new PropertyDataAccumulator(p.propertyName,p.ownerID);
					playerEntireGameTemp.put(p.ownerID, playerTemp);
				}
				
				playerTemp.accNumHouses += p.accNumHouses;
				playerTemp.accTotalRevenueWithHouses += p.accTotalRevenueWithHouses;
				playerTemp.accTotalRevenueWithoutHouses += p.accTotalRevenueWithoutHouses;
				int divideBy = currentMaxValues.get(playerTemp.propertyName).numDataPoints;
				divideBy = divideBy == 0 ? 1 : divideBy;
				playerTemp.accTimeOwned += p.numDataPoints/divideBy;
				playerTemp.numDataPoints += 1;
				
				p.reset();
			}
		}
	}
		
	public void putPropertyData(PropertyData data){ //this goes through all time stamps in a game
		//finds the max so far
		PropertyDataAccumulator accData = currentMaxValues.get(data.propertyName);
		if(accData == null){
			accData = new PropertyDataAccumulator(data.propertyName, -1);
			currentMaxValues.put(data.propertyName, accData);
		}
		
		accData.accNumHouses = Math.max(accData.accNumHouses, data.numHouses);
		accData.accTotalRevenueWithHouses = Math.max(accData.accTotalRevenueWithHouses, data.totalRevenueWithHouses);
		accData.accTotalRevenueWithoutHouses = Math.max(accData.accTotalRevenueWithoutHouses, data.totalRevenueWithoutHouses);
		accData.numDataPoints += 1;
		
		////////////////////////////////////////////////////
		
		Map<Integer,PropertyDataAccumulator> allPlayers = playerCurrentMaxValues.get(data.propertyName);
		if(allPlayers == null){
			allPlayers = new HashMap<>();
			playerCurrentMaxValues.put(data.propertyName,allPlayers);
		}
		PropertyDataAccumulator playerData = allPlayers.get(data.ownerID);
		if(playerData == null){
			playerData = new PropertyDataAccumulator(data.propertyName,data.ownerID);
		}
		playerData.accNumHouses = Math.max(playerData.accNumHouses, data.numHouses);
		playerData.accTotalRevenueWithHouses = Math.max(playerData.accTotalRevenueWithHouses, data.personalRevenueWithHouses);
		playerData.accTotalRevenueWithoutHouses = Math.max(playerData.accTotalRevenueWithoutHouses, data.personalRevenueWithoutHouses);
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
		List<PropertyDataReport> tempList;
		Map<String,PropertyDataReport> overallPropertyData = new HashMap<>();
		for(PropertyDataAccumulator p : entireGameData.values()){
//			overallPlayerPropertyData.put(p.propertyName, p.toPlayerPropertyDataReport(p.numDataPoints));
			p.average();
			overallPropertyData.put(p.propertyName, p.toPropertyDataReport());
		}
		
		for(Map<Integer,PropertyDataAccumulator> m : playerEntireGameData.values()){
			tempList = new ArrayList<>();			
			for(PropertyDataAccumulator p : m.values()){
				p.average();
				tempList.add(p.toPropertyDataReport());
			}
			overallPlayerPropertyData.put(tempList.get(0).propertyName, tempList);
		}
		
		return new GameDataReport(times, getPlayerWithMostWins(), overallPlayerPropertyData, overallPropertyData);		
	}
}