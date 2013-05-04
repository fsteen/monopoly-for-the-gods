package edu.brown.cs32.MFTG.tournament.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ArrayListMultimap;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.PlayerWealthData;
import edu.brown.cs32.MFTG.monopoly.PropertyData;

public class GameDataAccumulator {

	public List<TimeStampAccumulator> timeStamps;
	public Map<String, PropertyDataAccumulator> entireGameData;
	private Map<String, PropertyDataAccumulator> currentMaxValues;

	public Map<String, Map<Integer,PropertyDataAccumulator>> playerEntireGameData;
	private Map<String, Map<Integer,PropertyDataAccumulator>> playerCurrentMaxValues;
	public Map<Integer, Double> _playerWins;
	public List<Integer> _winList;
	public final int _numPlayers;
	public final int MAX_NUM_PLAYERS=4;

	public GameDataAccumulator(int numTimeStamps, int numPlayers){
		_playerWins = new HashMap<>();
		entireGameData = new HashMap<>();
		currentMaxValues = new HashMap<>();
		playerEntireGameData = new HashMap<>();
		playerCurrentMaxValues = new HashMap<>();
		timeStamps = new ArrayList<>();
		_winList = new ArrayList<>();
		_numPlayers = numPlayers;

		/* initialize timestamps */
		for(int i = 0; i < numTimeStamps; i++){
			timeStamps.add(new TimeStampAccumulator(i));
		}
		
		/* initialize winners */
		for(int i = -1; i < MAX_NUM_PLAYERS; i++){
			_playerWins.put(i, 0.);
		}
	}

	/**
	 * Averages this and g, appending g's winList to this's
	 * @param g
	 */
	public void combineWith(GameDataAccumulator g){
		if(g == null){
			System.out.println("Cannot merge GameDataAcumulators with a null GameDataAccumulator.");
		}
		if(g.timeStamps.size() != timeStamps.size()){
			System.out.println("Cannot merge GameDataAcumulators with different numbers of timestamps.");
		}
		
		for(int i = 0; i < timeStamps.size(); i++){
			timeStamps.get(i).averageWith(g.timeStamps.get(i));
		}
		for(PropertyDataAccumulator p : entireGameData.values()){
			p.averageWith(g.entireGameData.get(p.propertyName));
		}
		for(Map<Integer,PropertyDataAccumulator> m : playerEntireGameData.values()){
			for(PropertyDataAccumulator p : m.values()){
				p.averageWith(g.playerEntireGameData.get(p.propertyName).get(p.ownerID));
			}
		}
		for(Entry<Integer,Double> e : _playerWins.entrySet()){
			e.setValue(e.getValue() + g._playerWins.get(e.getKey()));
		}
		this._winList.addAll(g._winList);
	}

	/**
	 * puts all the current max values into the entireGameData and playerEntireGameData
	 */
	public void gameFinished(){
		PropertyDataAccumulator temp;
		Map<Integer,PropertyDataAccumulator> playerEntireGameTemp;
		/* initialize everything */
		for(String s : currentMaxValues.keySet()){
			initializeEntireGameData(s);
		}
		
		/* Add the overall property information */
		for(PropertyDataAccumulator p : currentMaxValues.values()){ //for all of the current maxes
			temp = entireGameData.get(p.propertyName); //add to the overall property data
			temp.accNumHouses += p.accNumHouses;
			temp.accTotalRevenueWithHouses += p.accTotalRevenueWithHouses;
			temp.accTotalRevenueWithoutHouses += p.accTotalRevenueWithoutHouses;
			temp.numDataPoints += 1;
			p.reset(); //reset the currentMaxes
		}
		
		/* Add the player specific property information */
		for(Entry<String,Map<Integer,PropertyDataAccumulator>> e : playerCurrentMaxValues.entrySet()){
			playerEntireGameTemp = playerEntireGameData.get(e.getKey());
			for(PropertyDataAccumulator p : e.getValue().values()){
				temp = playerEntireGameTemp.get(p.ownerID);
				temp.accNumHouses += p.accNumHouses;
				temp.accTotalRevenueWithHouses += p.accTotalRevenueWithHouses;
				temp.accTotalRevenueWithoutHouses += p.accTotalRevenueWithoutHouses;
				int divideBy = currentMaxValues.get(temp.propertyName).numDataPoints;
				divideBy = divideBy == 0 ? 1 : divideBy;
				temp.accTimeOwned += p.numDataPoints/divideBy;
				temp.numDataPoints += 1;
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
		PropertyDataAccumulator temp = currentMaxValues.get(propertyName);
		if(temp == null){
			temp = new PropertyDataAccumulator(propertyName, -1);
			currentMaxValues.put(propertyName, temp);
		}
		
		Map<Integer,PropertyDataAccumulator> playerProperties = playerCurrentMaxValues.get(propertyName);
		if(playerProperties == null){
			playerProperties = new HashMap<>();
			for(int i = 0; i < _numPlayers; i++){
				temp = new PropertyDataAccumulator(propertyName,i);
				playerProperties.put(i, temp);
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
		Double wins = _playerWins.get(playerID);
		if(wins == null){
			wins = 0.;
		}
		_playerWins.put(playerID, ++wins);
		_winList.add(playerID);

	}
	
	/**
	 * 
	 * @return a pair of the winner id and the number of wins
	 */
	public Pair<Integer,Double> getMostGamesWonPlayer(){
		Pair<Integer,Double> winner = new Pair<>(-2,-2.);
		for(Entry<Integer, Double> current : _playerWins.entrySet()){
			if(current.getValue() > winner.getRight()){
				winner.setLeft(current.getKey());
				winner.setRight(current.getValue());
			}
		}
		return winner;
	}
	
	public Pair<Integer,Double> getGreatestAverageWealthPlayer(){
		Pair<Integer,Double> winner = new Pair<>(-2,-2.);
		for(PlayerWealthDataAccumulator p : timeStamps.get(timeStamps.size()-1).getAllPlayerWealthData()){
			if(p.accTotalWealth > winner.getRight()){
				winner.setLeft(p.ownerID);
				winner.setRight(p.accTotalWealth);
			}
		}
		return winner;
	}
	
	public Map<Integer,Double> getEndOfGameWealth(){
		List<PlayerWealthDataAccumulator> wealth = timeStamps.get(timeStamps.size()-1).getAllPlayerWealthData();
		Map<Integer,Double> wealthMap = new HashMap<>();
		for(PlayerWealthDataAccumulator w : wealth){
			wealthMap.put(w.ownerID, w.accTotalWealth);
		}
		return wealthMap;
	}

	public void average(){
		for(TimeStampAccumulator t : timeStamps){
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

		for(TimeStampAccumulator t : timeStamps){
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