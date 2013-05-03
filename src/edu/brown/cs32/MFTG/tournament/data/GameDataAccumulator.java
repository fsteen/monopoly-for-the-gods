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
	public Map<Integer, Integer> _playerWins;
	public List<Integer> _winList;
	public final int _numPlayers;

	public GameDataAccumulator(int numTimeStamps, int numPlayers){
		_playerWins = new HashMap<>();
		entireGameData = new HashMap<>();
		currentMaxValues = new HashMap<>();
		playerEntireGameData = new HashMap<>();
		playerCurrentMaxValues = new HashMap<>();
		data = new ArrayList<>();
		_winList = new ArrayList<>();
		_numPlayers = numPlayers;

		for(int i = 0; i < numTimeStamps; i++){
			data.add(new TimeStampAccumulator(i));
		}
		
		for(int i = -1; i < numPlayers; i++){
			_playerWins.put(i, 0);
		}
	}


	/**
	 * Averages this and g, appending g's winList to this's
	 * @param g
	 */
	public void averageWith(GameDataAccumulator g){
		if(g.data.size() != data.size()){
			System.out.println("cannot merge gamedata with different numbers of timestamps");
		}
		for(int i = 0; i < data.size(); i++){
			data.get(i).averageWith(g.data.get(i)); //average all of the timestamps
		}
		for(PropertyDataAccumulator p : entireGameData.values()){ //average all of the overall property values
			p.averageWith(g.entireGameData.get(p.propertyName)); //TODO CHANGED
		}
		for(Map<Integer,PropertyDataAccumulator> m : playerEntireGameData.values()){
			for(PropertyDataAccumulator p : m.values()){
				p.averageWith(g.playerEntireGameData.get(p.propertyName).get(p.ownerID));
			}
		}
		for(Entry<Integer,Integer> e : _playerWins.entrySet()){
			e.setValue(e.getValue() + g._playerWins.get(e.getKey()));
		}
		this._winList.addAll(g._winList);
	}

	/**
	 * puts all the current max values into the entireGameData and playerEntireGameData
	 */
	public void gameFinished(){
		/* initialize everything */
		for(String s : currentMaxValues.keySet()){
			initializeEntireGameData(s);
		}
		
		/* Add the overall property information */
		PropertyDataAccumulator entireGameTemp;
		for(PropertyDataAccumulator p : currentMaxValues.values()){ //for all of the current maxes
			entireGameTemp = entireGameData.get(p.propertyName); //add to the overall property data
			entireGameTemp.accNumHouses += p.accNumHouses;
			entireGameTemp.accTotalRevenueWithHouses += p.accTotalRevenueWithHouses;
			entireGameTemp.accTotalRevenueWithoutHouses += p.accTotalRevenueWithoutHouses;
			entireGameTemp.numDataPoints += 1;
			p.reset(); //reset the currentMaxes
		}
		
		/* Add the player specific property information */
		Map<Integer,PropertyDataAccumulator> playerEntireGameTemp;
		PropertyDataAccumulator playerTemp;
		for(Entry<String,Map<Integer,PropertyDataAccumulator>> e : playerCurrentMaxValues.entrySet()){
			playerEntireGameTemp = playerEntireGameData.get(e.getKey());
			for(PropertyDataAccumulator p : e.getValue().values()){
				playerTemp = playerEntireGameTemp.get(p.ownerID);
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

	/**
	 * Initialize stuff
	 * @param propertyName
	 */
	private void initializeEntireGameData(String propertyName){
		PropertyDataAccumulator accData = entireGameData.get(propertyName);
		if(accData == null){
			accData = new PropertyDataAccumulator(propertyName, -1);
			entireGameData.put(propertyName, accData);
		}
		
		Map<Integer,PropertyDataAccumulator>  playerProperties = playerEntireGameData.get(propertyName);
		PropertyDataAccumulator playerData;

		if(playerProperties == null){
			playerProperties = new HashMap<>();
			for(int i = 0; i < _numPlayers; i++){
				playerData = new PropertyDataAccumulator(propertyName,i);
				playerProperties.put(i, playerData);
			}
			playerEntireGameData.put(propertyName,playerProperties); //add the hashmap if it doesn't yet exist
		}
	}
	
	/**
	 * Initialize stuff
	 * @param propertyName
	 */
	private void initializeCurrentMaxValues(String propertyName){
		PropertyDataAccumulator accData = currentMaxValues.get(propertyName);
		if(accData == null){
			accData = new PropertyDataAccumulator(propertyName, -1);
			currentMaxValues.put(propertyName, accData);
		}
		
		Map<Integer,PropertyDataAccumulator> playerProperties = playerCurrentMaxValues.get(propertyName);
		PropertyDataAccumulator playerData;
		if(playerProperties == null){
			playerProperties = new HashMap<>();
			for(int i = 0; i < _numPlayers; i++){
				playerData = new PropertyDataAccumulator(propertyName,i);
				playerProperties.put(i, playerData);
			}
			playerCurrentMaxValues.put(propertyName,playerProperties); //add the hashmap if it doesn't yet exist
		}
	}

	/**
	 * 
	 * @param data
	 */
	public void putPropertyData(PropertyData data){ //this goes through all time stamps in a game		
		initializeCurrentMaxValues(data.propertyName);
		/* Add the overall property information */
		PropertyDataAccumulator accData = currentMaxValues.get(data.propertyName);
		accData.accNumHouses = Math.max(accData.accNumHouses, data.numHouses);
		accData.accTotalRevenueWithHouses = Math.max(accData.accTotalRevenueWithHouses, data.totalRevenueWithHouses);
		accData.accTotalRevenueWithoutHouses = Math.max(accData.accTotalRevenueWithoutHouses, data.totalRevenueWithoutHouses);
		accData.numDataPoints += 1;
		/* Add the player specific property information */
		if(data.ownerID == -1){ //the property is not owned
			return;
		}
		
		Map<Integer,PropertyDataAccumulator> allPlayers = playerCurrentMaxValues.get(data.propertyName);
		PropertyDataAccumulator playerData = allPlayers.get(data.ownerID);
		playerData.accNumHouses = Math.max(playerData.accNumHouses, data.numHouses);
		playerData.accTotalRevenueWithHouses = Math.max(playerData.accTotalRevenueWithHouses, data.personalRevenueWithHouses);
		playerData.accTotalRevenueWithoutHouses = Math.max(playerData.accTotalRevenueWithoutHouses, data.personalRevenueWithoutHouses);
		playerData.numDataPoints += 1;
	}

	/**
	 * 
	 * @param playerID
	 */
	public void addPlayerWin(int playerID){
		Integer wins = _playerWins.get(playerID);
		if(wins == null){
			wins = 0;
		}
		_playerWins.put(playerID, ++wins);
		_winList.add(playerID);

	}

	public int getPlayerWithMostWins(){
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

	public void average(){
		for(TimeStampAccumulator t : data){
			t.average();
		}

		for(PropertyDataAccumulator d : entireGameData.values()){
			d.average();
		}

		for(Map<Integer,PropertyDataAccumulator> m : playerEntireGameData.values()){
			for(PropertyDataAccumulator p : m.values()){
				p.average();
			}
		}
	}

	/**
	 * Converts a GameDataAccumulator to a GameData
	 * @return
	 */
	public GameDataReport toGameDataReport(){
		System.out.println("here");
		List<TimeStampReport> times = new ArrayList<>();

		for(TimeStampAccumulator t : data){
			times.add(t.toTimeStampReport());
		}

		Map<String,List<PropertyDataReport>> overallPlayerPropertyData = new HashMap<>();
		List<PropertyDataReport> tempList;
		Map<String,PropertyDataReport> overallPropertyData = new HashMap<>();
		for(PropertyDataAccumulator p : entireGameData.values()){
			overallPropertyData.put(p.propertyName, p.toPropertyDataReport());
		}
		for(Map<Integer,PropertyDataAccumulator> m : playerEntireGameData.values()){
			tempList = new ArrayList<>();			
			for(PropertyDataAccumulator p : m.values()){
				if (tempList != null && p != null) tempList.add(p.toPropertyDataReport());
			}
			overallPlayerPropertyData.put(tempList.get(0).propertyName, tempList);
		}

		return new GameDataReport(times, _playerWins,_winList, overallPlayerPropertyData, overallPropertyData);		
	}
}