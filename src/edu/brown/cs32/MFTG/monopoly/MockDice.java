package edu.brown.cs32.MFTG.monopoly;

import java.util.Random;

/**
 * This class models fake dice that always return the same thing
 * @author jschvime
 *
 */
public class MockDice extends Dice{
	private int _roll;
	private boolean _doubles;
	public MockDice(int roll, boolean doubles) {
		super(new Random());
		_roll=roll;
		_doubles=doubles;
	}
	/**
	 * 
	 * @return mock roll
	 */
	public int rollDice(){
		return _roll;		
	}
	
	/**
	 * 
	 * @return mock doubles
	 */
	public boolean wasDoubles(){
		return _doubles;
	}

}
