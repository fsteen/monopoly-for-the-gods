package edu.brown.cs32.MFTG.monopoly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.brown.cs32.MFTG.monopoly.Player.*;

public class GamePlayer {
	private final Player _player;
	private int _cash, _numGetOutOfJailFree, _position, _turnsInJail, _numRailroads, _numUtilities;
	private ArrayList<Property> _properties;
	
	public GamePlayer(Player player) {
		_player=player;
		_cash=1500;
		_numGetOutOfJailFree=0;
		_position=0;
		_turnsInJail=-1;
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
	 * 
	 * @return list of properties
	 */
	public List<Property> getProperties(){
		return _properties;
	}
	
	/**
	 * Adds money from player
	 * @param newMoney
	 */
	public void addMoney(int newMoney) throws IllegalArgumentException{
		if(newMoney<0){
			throw new IllegalArgumentException("Money gained must be positive");
		}
		_cash+=newMoney;
	}
	
	/**
	 * subtracts money from player, if it goes negative player starts selling houses and mortgaging
	 * @param lostMoney
	 * @return money actually paid
	 */
	public int payMoney(int lostMoney) throws IllegalArgumentException{
		if(lostMoney<0){
			throw new IllegalArgumentException("Money lost must be positive");
		}
		
		//TODO:start selling and mortgaging and somehow tell the game if the player is bankrupt
		if(_cash>=lostMoney){
			_cash-=lostMoney;
			return lostMoney;
		}
		else{
			int amountPaid=_cash;
			_cash=0;
			return amountPaid+tryMortgagingOrSelling(lostMoney-amountPaid);
		}
	}
	
	/**
	 * tries selling houses and mortgaging buildings to get the money
	 * first mortgages unmonopolized things
	 * then mortgages monopolies without houses
	 * then sells houses according to selling plan/building evenness
	 * then mortgage things without houses and repeat
	 * @param total
	 * @return amount actually gotten
	 */
	private int tryMortgagingOrSelling(int total){
		int amountPaid=mortgageProperties(total);
		if(_player.getBuildingEvenness()==Balance.EVEN){
			
		}
		else{
			
		}
			
		return 0;
	}
	
	private int mortgageProperties(int total){
		int amountPaid=0;
		//mortgage properties without houses
		List<Property> unmonopolized = new ArrayList<>();
		List<Property> unhoused = new ArrayList<>();
		for(Property p: _properties){
			if(p.getMortgagedState()){
				continue;
			}
			else if(p.getMonopolyState()==false){
				unmonopolized.add(p);
			}
			else if(p.getNumHouses()==0){
				unhoused.add(p);
			}
		}
		Collections.sort(unmonopolized,new MortgagePriceComparator());
		
		//mortgages unmonopolized properties
		if(_player.getMortgageChoice()==Expense.CHEAP){
			for(int i=0; i<unmonopolized.size();i++){
				unmonopolized.get(i).setMortgagedState(true);
				amountPaid+=unmonopolized.get(i).MortgageValue;
				if(amountPaid>=total){
					_cash=amountPaid-total;
					return total;
				}
				unmonopolized.remove(i);
				i--;
			}
		}
		else{
			for(int i=unmonopolized.size()-1; i>=0;i--){
				unmonopolized.get(i).setMortgagedState(true);
				amountPaid+=unmonopolized.get(i).MortgageValue;
				if(amountPaid>=total){
					_cash=amountPaid-total;
					return total;
				}
				unmonopolized.remove(i);
				i++;
			}
		}
		
		Collections.sort(unhoused,new MortgagePriceComparator());
		
		//mortgages unhoused but monopolized properties
		if(_player.getMortgageChoice()==Expense.CHEAP){
			for(int i=0; i<unhoused.size();i++){
				unhoused.get(i).setMortgagedState(true);
				amountPaid+=unhoused.get(i).MortgageValue;
				if(amountPaid>=total){
					_cash=amountPaid-total;
					return total;
				}
				unhoused.remove(i);
				i--;
			}
		}
		else{
			for(int i=unhoused.size()-1; i>=0;i--){
				unhoused.get(i).setMortgagedState(true);
				amountPaid+=unhoused.get(i).MortgageValue;
				if(amountPaid>=total){
					_cash=amountPaid-total;
					return total;
				}
				unhoused.remove(i);
				i++;
			}
		}
		return amountPaid;
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
			_properties.add(property);
			
			//checks if the property now creates a monopoly
			if((property.getSibling1()==null||property.getSibling1().getOwner()==this) &&(property.getSibling2()==null||property.getSibling2().getOwner()==this)){
				property.setMonopolyState(true);
				if(property.getSibling1()!=null) property.getSibling1().setMonopolyState(true);
				if(property.getSibling2()!=null) property.getSibling2().setMonopolyState(true);
			}
			payMoney(property.Price);
			if(property.Name.equals("electric company")||property.Name.equals("water works")){
				_numUtilities++;
			}
			else if(property.Name.equals("reading railroad")||property.Name.equals("pennsylvania avenue")||property.Name.equals("b and o railroad")||property.Name.equals("short line")){
				_numRailroads++;
			}
			return true;
		}
	}
	
