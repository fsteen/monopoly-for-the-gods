package edu.brown.cs32.MFTG.tournament.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Multimap;

public class GameDataReport {

	public final List<TimeStampReport> _timeStamps;
	public final Multimap<String, PropertyDataReport> _entireGameData;
	public final int _winner;
	
	@JsonCreator
	public GameDataReport(@JsonProperty("timeStamps") List<TimeStampReport> timeStamps,
						  @JsonProperty("winner") int winner, 
						  @JsonProperty("entireGameData") Multimap<String, PropertyDataReport> entireGameData){
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
}