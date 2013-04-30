package edu.brown.cs32.MFTG.monopoly;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	 * Getter for the current time attribute
	 * @return _time
	 */
	public int getTime(){
		return _time;
	}
	
	/**
	 * Setter for the time attribute
	 * @param time
	 */
	public void setTime(int time){
		_time = time;
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
	void setPropertyAtTime(String propertyName, int ownerID, double numHouses,  double personalRevenueWithHouses, double personalRevenueWithoutHouses,double totalRevenueWithHouses,double totalRevenueWithoutHouses, boolean mortgaged){
		_data.get(_time).addPropertyData(propertyName,ownerID,numHouses,personalRevenueWithHouses, personalRevenueWithoutHouses, totalRevenueWithHouses, totalRevenueWithoutHouses,mortgaged);
	}
	
	/**
	 * sets data for the wealth of an owner at the time
	 * @param ownerID
	 * @param cash
	 * @param totalWealth
	 */
	void setWealthAtTime(int ownerID, double cash, double totalWealth){
		_data.get(_time).addWealthData(ownerID,cash,totalWealth);
	}
	
	public void printData() {
		for(TimeStamp t: _data) {
			System.out.println(t._time);
			for(PropertyData prop: t.getPropertyData()) {
				System.out.println(String.format("Property: %s OwnerID: %d numHouses: %f personalRevenueWithHouses: %f personalRevenueWithoutHouses: %f totalRevenueWithHouses: %f totalRevenueWithoutHouses: %f morgaged: %b", prop.propertyName, prop.ownerID, prop.numHouses, prop.personalRevenueWithHouses, prop.personalRevenueWithoutHouses, prop.totalRevenueWithHouses, prop.totalRevenueWithoutHouses, prop.mortgaged));
			}
		}
		for(TimeStamp t: _data) {
			System.out.println(t._time);
			for(PlayerWealthData play: t.getWealthData()) {
				System.out.println(String.format("OwnerID: %d Cash: %f TotalWealth: %f", play.ownerID, play.cash, play.totalWealth));

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
	
}
