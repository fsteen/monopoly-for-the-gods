package edu.brown.cs32.MFTG.monopoly;

import java.util.ArrayList;
import java.util.List;

/**
 * Models game data, game
 * @author JudahSchvimer
 *
 */
public class GameData {
	private ArrayList<TimeStamp> _data;
	private int _time, _numPlayers;
	private int _winner;
	
	/**
	 * Constructs the game data objects
	 * @param numPlayers
	 */
	public GameData(int numPlayers){
		_numPlayers=numPlayers;
		
		//this is a list of players, each player has a list 
		_data = new ArrayList<>();
		_time=0;
		_data.add(new TimeStamp(_time));
		
		//list of players where for each player it has a list of times
		//each time has a list of wealth data
		//data (in order): cash, total wealth
		_data = new ArrayList<>();


		
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
	 * Sets data for a property at the given time
	 * @param propertyName
	 * @param ownerID
	 * @param numHouses
	 * @param revenue
	 * @param mortgaged
	 */
	void setPropertyAtTime(String propertyName, int ownerID, int numHouses,  int personalRevenueWithHouses, int personalRevenueWithoutHouses,int totalRevenueWithHouses,int totalRevenueWithoutHouses, boolean mortgaged){
		_data.get(_time).setPropertyData(propertyName,ownerID,numHouses,personalRevenueWithHouses, personalRevenueWithoutHouses, totalRevenueWithHouses, totalRevenueWithoutHouses,mortgaged);
	}
	
	/**
	 * sets data for the wealth of an owner at the time
	 * @param ownerID
	 * @param cash
	 * @param totalWealth
	 */
	void setWealthAtTime(int ownerID, int cash, int totalWealth){
		_data.get(_time).setWealthData(ownerID,cash,totalWealth);
	}
	
	/**
	 * class to hold all data for a given property at any given time
	 * @author Jschvime
	 *
	 */
	public class TimeStamp{
		ArrayList<PropertyData> _propertyData;
		ArrayList<PlayerWealthData> _wealthData;
		private int _time;
		public TimeStamp(int time){
			_time=time;
			_propertyData = new ArrayList<>();
			_wealthData = new ArrayList<>();
		}
		
		/**
		 * Sets property data at the given time
		 * @param propertyName
		 * @param ownerID
		 * @param numHouses
		 * @param revenue
		 * @param mortgaged
		 */
		void setPropertyData(String propertyName, int ownerID, int numHouses, int personalRevenueWithHouses, int personalRevenueWithoutHouses,int totalRevenueWithHouses,int totalRevenueWithoutHouses, boolean mortgaged){
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
		 * sets wealth data for the given owner at the given time
		 * @param ownerID
		 * @param cash
		 * @param totalWealth
		 */
		void setWealthData(int ownerID, int cash, int totalWealth){
			_wealthData.add(new PlayerWealthData(ownerID,cash,totalWealth));
		}
		
		/**
		 * 
		 * @return wealth data at given time
		 */
		public ArrayList<PlayerWealthData> getWealthData(){
			return _wealthData;
		}
	}
	
	/**
	 * class to hold all data for a owner at a given time
	 * @author Jschvime
	 *
	 */
	public class PlayerWealthData{
		public int ownerID;
		public int cash;
		public int totalWealth;
		public PlayerWealthData(int ownerID, int cash, int totalWealth){
			this.ownerID=ownerID;
			this.cash=cash;
			this.totalWealth=totalWealth;
		}
	}
	
	/**
	 * class to hold all data for a given property at any given time
	 * @author Jschvime
	 *
	 */
	public class PropertyData{
		public String propertyName;
		public int ownerID;
		public int numHouses;
		public int personalRevenueWithHouses;
		public int personalRevenueWithoutHouses;
		public int totalRevenueWithHouses;
		public int totalRevenueWithoutHouses;
		public boolean mortgaged;
		public PropertyData(String propertyName, int ownerID, int numHouses, int personalRevenueWithHouses, int personalRevenueWithoutHouses,int totalRevenueWithHouses,int totalRevenueWithoutHouses,boolean mortgaged){
			this.propertyName=propertyName;
			this.ownerID=ownerID;
			this.numHouses=numHouses;
			this.personalRevenueWithHouses=personalRevenueWithHouses;
			this.personalRevenueWithoutHouses=personalRevenueWithoutHouses;
			this.totalRevenueWithHouses=totalRevenueWithHouses;
			this.totalRevenueWithoutHouses=totalRevenueWithoutHouses;
			this.mortgaged=mortgaged;
		}
	}
}
