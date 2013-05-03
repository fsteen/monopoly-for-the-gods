package edu.brown.cs32.MFTG.tournament.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GameDataReport {

	public final List<TimeStampReport> _timeStamps; //the average wealth data for players over these time stamps
	public final Map<String, List<PropertyDataReport>> _overallPlayerPropertyData; //the 
	public final Map<String, PropertyDataReport> _overallPropertyData;
	public final Map<Integer, Integer> _playerWins;
	public final List<Integer> _winList;
	
	@JsonCreator
	public GameDataReport(@JsonProperty("timeStamps") List<TimeStampReport> timeStamps,
						  @JsonProperty("playerWins") Map<Integer, Integer> playerWins,
						  @JsonProperty("winList") List<Integer> winList, 
						  @JsonProperty("entireGameData") Map<String, List<PropertyDataReport>> entireGameData,
						  @JsonProperty("overallPropertyData") Map<String, PropertyDataReport> overallPropertyData){
		_timeStamps = timeStamps;
		_playerWins = playerWins;
		_winList = winList;
		_overallPlayerPropertyData = entireGameData;
		_overallPropertyData = overallPropertyData;
	}
	
	public GameDataAccumulator toGameDataAccumulator(){
		GameDataAccumulator g = new GameDataAccumulator(_timeStamps.get(0).wealthData.size(),_playerWins.size()); //the number of players
		List<TimeStampAccumulator> stamps = new ArrayList<>();
		for(TimeStampReport t: _timeStamps){
			stamps.add(t.toTimeStampAccumulator());
		}
		
		Map<String, PropertyDataAccumulator> tempOverallData = new HashMap<>();
		for(PropertyDataReport p : _overallPropertyData.values()){
			tempOverallData.put(p.propertyName, p.toPropertyDataAccumulator());
		}
		
		Map<String,Map<Integer,PropertyDataAccumulator>> tempOverallPlayerData = new HashMap<>();
		Map<Integer,PropertyDataAccumulator> tempPlayerData;
		for(List<PropertyDataReport> l : _overallPlayerPropertyData.values()){
			tempPlayerData = new HashMap<>();
			for(PropertyDataReport p : l){
				tempPlayerData.put(p.ownerID, p.toPropertyDataAccumulator());
			}
			tempOverallPlayerData.put(l.get(0).propertyName, tempPlayerData);
		}
		g._playerWins = _playerWins;
		g._winList = _winList;
		g.timeStamps = stamps;
		g.entireGameData = tempOverallData;
		g.playerEntireGameData = tempOverallPlayerData;
		
		return g;
	}
	
	@Override
	public String toString(){
		StringBuilder b = new StringBuilder();
		b.append("*****ALL TIMESTAMPS*****\n");
		if(_timeStamps != null){
			for(TimeStampReport t : _timeStamps){
				b.append(t.toString() + "\n");
			}
		}
		b.append("************************\n");
		b.append("************************\n");
		b.append("*****OVERALL PLAYER PROPERTY DATA*****\n");
//		b.append(_overallPlayerPropertyData.entrySet().size() == 0);
		if(_overallPlayerPropertyData != null){
			for(Entry<String,List<PropertyDataReport>> e : _overallPlayerPropertyData.entrySet()){
				for(PropertyDataReport p : e.getValue()){
					if(p == null){
						b.append("{"+e.getKey()+", null}\n");
					} else {
						b.append(p.toString() + "\n");
					}
				}
			}
		} else {
			System.out.println("null");
		}
		b.append("*****OVERALL PROPERTY DATA*****\n");
		if(_overallPropertyData != null){
			for(Entry<String,PropertyDataReport> e : _overallPropertyData.entrySet()){
				if(e.getValue() == null){
					b.append("{"+e.getKey()+", null}\n");
				} else {
					b.append(e.getValue().toString() + "\n");
				}
			}
		}
		b.append("*********************\n\n\n\n");
		
		for(Entry<Integer, Integer> e : _playerWins.entrySet()){
			b.append(String.format("Player %d has %d wins\n",e.getKey(),e.getValue()));
		}
		b.append("winList\n" + _winList + "\n");
		return b.toString();
	}
	
	//TODO change to match Map<String,List<PropertyDataReport>>
	@Override
	public boolean equals(Object o){
		if (o == null || !(o instanceof GameDataReport))
			return false;
		
		GameDataReport that = (GameDataReport) o;
		
		return Objects.equals(_timeStamps, that._timeStamps) && Objects.equals(_overallPlayerPropertyData, that._overallPlayerPropertyData)
				&& Objects.equals(_playerWins, that._playerWins);  //TODO add back in
	}
}