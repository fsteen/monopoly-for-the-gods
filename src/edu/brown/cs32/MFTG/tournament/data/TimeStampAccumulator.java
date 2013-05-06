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

	public List<PlayerWealthDataAccumulator> getAllPlayerWealthData(){
		return new ArrayList<PlayerWealthDataAccumulator>(_wealthData.values());
	}
	
	/**
	 * Adds the values in data to the values in this
	 * @param data
	 */
	public void putWealthData(PlayerWealthData data){
		PlayerWealthDataAccumulator accData = _wealthData.get(data.ownerID);
		if(accData == null){
			accData = new PlayerWealthDataAccumulator(data.ownerID);
			_wealthData.put(data.ownerID, accData);
		}
		accData.accCash += data.cash;
		accData.accTotalWealth += data.totalWealth;
		accData.numDataPoints += 1;
	}

	/**
	 * Divides all of the data in this by the number of data points
	 */
	public void average(){
		for(PlayerWealthDataAccumulator w : _wealthData.values()){
			w.average();
		}
	}
	
	/**
	 * Combines this and t, averaging mainly
	 * @param t the time stamp whose information is transfered over to this one's
	 */
	public void averageWith(TimeStampAccumulator t){
		if(_time != t._time){
			System.err.println("Error : TimeStampAccumulator times do not match");
		}
		if(t._wealthData.size() == _wealthData.size()){
			for(PlayerWealthDataAccumulator p : t._wealthData.values()){
				_wealthData.get(p.ownerID).averageWith(p);
			}
		}
	}
	
	/**
	 * Converts a TimeStampAccumulator to a TimeStampReport
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