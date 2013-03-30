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
	private Expense _sellingChoice;
	private Balance _buildingEvenness;
	private Expense _buildingChoice;
	private Balance _buildingTiming;
	private int _minBuildCash;
	private int _jailPoor, _jailRich;
	
	public Player(int id){
		ID=id;
		_propertyValues= new HashMap<>();
		
		//a hashmap of values for each color. for each color there is an array such that;
		//T[0]=value of monopoly
		//T[1]=value of houses for the color
		//T[2]=value of breaking an opponent's monopoly
		//T[3]=how much value is affected by how many other properties of the same color you have
		_colorValues = new HashMap<>();
		
		//a number from 1-10 specifying how much they're willing to spend as they have more money
		_liquidity=5;
		
		//a number from 1-10 specifying how much more they value a property as time goes on
		_timeChange=1;
		
		_minBuyCash=0;
		_jailWait=3;
		
		_mortgageChoice=Expense.EXPENSIVE;
		
		_buildingEvenness=Balance.UNEVEN;
		_buildingChoice=Expense.CHEAP;
		_houseSelling=Amount.FEWER;
		
		//even represents buying slowly
		//uneven represents buying all at once
		_buildingTiming=Balance.EVEN;
		
		//a number from 1-3 specifying the number of turns they'd like to stay in if they're doing well/poorly
		_jailPoor=3;
		_jailRich=3;
		
		_minBuildCash=0;
	}
	
	/**
	 * sets property value
	 * @param property
	 * @param value
	 */
	public void setPropertyValue(String property, int value){
		_propertyValues.put(property, value);
	}
	
	/**
	 * 
	 * @param property
	 * @return property value
	 */
	public int getPropertyValue(String property){
		return _propertyValues.get(property);
	}
	
	/**
	 * sets color value
	 * @param color
	 * @param value
	 */
	public void setColorValue(String color, double monopolyValue, double housesValue, double breakingOpponentValue, double sameColorEffect ){
		Double[] monopoly = new Double[4];
		monopoly[0]=monopolyValue;
		monopoly[1]=housesValue;
		monopoly[2]=breakingOpponentValue;
		monopoly[3]=sameColorEffect;
		_colorValues.put(color, monopoly);
	}
	
	/**
	 * 
	 * @param color
	 * @return value of monopoly
	 */
	public double getMonopolyValue(String color){
		return _colorValues.get(color)[0];
	}
	
	/**
	 * 
	 * @param color
	 * @return value of houses on color
	 */
	public double getHouseValueOfColor(String color){
		return _colorValues.get(color)[1];
	}
	
	/**
	 * 
	 * @param color
	 * @return value of breaking opponents value
	 */
	public double getBreakingOpponentMonopolyValue(String color){
		return _colorValues.get(color)[2];
	}
	
	/**
	 * 
	 * @param color
	 * @return how much value is affected by how many other properties of the same color you have
	 */
	public double getSameColorEffect(String color){
		return _colorValues.get(color)[3];
	}
	
	/**
	 * @return the _liquidity
	 */
	public double getLiquidity() {
		return _liquidity;
	}

	/**
	 * @param _liquidity the _liquidity to set
	 */
	public void setLiquidity(double liquidity) {
		_liquidity = liquidity;
	}

	/**
	 * @return the _timeChange
	 */
	public double getTimeChange() {
		return _timeChange;
	}

	/**
	 * @param _timeChange the _timeChange to set
	 */
	public void setTimeChange(double timeChange) {
		_timeChange = timeChange;
	}

	/**
	 * @return the _minBuyCash
	 */
	public int getMinBuyCash() {
		return _minBuyCash;
	}

	/**
	 * @param _minBuyCash the _minBuyCash to set
	 */
	public void setMinBuyCash(int minBuyCash) {
		_minBuyCash = minBuyCash;
	}

	/**
	 * @return the _jailWait
	 */
	public int getJailWait() {
		return _jailWait;
	}

	/**
	 * @param _jailWait the _jailWait to set
	 */
	public void setJailWait(int jailWait) {
		_jailWait = jailWait;
	}

	/**
	 * @return the _mortgageChoice
	 */
	public Expense getMortgageChoice() {
		return _mortgageChoice;
	}

	/**
	 * @param _mortgageChoice the _mortgageChoice to set
	 */
	public void setMortgageChoice(Expense mortgageChoice) {
		_mortgageChoice = mortgageChoice;
	}

	/**
	 * @return the _houseSelling
	 */
	public Amount getHouseSelling() {
		return _houseSelling;
	}

	/**
	 * @param _houseSelling the _houseSelling to set
	 */
	public void setHouseSelling(Amount houseSelling) {
		_houseSelling = houseSelling;
	}

	/**
	 * @return the _buildingEvenness
	 */
	public Balance getBuildingEvenness() {
		return _buildingEvenness;
	}

	/**
	 * @param _buildingEvenness the _buildingEvenness to set
	 */
	public void setBuildingEvenness(Balance buildingEvenness) {
		_buildingEvenness = buildingEvenness;
	}

	/**
	 * @return the _buildingChoice
	 */
	public Expense getBuildingChoice() {
		return _buildingChoice;
	}

	/**
	 * @param _buildingChoice the _buildingChoice to set
	 */
	public void setBuildingChoice(Expense buildingChoice) {
		_buildingChoice = buildingChoice;
	}
	
	/**
	 * @return the sellingChoice
	 */
	public Expense getSellingChoice() {
		return _sellingChoice;
	}

	/**
	 * @param _sellingChoice the _sellingChoice to set
	 */
	public void setSellingChoice(Expense sellingChoice) {
		_sellingChoice = sellingChoice;
	}

	/**
	 * @return the _buildingTiming
	 */
	public Balance getBuildingTiming() {
		return _buildingTiming;
	}

	/**
	 * @param _buildingTiming the _buildingTiming to set
	 */
	public void setBuildingTiming(Balance buildingTiming) {
		_buildingTiming = buildingTiming;
	}

	/**
	 * @return the _minBuildCash
	 */
	public int getMinBuildCash() {
		return _minBuildCash;
	}

	/**
	 * @param _minBuildCash the _minBuildCash to set
	 */
	public void setMinBuildCash(int minBuildCash) {
		_minBuildCash = minBuildCash;
	}

	/**
	 * @return the _jailPoor
	 */
	public int getJailPoor() {
		return _jailPoor;
	}

	/**
	 * @param _jailPoor the _jailPoor to set
	 */
	public void setJailPoor(int jailPoor) {
		_jailPoor = jailPoor;
	}

	/**
	 * @return the _jailRich
	 */
	public int getJailRich() {
		return _jailRich;
	}

	/**
	 * @param _jailRich the _jailRich to set
	 */
	public void setJailRich(int jailRich) {
		_jailRich = jailRich;
	}


}
