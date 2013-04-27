package edu.brown.cs32.MFTG.tournament.data;

import java.util.Map;

public class TimeStampReport {
	public final Map<Integer, PlayerWealthDataReport> wealthData;
	public final int time;
	
	public TimeStampReport(int time, Map<Integer, PlayerWealthDataReport> wealthData){
		this.wealthData = wealthData;
		this.time = time;
	}
}
