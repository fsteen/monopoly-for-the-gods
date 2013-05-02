package edu.brown.cs32.MFTG.tournament.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TimeStampReport {
	public final Map<Integer, PlayerWealthDataReport> wealthData;
	public final int time;
	
	@JsonCreator
	public TimeStampReport(@JsonProperty("time") int time, @JsonProperty("wealthData") Map<Integer, PlayerWealthDataReport> wealthData){
		this.wealthData = wealthData;
		this.time = time;
	}
	
	public TimeStampAccumulator toTimeStampAccumulator(){
		TimeStampAccumulator t = new TimeStampAccumulator(time);
		
		Map<Integer, PlayerWealthDataAccumulator> tempMap = new HashMap<>();
		for(PlayerWealthDataReport p : wealthData.values()){
			tempMap.put(p.ownerID, p.toPlayerWealthDataAccumulator());
		}
		
		t.setPlayerWealthData(tempMap);
		
		return t;
	}
	
	@Override
	public boolean equals(Object o){
		if (o == null || !(o instanceof TimeStampReport))
			return false;
		
		TimeStampReport that = (TimeStampReport) o;
		
		return this.time == that.time && Objects.equals(this.wealthData, that.wealthData);
	}
	
	public String toString(){
		StringBuilder b = new StringBuilder();
		b.append("-----TIME STAMP " + time + "-----\n");
		for(PlayerWealthDataReport w : wealthData.values()){
			b.append(w.toString() + "\n");
		}
		b.append("---------------------\n");
		return b.toString();
	}
}