	public void gainProperty(Property property){
		property.setOwner(this);
		_properties.add(property);
		//checks if the property now creates a monopoly
		if((property.getSibling1()==null||property.getSibling1().getOwner()==this) &&(property.getSibling2()==null||property.getSibling2().getOwner()==this)){
			property.setMonopolyState(true);
			if(property.getSibling1()!=null) property.getSibling1().setMonopolyState(true);
			if(property.getSibling2()!=null) property.getSibling2().setMonopolyState(true);
		}
	}
	
	/**
	 * Mortgage a property
	 * @param property
	 */
	public void mortgageProperty(Property property){
		_cash+=property.MortgageValue;
		property.setMortgagedState(true);
	}
	
	/**
	 * UnMortgage a property
	 * @param property
	 * @throws Exception 
	 */
	public void unmortgageProperty(Property property) throws Exception{
		int cost=(int) (property.MortgageValue*1.1);
		if(cost>_cash){
			throw new Exception("Cannot unmortgage without enough cash");
		}
		_cash-=cost;
		property.setMortgagedState(false);
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
		_turnsInJail=-1;
	}
	
	/**
	 * set it so player is out of jail
	 */
	public void goToJail(){
		_turnsInJail=0;
	}
	
	/**
	 * 
	 * @return if player is in jail
	 */
	public boolean isInJail(){
		return (_turnsInJail!=-1);
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
	
	/**
	 * 
	 * @return total wealth of player
	 */
	public int getTotalWealth(){
		int total =_cash;
		for(Property p: _properties){
			total+=p.Price;
			total+=p.CostPerHouse*p.getNumHouses();
		}
		return total;
	}
	
	/**
	 * 
	 * @return amount of cash on hand
	 */
	public int getCash(){
		return _cash;
	}
	
	/**
	 * Compares two properties based on mortgage price
	 * @author JudahSchvimer
	 *
	 */
	public class MortgagePriceComparator implements Comparator<Property> {

		public MortgagePriceComparator() {}

		@Override
		public int compare(Property o1, Property o2) {
			return o1.MortgageValue-o2.MortgageValue;
		}

	}
	
	/**
	 * Compares two properties based on number of houses and then by price
	 * @author JudahSchvimer
	 *
	 */
	public class HouseNumberComparator implements Comparator<Property> {

		public HouseNumberComparator() {}

		@Override
		public int compare(Property o1, Property o2) {
			int dif =o1.getNumHouses()-o2.getNumHouses();
			if(dif==0){
				dif=o1.Price-o2.Price;
			}
			return dif;
		}

	}
	
	/**
	 * Compares two properties based on price
	 * @author JudahSchvimer
	 *
	 */
	public class PriceComparator implements Comparator<Property> {

		public PriceComparator() {}

		@Override
		public int compare(Property o1, Property o2) {
			return o1.Price-o2.Price;
		}

	}



}
