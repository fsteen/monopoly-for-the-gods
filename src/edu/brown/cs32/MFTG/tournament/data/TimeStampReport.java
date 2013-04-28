package edu.brown.cs32.MFTG.tournament.data;

import java.util.Map;

public class TimeStampReport {
	public final Map<Integer, PlayerWealthDataReport> wealthData;
	public final int time;
	
	public TimeStampReport(int time, Map<Integer, PlayerWealthDataReport> wealthData){
		this.wealthData = wealthData;
		this.time = time;
	}
	
	@Override
	public String toString(){
		StringBuilder b = new StringBuilder();
		b.append("-----TIME STAMPS-----\n");
		for(PlayerWealthDataReport w : wealthData.values()){
			b.append(w.toString() + "\n");
		}
		b.append("---------------------\n");
		return b.toString();
	}
}
