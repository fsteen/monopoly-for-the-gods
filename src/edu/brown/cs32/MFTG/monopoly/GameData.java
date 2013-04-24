package edu.brown.cs32.MFTG.monopoly;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

/**
 * Models game data, game
 * @author JudahSchvimer
 *
 */
public class GameData {
	private ArrayList<TimeStamp> _data;
	private int _time;
	public final int _numPlayers;
	private int _winner;
	
	/**
	 * Constructs the game data objects
	 * @param numPlayers
	 */
	@JsonCreator
	public GameData(@JsonProperty("numPlayers") int numPlayers){
		_numPlayers=numPlayers;
		
		//this is a list of players, each player has a list 
		_data = new ArrayList<>();
		_time=-1;
		
		//list of players where for each player it has a list of times
		//each time has a list of wealth data
		//data (in order): cash, total wealth
		_data = new ArrayList<>();
	}
	
	/**
	 * Getter for the time attribute
	 * @return _time
	 */
	public int getTime(){
		return _time;
	}
	
	/**
	 * 
	 * @param t the _time to set
	 */
	public void setTime(int t){
		_time = t;
	}
	
	/**
	 * @return the _winner
	 */
	public int getWinner() {
		return _winner;
	}

	/**
	 * @param _winner the _winner to set
	 */
	public void setWinner(int winner) {
		_winner = winner;
	}
	
	/**
	 * called to add a new time to the gamedata objects
	 */
	void addNewTime(){
		_time++;
		_data.add(new TimeStamp(_time));
	}
	
	/**
	 * 
	 * @return list of wealth data
	 */
	public ArrayList<TimeStamp> getData(){
		return _data;
	}
	
	/**
	 * Setter for the data attribute
	 * @param data the data to set _data to
	 */
	public void setData(ArrayList<TimeStamp> data){
		_data = data;
	}
	
	/**
	 * Sets data for a property at the given time
	 * @param propertyName
	 * @param ownerID
	 * @param numHouses
	 * @param revenue
	 * @param mortgaged
	 */
	void setPropertyAtTime(String propertyName, int ownerID, int numHouses,  int personalRevenueWithHouses, int personalRevenueWithoutHouses,int totalRevenueWithHouses,int totalRevenueWithoutHouses, boolean mortgaged){
		_data.get(_time).addPropertyData(propertyName,ownerID,numHouses,personalRevenueWithHouses, personalRevenueWithoutHouses, totalRevenueWithHouses, totalRevenueWithoutHouses,mortgaged);
	}
	
	/**
	 * sets data for the wealth of an owner at the time
	 * @param ownerID
	 * @param cash
	 * @param totalWealth
	 */
	void setWealthAtTime(int ownerID, int cash, int totalWealth){
		_data.get(_time).addWealthData(ownerID,cash,totalWealth);
	}
	
	public void printData() {
		for(TimeStamp t: _data) {
			System.out.println(t._time);
			for(PropertyData prop: t.getPropertyData()) {
				System.out.println(String.format("Property: %s OwnerID: %s numHouses: %d personalRevenueWithHouses: %d personalRevenueWithoutHouses: %d totalRevenueWithHouses: %d totalRevenueWithoutHouses: %d morgaged: %b", prop.propertyName, prop.ownerID, prop.numHouses, prop.personalRevenueWithHouses, prop.personalRevenueWithoutHouses, prop.totalRevenueWithHouses, prop.totalRevenueWithoutHouses, prop.mortgaged));
			}
		}
		for(TimeStamp t: _data) {
			System.out.println(t._time);
			for(PlayerWealthData play: t.getWealthData()) {
				System.out.println(String.format("OwnerID: %s Cash: %d TotalWealth: %d", play.ownerID, play.cash, play.totalWealth));

			}
		}

	}
	
	@Override
	public boolean equals(Object o){
		if (o == null || !(o instanceof GameData))
			return false;
		
		GameData that = (GameData) o;
		
		return java.util.Objects.equals(_data, that.getData()) && _time == that.getTime() && _numPlayers == that._numPlayers && _winner == that.getWinner();
	}
	
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
		void setPropertyData(ArrayList<PropertyData> propertyData){
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
	
	/**
	 * class to hold all data for a owner at a given time
	 * @author Jschvime
	 *
	 */
	public class PlayerWealthData{
		public final  int ownerID;
		public final int cash;
		public final int totalWealth;
		
		@JsonCreator
		public PlayerWealthData(@JsonProperty("ownerID") int ownerID, @JsonProperty("cash") int cash, 
								@JsonProperty("totalWealth") int totalWealth){
			this.ownerID=ownerID;
			this.cash=cash;
			this.totalWealth=totalWealth;
		}
		@Override
		public boolean equals(Object other){
			if(other instanceof PlayerWealthData){
				PlayerWealthData otherData= (PlayerWealthData)other;
				if(otherData.ownerID!=this.ownerID) return false;
				if(otherData.cash!=this.cash) return false;
				if(otherData.totalWealth!=this.totalWealth) return false;
				return true;

			}
			return false;
		}
	}
}
