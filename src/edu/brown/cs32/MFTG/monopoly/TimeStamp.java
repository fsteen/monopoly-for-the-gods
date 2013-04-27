package edu.brown.cs32.MFTG.monopoly;

import java.util.ArrayList;

import edu.brown.cs32.MFTG.monopoly.PlayerWealthData;

/**
 * class to hold all data for a given property at any given time
 * @author Jschvime
 *
 */
public class TimeStamp{
	ArrayList<PropertyData> _propertyData;
	ArrayList<PlayerWealthData> _wealthData;
	int _time;
	
	public TimeStamp(int time){
		_time=time;
		_propertyData = new ArrayList<>();
		_wealthData = new ArrayList<>();
	}
	
	public int getTime(){
		return _time;
	}
	
	public void setTime(int t){
		_time = t;
	}
	
	/**
	 * Sets property data at the given time
	 * @param propertyName
	 * @param ownerID
	 * @param numHouses
	 * @param revenue
	 * @param mortgaged
	 */
	void addPropertyData(String propertyName, int ownerID, int numHouses, int personalRevenueWithHouses, int personalRevenueWithoutHouses,int totalRevenueWithHouses,int totalRevenueWithoutHouses, boolean mortgaged){
		_propertyData.add(new PropertyData(propertyName,ownerID,numHouses, personalRevenueWithHouses, personalRevenueWithoutHouses, totalRevenueWithHouses, totalRevenueWithoutHouses,mortgaged));
	}
	
	/**
	 * 
	 * @return property data at given time
	 */
	public ArrayList<PropertyData> getPropertyData(){
		return _propertyData;
	}
	
	/**
	 * A setter for the propertyData attribute
	 * @param propertyData the value to set _propertyData to
	 */
	//TODO i changed this method to public, is that ok Judah?
	public void setPropertyData(ArrayList<PropertyData> propertyData){
		_propertyData = propertyData;
	}
	
	/**
	 * sets wealth data for the given owner at the given time
	 * @param ownerID
	 * @param cash
	 * @param totalWealth
	 */
	void addWealthData(int ownerID, int cash, int totalWealth){
		_wealthData.add(new PlayerWealthData(ownerID,cash,totalWealth));
	}
	
	/**
	 * 
	 * @return wealth data at given time
	 */
	public ArrayList<PlayerWealthData> getWealthData(){
		return _wealthData;
	}
	
	/**
	 * Setter for the wealthData attribute
	 * @param wealthData
	 */
	public void setWealthData(ArrayList<PlayerWealthData> wealthData){
		_wealthData = wealthData;
	}
	
	@Override
	public boolean equals(Object other){
		if(other instanceof TimeStamp){
			TimeStamp otherTime= (TimeStamp)other;
			for(int i=0; i<_propertyData.size();i++) {
				if(otherTime.getPropertyData().get(i)==null) {
					return false;
				}
				if(otherTime.getPropertyData().get(i).equals(_propertyData.get(i))==false) {
					return false;
				}
			}
			for(int j=0; j<_wealthData.size();j++) {
				if(otherTime.getWealthData().get(j)==null) {
					return false;
				}
				if(otherTime.getWealthData().get(j).equals(_wealthData.get(j))==false) {
					return false;
				}
			}
			return true;

		}
		return false;
	}
}