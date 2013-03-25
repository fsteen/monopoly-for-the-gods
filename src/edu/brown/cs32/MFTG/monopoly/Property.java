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
	private int _ownerID;

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
		_ownerID=-1;
		
	}
	
	/**
	 * 
	 * @return rent owed
	 */
	public int getRent(){
		return _cost[_numHouses];
	}
	
	/**
	 * 
	 * @return ownerID
	 */
	public int getOwner(){
		return _ownerID;
	}
	
	/**
	 * 
	 * @param newOwnerID
	 */
	public void setOwner(int newOwnerID){
		_ownerID=newOwnerID;
	}

}
