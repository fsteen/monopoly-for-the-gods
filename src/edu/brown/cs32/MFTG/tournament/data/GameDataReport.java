package edu.brown.cs32.MFTG.tournament.data;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GameDataReport {

	public final List<TimeStampReport> _timeStamps;
	public final Map<String, PropertyDataReport> _entireGameData;
	public final int _winner;
	
	@JsonCreator
	public GameDataReport(@JsonProperty("timeStamps") List<TimeStampReport> timeStamps,
						  @JsonProperty("winner") int winner, 
						  @JsonProperty("entireGameData") Map<String, PropertyDataReport> entireGameData){
		_timeStamps = timeStamps;
		_winner = winner;
		_entireGameData = entireGameData;
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
		for(PropertyDataReport p : _entireGameData.values()){
			b.append(p.toString() + "\n");
		}
		b.append("*********************\n\n\n\n");
		return b.toString();
	}
	
	@Override
	public boolean equals(Object o){
		if (o == null || !(o instanceof GameDataReport))
			return false;
		
		GameDataReport that = (GameDataReport) o;
		
		return Objects.equals(_timeStamps, that._timeStamps) && Objects.equals(_entireGameData, that._entireGameData)
				&& _winner == that._winner; 
	}
}