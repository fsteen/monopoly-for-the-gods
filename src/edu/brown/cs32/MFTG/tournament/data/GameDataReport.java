package edu.brown.cs32.MFTG.tournament.data;

import java.util.List;
import java.util.Map;

public class GameDataReport {

	public final List<TimeStampReport> _timeStamps;
	public final Map<String, PropertyDataAccumulator> _entireGameData;
	public final int _winner;
	
	public GameDataReport(List<TimeStampReport> timeStamps, int winner, Map<String, PropertyDataAccumulator> entireGameData){
		_timeStamps = timeStamps;
		_winner = winner;
		_entireGameData = entireGameData;
	}
}