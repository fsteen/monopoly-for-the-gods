package edu.brown.cs32.MFTG.tournament.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.TimeStamp;

public class GameDataAccumulator {

	public List<TimeStampAccumulator> data;
	private Map<Integer, Integer> _playerWins;
	
	public GameDataAccumulator(int numTimeStamps){
		data = new ArrayList<>();
		
		for(int i = 0; i < numTimeStamps; i++){
			data.add(new TimeStampAccumulator(i));
		}
		
		_playerWins = new HashMap<>();
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
	public GameData toGameData(){
		GameData gameData = new GameData(_playerWins.keySet().size()); //infer the number of players
		ArrayList<TimeStamp> times = new ArrayList<>();
		
		for(TimeStampAccumulator t : data){
			times.add(t.toTimeStamp());
		}
		
		gameData.setData(times);
		gameData.setWinner(getPlayerWithMostWins());
		return gameData;
	}
}