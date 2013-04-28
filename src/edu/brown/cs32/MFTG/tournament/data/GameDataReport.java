package edu.brown.cs32.MFTG.tournament.data;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GameDataReport {

	public final List<TimeStampReport> _timeStamps;
	public final Map<String, PropertyDataAccumulator> _entireGameData;
	public final int _winner;
	
	@JsonCreator
	public GameDataReport(@JsonProperty("timeStamps") List<TimeStampReport> timeStamps,
						  @JsonProperty("winner") int winner, 
						  @JsonProperty("entireGameData") Map<String, PropertyDataAccumulator> entireGameData){
		_timeStamps = timeStamps;
		_winner = winner;
		_entireGameData = entireGameData;
	}
}