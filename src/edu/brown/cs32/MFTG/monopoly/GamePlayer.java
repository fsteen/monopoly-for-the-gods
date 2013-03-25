package edu.brown.cs32.MFTG.monopoly;

import java.util.ArrayList;

public class GamePlayer {
	private final Player _player;
	private int _cash, _numGetOutOfJailFree, _position, _turnsInJail, _numRailroads, _numUtilities, _totalWealth;
	private ArrayList<Property> _properties;
	
	public GamePlayer(Player player) {
		_player=player;
		_cash=1500;
		_numGetOutOfJailFree=0;
		_position=0;
		_turnsInJail=0;
		_numRailroads=0;
		_numUtilities=0;
		_properties= new ArrayList<>();
		
	}
	
	/**
	 * 
	 * @return player
	 */
	public Player getPlayer(){
		return _player;
	}
	
	/**
	 * Adds money from player
	 * @param newMoney
	 */
	public void addMoney(int newMoney){
		_cash+=newMoney;
		_totalWealth+=newMoney;
	}
	
	/**
	 * subtracts money from player, if it goes negative player starts selling houses and mortgaging
	 * @param lostMoney
	 */
	public void payMoney(int lostMoney){
		_cash-=lostMoney;
		_totalWealth-=lostMoney;
		//TODO:start selling and mortgaging and somehow tell the game if the player is bankrupt
	}
	
	/**
	 * Attempts to buy the property
	 * @param property
	 * @return if the property was bought
	 */
	public boolean buyProperty(Property property){
		if(_cash<property.Price){
			return false;
		}
		else{
			//TODO: decide if it wants to buy the property
			property.setOwner(this);
			_cash-=property.Price;
			//TODO: decide if property is a railroad or utility add that in
			return true;
		}
	}
	
	/**
	 * 
	 * @return position of player
	 */
	public int getPosition(){
		return _position;
	}
	
	/**
	 * sets position of player
	 * @param newPosition
	 */
	public void setPosition(int newPosition){
		_position=newPosition;
	}
	
	/**
	 * move the player a number of positions
	 * @param numPositions
	 */
	public void movePlayer(int numPositions){
		_position=(_position+=numPositions)%36;
	}
	
	/**
	 * adds a get out of jail free card
	 */
	public void addGetOutOfJailFreeCard(){
		_numGetOutOfJailFree++;
	}
	
	/**
	 * subtracts a get out of jail free card
	 */
	public void subtractGetOutOfJailFreeCard(){
		_numGetOutOfJailFree--;
	}
	
	/**
	 * 
	 * @return number of turns in jail
	 */
	public int getTurnsInJail(){
		return _turnsInJail;
	}
	
	/**
	 * adds a turn to the number of turns in jail
	 */
	public void incrementTurnsInJail(){
		_turnsInJail++;
	}
	
	/**
	 * set it so player is out of jail
	 */
	public void getOutOfJail(){
		_turnsInJail=0;
	}
	
	/**
	 * 
	 * @return if player is in jail
	 */
	public boolean isInJail(){
		return (_turnsInJail!=0);
	}
	
	/**
	 * 
	 * @return number of railroads owned
	 */
	public int getNumRailroads(){
		return _numRailroads;
	}
	
	/**
	 * 
	 * @return number of utitilies owned
	 */
	public int getNumUtilities(){
		return _numUtilities;
	}


}
