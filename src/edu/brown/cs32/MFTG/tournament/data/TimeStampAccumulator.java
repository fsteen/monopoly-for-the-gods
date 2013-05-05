package edu.brown.cs32.MFTG.tournament.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.brown.cs32.MFTG.monopoly.PlayerWealthData;

public class TimeStampAccumulator {

	private Map<Integer, PlayerWealthDataAccumulator> _wealthData;
	public final int _time;
	
	public TimeStampAccumulator(int time){
		_time=time;
		_wealthData = new HashMap<>();
	}
	
	public void setPlayerWealthData(Map<Integer, PlayerWealthDataAccumulator> wealthData){
		_wealthData = wealthData;
	}

	public void putWealthData(PlayerWealthData data){
		//add everything together so that in the end you can average it
		PlayerWealthDataAccumulator accData = _wealthData.get(data.ownerID);
		if(accData == null){
			accData = new PlayerWealthDataAccumulator(data.ownerID);
			_wealthData.put(data.ownerID, accData);
		}
		
		accData.accCash += data.cash;
		accData.accTotalWealth += data.totalWealth;
		accData.numDataPoints += 1;
	}
	
	public List<PlayerWealthDataAccumulator> getAllPlayerWealthData(){
		return new ArrayList<PlayerWealthDataAccumulator>(_wealthData.values());
	}
	
	public void averageWith(TimeStampAccumulator t){
		if(_time != t._time){
			System.out.println("TIMES DON'T MATCH");
		}
		for(PlayerWealthDataAccumulator p : t._wealthData.values()){
			_wealthData.get(p.ownerID).averageWith(p);
		}
	}
	
	public void average(){
		for(PlayerWealthDataAccumulator w : _wealthData.values()){
			w.average();
		}
	}
	
	/**
	 * Converts a TimeStampAccumulator to a TimeStamp
	 * @return
	 */
	public TimeStampReport toTimeStampReport(){		
		Map<Integer,PlayerWealthDataReport> wData = new HashMap<>();
		for(PlayerWealthDataAccumulator w : _wealthData.values()){
			wData.put(w.ownerID, w.toPlayerWealthDataReport());
		}

		return new TimeStampReport(_time,wData);
	}
}