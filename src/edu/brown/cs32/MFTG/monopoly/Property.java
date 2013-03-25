package edu.brown.cs32.MFTG.monopoly;

/**
 * Class modeling a property
 * @author JudahSchvimer
 *
 */
public class Property {
	public final String Name;
	public final int Price;
	public final int MortgageValue;
	private int[] _cost;
	private int _numHouses;
	private GamePlayer _owner;
	private boolean _isMortgaged;

	/**
	 * Constructs a new property
	 * @param name
	 * @param price
	 * @param mortgageValue
	 * @param rent
	 * @param oneHouseCost
	 * @param twoHouseCost
	 * @param threeHouseCost
	 * @param fourHouseCost
	 * @param hotelCost
	 */
	public Property(String name, int price, int mortgageValue, int rent, int oneHouseCost, int twoHouseCost, int threeHouseCost, int fourHouseCost, int hotelCost){
		Name=name;
		Price=price;
		MortgageValue=mortgageValue;
		
		_cost = new int[6];
		_cost[0]=rent;
		_cost[1]=oneHouseCost;
		_cost[2]=twoHouseCost;
		_cost[3]=threeHouseCost;
		_cost[4]=fourHouseCost;
		_cost[5]=hotelCost;
		
		_numHouses=0;
		_owner=null;
		
	}
	
	/**
	 * 
	 * @return rent owed
	 */
	public int getRent() throws Exception{
		return _cost[_numHouses];
	}
	
	/**
	 * 
	 * @return owner
	 */
	public GamePlayer getOwner(){
		return _owner;
	}
	
	/**
	 * 
	 * @param newOwnerID
	 */
	public void setOwner(GamePlayer newOwner){
		_owner=newOwner;
	}
	
	/**
	 * 
	 * @return number of houses
	 */
	public int getNumHouses(){
		return _numHouses;
	}
	
	/**
	 * adds a house to the property
	 * @throws Exception 
	 */
	public void addHouse() throws Exception{
		if(_numHouses==5){
			throw new Exception("Too many houses, cannot add more");
		}
		_numHouses+=1;
	}
	
	/**
	 * 
	 * @return if it's mortgaged
	 */
	public boolean getMortgagedState(){
		return _isMortgaged;
	}
	
	/**
	 * 
	 * @param newMortgagedState
	 */
	public void setMortgagedState(boolean newMortgagedState){
		_isMortgaged=newMortgagedState;
	}

}
