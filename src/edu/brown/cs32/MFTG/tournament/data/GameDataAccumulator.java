package edu.brown.cs32.MFTG.tournament.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.brown.cs32.MFTG.monopoly.PropertyData;
import edu.brown.cs32.MFTG.tournament.BackendConstants;
import edu.brown.cs32.MFTG.tournament.Settings.WinningCondition;


public class GameDataAccumulator {
	public List<TimeStampAccumulator> timeStamps;
	public Map<String, PropertyDataAccumulator> entireGameData;
	private Map<String, PropertyDataAccumulator> currentMaxValues;
	public Map<String, Map<Integer,PropertyDataAccumulator>> playerEntireGameData;
	private Map<String, Map<Integer,PropertyDataAccumulator>> playerCurrentMaxValues;
	public Map<Integer, Double> _playerWins;
	public Map<Integer,Integer> _winList;
	public boolean matchIsOver;

	public GameDataAccumulator(int numTimeStamps){
		entireGameData = new HashMap<>();
		currentMaxValues = new HashMap<>();
		playerEntireGameData = new HashMap<>();
		playerCurrentMaxValues = new HashMap<>();
		_winList = new HashMap<>();
		matchIsOver = false;

		/* initialize timestamps */
		timeStamps = new ArrayList<>();
		for(int i = 0; i < numTimeStamps; i++){
			timeStamps.add(new TimeStampAccumulator(i));
		}
		
		/* initialize player wins */
		_playerWins = new HashMap<>();
		for(int i = -1; i < BackendConstants.MAX_NUM_PLAYERS; i++){
			_playerWins.put(i, 0.);
		}
	}

	/**
	 * Combines this and g, averaging mainly, but appending g's winList to this's
	 * @param g the accumulator whose information is transfered over to this one's
	 */
	public void combineWith(GameDataAccumulator g){
		if(g.timeStamps.size() == timeStamps.size()){
			for(int i = 0; i < timeStamps.size(); i++){
				timeStamps.get(i).averageWith(g.timeStamps.get(i));
			}
		}
		
		if(g.entireGameData.size() == entireGameData.size()){
			for(PropertyDataAccumulator p : entireGameData.values()){
				p.averageWith(g.entireGameData.get(p.propertyName));
			}
		}
		
		if(g.playerEntireGameData.size() == playerEntireGameData.size()){
			for(Map<Integer,PropertyDataAccumulator> m : playerEntireGameData.values()){
				for(PropertyDataAccumulator p : m.values()){
					p.averageWith(g.playerEntireGameData.get(p.propertyName).get(p.ownerID));
				}
			}
		}
		
		if(g._playerWins.size() == _playerWins.size()){
			for(Entry<Integer,Double> e : _playerWins.entrySet()){
				e.setValue(e.getValue() + g._playerWins.get(e.getKey()));
			}
		}
		
		for(Entry<Integer,Integer> e : g._winList.entrySet()){
			_winList.put(e.getKey(),e.getValue());
		}		
	}

	/**
	 * puts all the current max values into the entireGameData and playerEntireGameData
	 */
	public void gameFinished(){
		/* initialize everything */
		PropertyDataAccumulator temp;
		Map<Integer,PropertyDataAccumulator> playerEntireGameTemp;
		int totalNumDataPoints = 0;
		for(String s : currentMaxValues.keySet()){
			initializeEntireGameData(s);
		}
		
		/* Add the overall property information */
		for(PropertyDataAccumulator p : currentMaxValues.values()){
			temp = entireGameData.get(p.propertyName);
			temp.accNumHouses += p.accNumHouses;
			temp.accTotalRevenueWithHouses += p.accTotalRevenueWithHouses;
			temp.accTotalRevenueWithoutHouses += p.accTotalRevenueWithoutHouses;
			temp.numDataPoints += 1;
			totalNumDataPoints = p.numDataPoints;
			p.reset();
		}
		totalNumDataPoints = totalNumDataPoints == 0 ? 1 : totalNumDataPoints;
		
		/* Add the player specific property information */
		for(Entry<String,Map<Integer,PropertyDataAccumulator>> e : playerCurrentMaxValues.entrySet()){
			playerEntireGameTemp = playerEntireGameData.get(e.getKey());
			for(PropertyDataAccumulator p : e.getValue().values()){
				temp = playerEntireGameTemp.get(p.ownerID);
				temp.accNumHouses += p.accNumHouses;
				temp.accTotalRevenueWithHouses += p.accTotalRevenueWithHouses;
				temp.accTotalRevenueWithoutHouses += p.accTotalRevenueWithoutHouses;
				temp.accTimeOwned += ((double)p.numDataPoints)/totalNumDataPoints;
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
			for(int i = 0; i < BackendConstants.MAX_NUM_PLAYERS; i++){
				playerData = new PropertyDataAccumulator(propertyName,i);
				playerProperties.put(i, playerData);
			}
			playerEntireGameData.put(propertyName,playerProperties);
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
			for(int i = 0; i < BackendConstants.MAX_NUM_PLAYERS; i++){
				temp = new PropertyDataAccumulator(propertyName,i);
				playerProperties.put(i, temp);
			}
			playerCurrentMaxValues.put(propertyName,playerProperties);
		}
	}

	/**
	 * Goes through all time stamps in a game
	 * @param data
	 */
	public void putPropertyData(PropertyData data){		
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
		playerData.numDataPoints += data.monopolized ? 1 : 0;
	}

	/**
	 * 
	 * @param playerID
	 */
	public void addPlayerWin(int gameNum, int playerID){
		Double wins = _playerWins.get(playerID);
		if(wins == null){
			wins = 0.;
		}
		_playerWins.put(playerID, ++wins);
		_winList.put(gameNum, playerID);

	}
	
	/**
	 * 
	 * @return a pair of the winner id and the number of wins
	 */
	public Pair<Integer,Double> getPlayerWithMostGamesWon(WinningCondition winType){
		Pair<Integer,Double> winner = new Pair<>(-2,-2.);
		if(winType == WinningCondition.MOST_MONEY){
			for(PlayerWealthDataAccumulator p : timeStamps.get(timeStamps.size()-1).getAllPlayerWealthData()){
				if(p.accTotalWealth > winner.getRight()){
					winner.setLeft(p.ownerID);
					winner.setRight(p.accTotalWealth);
				}
			}
		} else {
			for(Entry<Integer, Double> current : _playerWins.entrySet()){
				if(current.getValue() > winner.getRight()){
					winner.setLeft(current.getKey());
					winner.setRight(current.getValue());
				}
			}
		}
		return winner;
	}
	
	/**
	 * 
	 * @return
	 */
	public Map<Integer,Double> getEndOfGameWealth(){
		List<PlayerWealthDataAccumulator> wealth = timeStamps.get(timeStamps.size()-1).getAllPlayerWealthData();
		Map<Integer,Double> wealthMap = new HashMap<>();
		for(PlayerWealthDataAccumulator w : wealth){
			wealthMap.put(w.ownerID, w.accTotalWealth);
		}
		return wealthMap;
	}

	/**
	 * 
	 */
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
		return new GameDataReport(times, _playerWins,_winList, overallPlayerPropertyData, overallPropertyData,matchIsOver);		
	}
}