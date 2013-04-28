package edu.brown.cs32.MFTG.tournament.data;

import java.util.Map;
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
	
	@Override
	public boolean equals(Object o){
		if (o == null || !(o instanceof TimeStampReport))
			return false;
		
		TimeStampReport that = (TimeStampReport) o;
		
		return this.time == that.time && Objects.equals(this.wealthData, that.wealthData);
	}
}
