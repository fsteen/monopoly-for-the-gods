package edu.brown.cs32.MFTG.monopoly;

import java.util.HashMap;

public class Player {
	public enum Expense {
		CHEAP, EXPENSIVE
	}
	public enum Amount {
		FEWER, MORE
	}
	public enum Balance {
		EVEN, UNEVEN
	}
	public final int ID;
	private HashMap<String, Integer> _propertyValues;
	private HashMap<String, Double[]> _colorValues;
	private double _liquidity;
	private double _timeChange;
	private int _minBuyCash;
	private int _jailWait;
	private Expense _mortgageChoice;
	private Amount _houseSelling;
	private Balance _buildingEvenness;
	private Expense _buildingChoice;
	private Balance _buildingTiming;
	private int _minBuildCash;
	
	public Player(int id){
		ID=id;
		_propertyValues= new HashMap<>();
		
		//a hashmap of values for each color. for each color there is an array such that;
		//T[0]=value of monopoly
		//T[1]=value of houses for the color
		//T[2]=value of breaking an opponent's monopoly
		//T[3]=how much value is affected by how many other properties of the same color you have
		_colorValues = new HashMap<>();
		_liquidity=1;
		_timeChange=1;
		_minBuyCash=0;
		_jailWait=3;
		
		_mortgageChoice=Expense.EXPENSIVE;
		_houseSelling=Amount.FEWER;
		_buildingEvenness=Balance.UNEVEN;
		_buildingChoice=Expense.CHEAP;
		
		//even represents buying slowly
		//uneven represents buying all at once
		_buildingTiming=Balance.EVEN;
		
		_minBuildCash=0;
		
		
		
	}
}
