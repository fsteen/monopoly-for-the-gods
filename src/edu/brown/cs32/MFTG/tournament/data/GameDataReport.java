package edu.brown.cs32.MFTG.tournament.data;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GameDataReport {

	public final List<TimeStampReport> _timeStamps;
	public final Map<String, List<PropertyDataReport>> _overallPlayerPropertyData;
	public final Map<String, PropertyDataReport> _overallPropertyData;
	public final int _winner;
	
	@JsonCreator
	public GameDataReport(@JsonProperty("timeStamps") List<TimeStampReport> timeStamps,
						  @JsonProperty("winner") int winner, 
						  @JsonProperty("entireGameData") Map<String, List<PropertyDataReport>> entireGameData,
						  @JsonProperty("overallPropertyData") Map<String, PropertyDataReport> overallPropertyData){
		_timeStamps = timeStamps;
		_winner = winner;
		_overallPlayerPropertyData = entireGameData;
		_overallPropertyData = overallPropertyData;
	}
	
	@Override
	public String toString(){
		StringBuilder b = new StringBuilder();
		b.append("*****ALL TIMESTAMPS*****\n");
		for(TimeStampReport t : _timeStamps){
			b.append(t.toString() + "\n");
		}
		b.append("************************\n");
		b.append("************************\n");
		b.append("*****OVERALL DATA*****\n");
		for(List<PropertyDataReport> l : _overallPlayerPropertyData.values()){
			for(PropertyDataReport p : l){
				b.append(p.toString() + "\n");
			}
		}
		for(PropertyDataReport p : _overallPropertyData.values()){
			b.append(p.toString() + "\n");
		}
		b.append("*********************\n\n\n\n");
		return b.toString();
	}
	
	//TODO change to match Map<String,List<PropertyDataReport>>
	@Override
	public boolean equals(Object o){
		if (o == null || !(o instanceof GameDataReport))
			return false;
		
		GameDataReport that = (GameDataReport) o;
		
		return Objects.equals(_timeStamps, that._timeStamps) && Objects.equals(_overallPlayerPropertyData, that._overallPlayerPropertyData)
				&& _winner == that._winner; 
	}
}