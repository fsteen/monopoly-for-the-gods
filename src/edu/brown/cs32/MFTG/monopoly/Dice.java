package edu.brown.cs32.MFTG.monopoly;

import java.util.Random;

/**
 * Models a pair of dice 
 * @author JudahSchvimer
 *
 */
public class Dice {
	private Random _die;
	private boolean _doubles;
	/**
	 * Constructs two dice with unique seeds
	 */
	public Dice(Random rand) {
		_die= rand;
	}
	
	/**
	 * 
	 * @return random dice roll
	 */
	public int rollDice(){
		int r1 = _die.nextInt(6)+1;
		int r2 = _die.nextInt(6)+1;
		_doubles = (r1==r2);
		return r1+r2;			
	}
	
	/**
	 * 
	 * @return if most recent roll was doubles
	 */
	public boolean wasDoubles(){
		return _doubles;
	}
	
	

}
