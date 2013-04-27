package edu.brown.cs32.MFTG.tournament.data;

import java.util.List;

public class GameDataReport {

	public final List<TimeStampReport> _data;
	public final int _winner;
	
	public GameDataReport(List<TimeStampReport> data, int winner){
		_data = data;
		_winner = winner;
	}
}