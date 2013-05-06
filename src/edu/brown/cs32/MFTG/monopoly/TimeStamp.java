package edu.brown.cs32.MFTG.monopoly;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * class to hold all data for a given property at any given time
 * @author Jschvime
 *
 */
public class TimeStamp{
	List<PropertyData> _propertyData;
	List<PlayerWealthData> _wealthData;
	int _time;
	
	public TimeStamp(int time){
		_time=time;
		_propertyData = new ArrayList<>();
		_wealthData = new ArrayList<>();
	}
	
	public TimeStamp(){
	}
	
	public int getTime(){
		return _time;
	}
	
	public void setTime(int time){
		_time = time;
	}
	
	/**
	 * 
	 * @return property data at given time
	 */
	public List<PropertyData> getPropertyData(){
		return _propertyData;
	}
	
	/**
	 * A setter for the propertyData attribute
	 * @param propertyData the value to set _propertyData to
	 */
	public void setPropertyData(List<PropertyData> propertyData){
		_propertyData = propertyData;
	}
	
	/**
	 * 
	 * @return wealth data at given time
	 */
	public List<PlayerWealthData> getWealthData(){
		return _wealthData;
	}
	
	/**
	 * Setter for the wealthData attribute
	 * @param wealthData
	 */
	public void setWealthData(List<PlayerWealthData> wealthData){
		_wealthData = wealthData;
	}

	
	/**
	 * Sets property data at the given time
	 * @param propertyName
	 * @param ownerID
	 * @param numHouses
	 * @param revenue
	 * @param mortgaged
	 */
	void addPropertyData(String propertyName, int ownerID, double numHouses, double personalRevenueWithHouses, double personalRevenueWithoutHouses,double totalRevenueWithHouses,double totalRevenueWithoutHouses, boolean mortgaged, boolean monopolized){
		_propertyData.add(new PropertyData(propertyName,ownerID,numHouses, personalRevenueWithHouses, personalRevenueWithoutHouses, totalRevenueWithHouses, totalRevenueWithoutHouses,mortgaged, monopolized));
	}
	
	/**
	 * sets wealth data for the given owner at the given time
	 * @param ownerID
	 * @param cash
	 * @param totalWealth
	 */
	void addWealthData(int ownerID, double cash, double totalWealth){
		_wealthData.add(new PlayerWealthData(ownerID,cash,totalWealth));
	}
	
	@Override
	public boolean equals(Object o){
		if (o == null || !(o instanceof TimeStamp))
			return false;
		
		TimeStamp that = (TimeStamp) o;
		
		return Objects.equals(_propertyData, that.getPropertyData()) && Objects.equals(_wealthData, that.getWealthData())
			&& _time == that.getTime();
	}
}