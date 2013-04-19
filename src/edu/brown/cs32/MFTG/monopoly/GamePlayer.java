package edu.brown.cs32.MFTG.monopoly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import edu.brown.cs32.MFTG.monopoly.Player.*;

public class GamePlayer {
	private final Player _player;
	private int _cash, _numGetOutOfJailFree, _position, _turnsInJail, _numRailroads, _numUtilities;
	private Game _game;
	private ArrayList<Property> _properties;


	public GamePlayer(Game game, Player player) {
		_player=player;
		_cash=1500;
		_numGetOutOfJailFree=0;
		_position=0;
		_turnsInJail=-1;
		_numRailroads=0;
		_numUtilities=0;
		_properties= new ArrayList<>();
		_game=game;

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
	 * @throws Exception 
	 */
	public int payMoney(int lostMoney) throws Exception{
		if(lostMoney<0){
			throw new IllegalArgumentException("Money lost must be positive");
		}
		if(_cash>=lostMoney){
			_cash-=lostMoney;
			//System.out.println(this + " lost "+ lostMoney +" and ended up with "+_cash);
			return lostMoney;
		}
		else{
			int amountPaid=_cash;
			_cash=0;
			return amountPaid+tryMortgagingOrSelling(lostMoney-amountPaid);
		}
	}

	/**
	 * Attempts to buy the property
	 * @param property
	 * @return if the property was bought
	 * @throws Exception 
	 */
	public boolean buyProperty(Property property) throws Exception{
		if(_cash-property.Price<_player.getMinBuyCash()) {
			return false;
		}
		else{
			if(getPropertyValue(property)<property.Price) {
				return false;
			}
			
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
			else if(property.Name.equals("reading railroad")||property.Name.equals("pennsylvania railroad")||property.Name.equals("b and o railroad")||property.Name.equals("short line")){
				_numRailroads++;
			}
			return true;
		}
	}
	
	/**
	 * 
	 * @param property
	 * @return valuation of the property for this player
	 */
	double getPropertyValue(Property property) {
		double initialValue= _player.getPropertyValue(property.Name);
		//this has the effect of making it so someone near 1 wants to buy more near the beginning (from 1.4 to 1)and 10 near the end (from .5 to 1) 
		//double timeEffect=(5-_player.getTimeChange())*(Math.sqrt(_game.getNumberofUnownedProperties()/28.0)*Math.abs(5-_player.getTimeChange())/50)+(2*Math.sqrt(_player.getTimeChange())+7)/10;
		
		//this has the effect of making it so someone near 1 wants to buy more near the beginning (from 1.3 to 1)and 10 near the end (from 1 to 1.3) 
		double timeEffect=(_game.getNumberofUnownedProperties()/28.0)*(5.0-((double)_player.getTimeChange()))/5.0+(5.0+((double)_player.getTimeChange()))/10.0;
		timeEffect=Math.max(1, timeEffect*2/3);
		
		//this has the effect of making it so someone near 1 wants to buy nothing more with more money, and 10 wants to buy a lot more
		//this yields values near 1 for low cash values and low liquidities and 20 for large values
		double liquidEffect=_cash*_cash/20000000*(_player.getLiquidity()+1)+.85;
		double finalValue=-1;
		GamePlayer otherOwner=property.getCloseToMonopoly();
		if(otherOwner==null) {
			if(property.getSibling1()!=null && property.getSibling1().getOwner()==this) {
				finalValue=_player.getSameColorEffect(property.Color);
			}
			else if(property.getSibling2()!=null && property.getSibling2().getOwner()==this) {
				finalValue=_player.getSameColorEffect(property.Color);
			}
			else {
				finalValue=initialValue;
			}
		}
		else if(otherOwner==this) {
			finalValue=_player.getMonopolyValue(property.Color);
		}
		else {
			finalValue=_player.getBreakingOpponentMonopolyValue(property.Color);
		}
		finalValue*=timeEffect*=liquidEffect;
		
		return finalValue;
		
	}
	
	/**
	 * tries selling houses and mortgaging buildings to get the money
	 * first mortgages unmonopolized things
	 * then mortgages monopolies without houses
	 * then sells houses according to selling plan/building evenness
	 * then mortgage things without houses and repeat
	 * @param total
	 * @return amount actually gotten
	 * @throws Exception 
	 */
	private int tryMortgagingOrSelling(int total) throws Exception{
		int amountPaid=0;
		List<Property> unhoused = new ArrayList<>();
		PriorityQueue<Property> housed = new PriorityQueue<>(_properties.size(),new HouseSellingComparator());
		//separate all properties into those with houses and those without
		for(Property p: _properties){
			if(p.getMortgagedState()){
				continue;
			}
			else if(p.getNumHouses()==0){
				unhoused.add(p);
			}
			else{
				housed.add(p);
			}
		}

		Collections.sort(unhoused,new MortgageComparator());
		//mortgage all unhoused properties in order of preference
		for(int i=0; i<unhoused.size();i++){
			System.out.println(this +" mortgaged "+unhoused.get(i));
			unhoused.get(i).setMortgagedState(true);
			amountPaid+=unhoused.get(i).MortgageValue;
			if(amountPaid>=total){
				_cash=amountPaid-total;
				return total;
			}
			unhoused.remove(i);
			i--;
		}
		//sell all houses and mortgage unhoused properties in order of preference
		while(amountPaid<total){
			//find a property that can sell a house and then add back in all of the ones taht couldn't
			List<Property> temp = new ArrayList<>();
			Property curr = housed.poll();
			//if the queue is empty then we've sold all houses and mortgaged eveyrthing so we're bankrupt
			if(curr==null){
				return amountPaid;
			}
			while(curr.canSellHouse()==false){
				temp.add(curr);
				curr=housed.poll();
			}
			housed.addAll(temp);
			System.out.println(this +" sold a house from " +curr+ " to leave a total of "+curr.getNumHouses()+ " houses");
			//sell house on current property
			amountPaid+=curr.sellHouse();
			//if we still need money and this property has no houses we mortgage in it
			if(curr.getNumHouses()==0&&amountPaid<total){
				System.out.println(this +" mortgaged "+curr);
				curr.setMortgagedState(true);
				amountPaid+=curr.MortgageValue;
			}
			//add it back in at the correct position
			else{
				housed.add(curr);
			}

		}
		_cash=amountPaid-total;
		return total;
	}

	/**
	 * tries unmortgaging properties
	 * @throws Exception
	 */
	public void tryUnmortgaging() throws Exception{
		List<Property> mortgaged = new ArrayList<>();
		//separate all properties into those with houses and those without
		for(Property p: _properties){
			if(p.getMortgagedState()){
				mortgaged.add(p);
			}
		}
		if(mortgaged.isEmpty()){
			return;
		}
		Collections.sort(mortgaged,new MortgageComparator());
		//mortgage all unhoused properties in order of preference
		while(mortgaged.isEmpty()==false&&_cash>=_player.getMinBuildCash()){
			if(_cash<mortgaged.get(0).MortgageValue*1.1){
				if(_player.getMortgageChoice()==Expense.CHEAP){
					return;
				}
				else{
					mortgaged.remove(0);
					continue;
				}
			}
			System.out.println(this +" unmortgaged "+mortgaged.get(0));
			unmortgageProperty(mortgaged.get(0));
			mortgaged.remove(0);

		}
	}
	
	/**
	 * Tries building properties
	 * @throws Exception 
	 */
	public void tryBuilding() throws Exception{
		if(_properties.size()<2) {
			return;
		}
		PriorityQueue<Property> monopolies = new PriorityQueue<>(_properties.size(),new HouseBuildingComparator());
		for(Property p: _properties){
			if(p.getMonopolyState()&&p.getNumHouses()!=5){
				monopolies.add(p);
			}
		}
		if(monopolies.isEmpty()){
			return;
		}
		System.out.println(monopolies);
		List<Property> temp = new ArrayList<>();
		Property curr=monopolies.poll();
		while(keepIterating(curr)){
			temp.add(curr);
			curr=monopolies.poll();
			if(curr==null){
				return;
			}
		}
		monopolies.addAll(temp);
		while(curr.CostPerHouse<=_cash-_player.getMinBuildCash()){

			_cash-=curr.CostPerHouse;
			curr.buildHouse();
			System.out.println(this + " built on "+curr+" for a total of "+ curr.getNumHouses()+" houses.");
			monopolies.add(curr);
			curr = monopolies.poll();
			if(curr==null){
				return;
			}
			//while you can't build a house or while you don't have money to build a house but might for future ones
			while(keepIterating(curr)){
				temp.add(curr);
				curr=monopolies.poll();
				if(curr==null){
					return;
				}
			}
			monopolies.addAll(temp);

		}	

	}
	
	/**
	 * Checks if the loop above in building should keep iterating
	 * @param current property
	 * @return
	 */
	private boolean keepIterating(Property curr) {
		if(curr.canBuildHouse()==false) {
			return true;
		}
		if(_player.getBuyAggression()==Aggression.AGGRESSIVE) {
			if (curr.canBuildHouse()==true && curr.CostPerHouse>_cash-_player.getMinBuildCash() &&_player.getBuildingChoice()==Expense.EXPENSIVE) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * tries to get the player out of jail
	 * @throws Exception 
	 */
	public void tryGettingOutofJail() throws Exception {
		//if they don't have the money just wait
		if(_cash<=50) {
			return;
		}
		//if they've been in jail too long get em out
		if(_turnsInJail>=_player.getJailWait()) {
			getOutOfJail();
			_game.transferMoney(this, null, 50);
			return;
		}
		boolean rich=true;
		//if they're super poor,stay in for the length of term then
		for(GamePlayer p: _game.getOtherPlayers(this)) {
			if(p.getTotalWealth()>getTotalWealth()*2) {
				if(_turnsInJail>=_player.getJailPoor()) {
					getOutOfJail();
					_game.transferMoney(this, null, 50);
					return;
				}
			}
			else if(2*p.getTotalWealth()>getTotalWealth()) {
				rich=false;
			}
		}
		//if they're rich, let em out when they want
		if(rich==true) {
			if(_turnsInJail>=_player.getJailRich()) {
				getOutOfJail();
				_game.transferMoney(this, null, 50);
				return;
			}
		}
		
	}

	/**
	 * gain a property for free
	 * @param property
	 */
	public void gainProperty(Property property){
		property.setOwner(this);
		_properties.add(property);
		//checks if the property now creates a monopoly
		if((property.getSibling1()==null||property.getSibling1().getOwner()==this) &&(property.getSibling2()==null||property.getSibling2().getOwner()==this)){
			property.setMonopolyState(true);
			if(property.getSibling1()!=null) property.getSibling1().setMonopolyState(true);
			if(property.getSibling2()!=null) property.getSibling2().setMonopolyState(true);
		}
		if(property.Name.equals("electric company")||property.Name.equals("water works")){
			_numUtilities++;
		}
		else if(property.Name.equals("reading railroad")||property.Name.equals("pennsylvania railroad")||property.Name.equals("b and o railroad")||property.Name.equals("short line")){
			_numRailroads++;
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
			throw new Exception(String.format("Cannot unmortgage without enough cash; cash: %d, cost: %d", _cash, cost));
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
	
	@Override
	public String toString(){
		return _player.toString();
	}

	/**
	 * Compares two properties based on mortgage price
	 * bigger properties are ones that are more valuable to keep
	 * monopolized ones are more valuable then unmonopolized ones
	 * then if they prefer to mortgage cheap ones those are ranked less valuable or vice versa
	 * 
	 * @author JudahSchvimer
	 *
	 */
	public class MortgageComparator implements Comparator<Property> {

		public MortgageComparator() {}

		@Override
		public int compare(Property prop1, Property prop2) {
			if(prop1.getMortgagedState()&&prop2.getMortgagedState()==false){
				return 1;
			}
			else if(prop1.getMortgagedState()==false&&prop2.getMortgagedState()){
				return -1;
			}
			else{
				if(_player.getMortgageChoice()==Expense.CHEAP){
					return prop1.MortgageValue-prop2.MortgageValue;
				}
				else{
					return prop2.MortgageValue-prop1.MortgageValue;
				}
			}

		}

	}

	/**
	 * Compares two properties on which to sell houses on first
	 * bigger ones are more valuable to keep
	 * first go based on houseselling
	 * then by expense of house
	 * then by expense of property
	 * 
	 * @author JudahSchvimer
	 *
	 */
	public class HouseSellingComparator implements Comparator<Property> {

		public HouseSellingComparator() {}

		@Override
		public int compare(Property prop1, Property prop2) {

			int dif =prop1.getNumHouses()-prop2.getNumHouses();
			if(dif!=0){
				if(_player.getHouseSelling()==Amount.FEWER){
					return dif;
				}
				else{
					return (-1)*dif;
				}
			}
			else{
				dif=prop1.CostPerHouse-prop2.CostPerHouse;
				if(dif!=0){
					if(_player.getSellingChoice()==Expense.CHEAP){
						return (-1)*dif;
					}
					else{
						return dif;
					}
				}
				else{
					double colordif=_player.getHouseValueOfColor(prop1.Color)-_player.getHouseValueOfColor(prop2.Color);
					if(colordif!=0) {
						return (int) (Math.ceil(colordif));
					}
					else {
						return prop1.Price-prop2.Price;
					}
					
				}
			}

		}
	}

	/**
	 * Compares two properties on which to build houses on first
	 * bigger ones are more less likely to sell on
	 * first go based on evenness
	 * then by expense of house
	 * then by expense of property
	 * 
	 * @author JudahSchvimer
	 *
	 */
	public class HouseBuildingComparator implements Comparator<Property> {

		public HouseBuildingComparator() {}

		@Override
		public int compare(Property prop1, Property prop2) {
			int dif =prop1.getNumHouses()-prop2.getNumHouses();
			if(dif!=0){
				if(_player.getBuildingEvenness()==Balance.EVEN){
					return dif;
				}
				else{
					return (-1)*dif;
				}
			}
			else{
				double colordif=_player.getHouseValueOfColor(prop1.Color)-_player.getHouseValueOfColor(prop2.Color);
				if(dif!=0){
					return (int) ((-1)*Math.ceil(colordif));
				}
				else{
					dif=prop1.CostPerHouse-prop2.CostPerHouse;
					if(dif!=0){
						if(_player.getBuildingChoice()==Expense.CHEAP){
							return dif;
						}
						else{
							return (-1)*dif;
						}
					}
					else{
						return -1;
					}
				}
				
			}
		}

	}



}
