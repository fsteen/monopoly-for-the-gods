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
	public Map<String, PropertyDataAccumulator> setPropertyData, gamePropertyData;
	public Map<String, Map<Integer,PropertyDataAccumulator>> setPlayerPropertyData, gamePlayerPropertyData;
	public Map<Integer, Double> playerWins;
	public Map<Integer,Integer> winList;
	public boolean matchIsOver;

	public GameDataAccumulator(int numTimeStamps){
		setPropertyData = new HashMap<>();
		gamePropertyData = new HashMap<>();
		setPlayerPropertyData = new HashMap<>();
		gamePlayerPropertyData = new HashMap<>();
		winList = new HashMap<>();
		matchIsOver = false;

		/* initialize time stamps */
		timeStamps = new ArrayList<>();
		for(int i = 0; i < numTimeStamps; i++){
			timeStamps.add(new TimeStampAccumulator(i));
		}
		
		/* initialize player wins */
		playerWins = new HashMap<>();
		for(int i = -1; i < BackendConstants.MAX_NUM_PLAYERS; i++){
			playerWins.put(i, 0.);
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
		
		if(g.setPropertyData.size() == setPropertyData.size()){
			for(PropertyDataAccumulator p : setPropertyData.values()){
				p.averageWith(g.setPropertyData.get(p.propertyName));
			}
		}
		
		if(g.setPlayerPropertyData.size() == setPlayerPropertyData.size()){
			for(Map<Integer,PropertyDataAccumulator> m : setPlayerPropertyData.values()){
				for(PropertyDataAccumulator p : m.values()){
					p.averageWith(g.setPlayerPropertyData.get(p.propertyName).get(p.ownerID));
				}
			}
		}
		
		if(g.playerWins.size() == playerWins.size()){
			for(Entry<Integer,Double> e : playerWins.entrySet()){
				e.setValue(e.getValue() + g.playerWins.get(e.getKey()));
			}
		}
		
		for(Entry<Integer,Integer> e : g.winList.entrySet()){
			winList.put(e.getKey(),e.getValue());
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
		for(String s : gamePropertyData.keySet()){
			initializeSetSpecificData(s);
		}
		
		/* Add the overall property information */
		for(PropertyDataAccumulator p : gamePropertyData.values()){
			temp = setPropertyData.get(p.propertyName);
			temp.accNumHouses += p.accNumHouses;
			temp.accTotalRevenueWithHouses += p.accTotalRevenueWithHouses;
			temp.accTotalRevenueWithoutHouses += p.accTotalRevenueWithoutHouses;
			temp.numDataPoints += 1;
			totalNumDataPoints = p.numDataPoints;
			p.reset();
		}
		totalNumDataPoints = totalNumDataPoints == 0 ? 1 : totalNumDataPoints;
		
		/* Add the player specific property information */
		for(Entry<String,Map<Integer,PropertyDataAccumulator>> e : gamePlayerPropertyData.entrySet()){
			playerEntireGameTemp = setPlayerPropertyData.get(e.getKey());
			for(PropertyDataAccumulator p : e.getValue().values()){
				temp = playerEntireGameTemp.get(p.ownerID);
				temp.accNumHouses += p.accNumHouses;
				temp.accTotalRevenueWithHouses += p.accTotalRevenueWithHouses;
				temp.accTotalRevenueWithoutHouses += p.accTotalRevenueWithoutHouses;
				//temp.accTimeOwned += ((double)p.numDataPoints)/totalNumDataPoints; //average % time owned during game
				temp.accTimeOwned += 1; //for now, we store the total # data points here
				temp.numDataPoints += p.numDataPoints > 0 ? 1 : 0; //increment data points if it was owned in a given game
				p.reset();
			}
		}
	}

	/**
	 * Initialize set specific accumulators for the given property
	 * @param propertyName the name of the property
	 */
	private void initializeSetSpecificData(String propertyName){
		PropertyDataAccumulator accData = setPropertyData.get(propertyName);
		if(accData == null){
			accData = new PropertyDataAccumulator(propertyName, -1);
			setPropertyData.put(propertyName, accData);
		}
		
		Map<Integer,PropertyDataAccumulator>  playerProperties = setPlayerPropertyData.get(propertyName);
		PropertyDataAccumulator playerData;

		if(playerProperties == null){
			playerProperties = new HashMap<>();
			for(int i = 0; i < BackendConstants.MAX_NUM_PLAYERS; i++){
				playerData = new PropertyDataAccumulator(propertyName,i);
				playerProperties.put(i, playerData);
			}
			setPlayerPropertyData.put(propertyName,playerProperties);
		}
	}
	
	/**
	 * Initialize game specific accumulators for the given property
	 * @param propertyName the name of the property
	 */
	private void initializeGameSpecificValues(String propertyName){
		PropertyDataAccumulator temp = gamePropertyData.get(propertyName);
		if(temp == null){
			temp = new PropertyDataAccumulator(propertyName, -1);
			gamePropertyData.put(propertyName, temp);
		}
		
		Map<Integer,PropertyDataAccumulator> playerProperties = gamePlayerPropertyData.get(propertyName);
		if(playerProperties == null){
			playerProperties = new HashMap<>();
			for(int i = 0; i < BackendConstants.MAX_NUM_PLAYERS; i++){
				temp = new PropertyDataAccumulator(propertyName,i);
				playerProperties.put(i, temp);
			}
			gamePlayerPropertyData.put(propertyName,playerProperties);
		}
	}

	/**
	 * Combines the values from data with the current values,
	 * taking the max in most cases
	 * @param data the data with which to update the current values
	 */
	public void putPropertyData(PropertyData data){		
		initializeGameSpecificValues(data.propertyName);
		
		/* Add the overall property information */
		PropertyDataAccumulator accData = gamePropertyData.get(data.propertyName);
		accData.accNumHouses = Math.max(accData.accNumHouses, data.numHouses);
		accData.accTotalRevenueWithHouses = Math.max(accData.accTotalRevenueWithHouses, data.totalRevenueWithHouses);
		accData.accTotalRevenueWithoutHouses = Math.max(accData.accTotalRevenueWithoutHouses, data.totalRevenueWithoutHouses);
		accData.numDataPoints += 1;
		
		/* Add the player specific property information */
		if(data.ownerID == -1){ //the property is not owned
			return;
		}
		
		Map<Integer,PropertyDataAccumulator> allPlayers = gamePlayerPropertyData.get(data.propertyName);
		PropertyDataAccumulator playerData = allPlayers.get(data.ownerID);
		playerData.accNumHouses = Math.max(playerData.accNumHouses, data.numHouses);
		playerData.accTotalRevenueWithHouses = Math.max(playerData.accTotalRevenueWithHouses, data.personalRevenueWithHouses);
		playerData.accTotalRevenueWithoutHouses = Math.max(playerData.accTotalRevenueWithoutHouses, data.personalRevenueWithoutHouses);
		playerData.numDataPoints += 1;
		//playerData.numDataPoints += data.monopolized ? 1 : 0; //for numHouses, we want average num houses when monopolized
	}

	/**
	 * Records the player win
	 * @param gameNum the id of the game that the player won
	 * @param playerID the id of the player
	 */
	public void addPlayerWin(int gameNum, int playerID){
		Double wins = playerWins.get(playerID);
		if(wins == null){
			wins = 0.;
		}
		playerWins.put(playerID, ++wins);
		winList.put(gameNum, playerID);
	}
	
	/**
	 * Finds the player id and value of the player with the most wins
	 * @param winType how a win is determined
	 * @return the player and win value
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
			for(Entry<Integer, Double> current : playerWins.entrySet()){
				if(current.getValue() > winner.getRight()){
					winner.setLeft(current.getKey());
					winner.setRight(current.getValue());
				}
			}
		}
		return winner;
	}

	/**
	 * Divides the values in this by the number of data points
	 */
	public void average(){
		for(TimeStampAccumulator t : timeStamps){
			t.average();
		}

		for(PropertyDataAccumulator d : setPropertyData.values()){
			d.average();
		}

		for(Map<Integer,PropertyDataAccumulator> m : setPlayerPropertyData.values()){
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
		for(PropertyDataAccumulator p : setPropertyData.values()){
			overallPropertyData.put(p.propertyName, p.toPropertyDataReport());
		}
		
		for(Map<Integer,PropertyDataAccumulator> m : setPlayerPropertyData.values()){
			tempList = new ArrayList<>();			
			for(PropertyDataAccumulator p : m.values()){
				if (tempList != null && p != null) tempList.add(p.toPropertyDataReport());
			}
			overallPlayerPropertyData.put(tempList.get(0).propertyName, tempList);
		}
		return new GameDataReport(times, playerWins,winList, overallPlayerPropertyData,
				overallPropertyData,matchIsOver);		
	}
}


/*
What is being calculated:









*/