package edu.brown.cs32.MFTG.tournament;

import edu.brown.cs32.MFTG.monopoly.GamePlayer;
import edu.brown.cs32.MFTG.monopoly.Property;

public class Heuristics {
	
	/**
	 * Takes in a GamePlayer and a Property
	 * and determines whether to buy the property based on the
	 * GamePlayer's preferences and the specific property in question
	 * @param player
	 * @param property
	 * @return
	 */
	public static boolean buyProperty(GamePlayer player, Property property){
		//TODO implement
		//how much do you value this property?
		//how much $$ do you have? what is your min $$ level?
		//would it give you a monopoly?
		//would it break someone else's monopoly?
		return false;
	}
	
	/**
	 * Determines whether to build a house
	 * @param player
	 * @param property
	 * @return the number of houses to build
	 */
	public static int buildHouses(GamePlayer player, Property property){
		//TODO implement
		//do you like to build evenly?
		//how much $$ do you have? what is your min $$ level?
		//how much do you value the property?
		return 0;
	}
	
	//determining whether to sell houses/ mortgage properties will be done on a comparative basis
	//(by ordering them from most desirable to get rid of to least desirable to get rid of
	

}
