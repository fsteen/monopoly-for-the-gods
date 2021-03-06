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
	public final int CostPerHouse;
	private int[] _cost;
	private int _numHouses;
	private GamePlayer _owner;
	private boolean _isMortgaged;
	private boolean _isMonopoly;
	public final String Color;
	private Property _p1, _p2;
	private int _revenueWithoutHouses;
	private int _revenueWithHouses;
	private int _personalRevenueWithout;
	private int _personalRevenueWith;

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
	public Property(String name, String color, int price, int mortgageValue, int rent, int oneHouseCost, int twoHouseCost, int threeHouseCost, int fourHouseCost, int hotelCost, int costperhouse){
		Name=name;
		Price=price;
		MortgageValue=mortgageValue;
		Color=color;
		CostPerHouse=costperhouse;
		
		_cost = new int[6];
		_cost[0]=rent;
		_cost[1]=oneHouseCost;
		_cost[2]=twoHouseCost;
		_cost[3]=threeHouseCost;
		_cost[4]=fourHouseCost;
		_cost[5]=hotelCost;
		
		_numHouses=0;
		_owner=null;
		_isMonopoly=false;
		_revenueWithHouses=0;
		_revenueWithoutHouses=0;
		
	}
	
	/**
	 * 
	 * @return rent owed
	 */
	public int getRent() throws Exception{
		if(_isMortgaged){
			return 0;
		}
		if(_numHouses==0&&_isMonopoly){
			return _cost[0]*2;
		}
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
		//resets the personal revenue counter
		if(newOwner!=_owner){
			_personalRevenueWith=0;
			_personalRevenueWithout=0;
		}
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
	public void buildHouse(Game game) throws Exception{
		if(_numHouses==5){
			throw new Exception("Too many houses, cannot add more");
		}
		else if(_p1!=null&&_p1.getNumHouses()<_numHouses){
			throw new Exception("Cannot build houses unevenly");
		}
		else if(_p2!=null&&_p2.getNumHouses()<_numHouses){
			throw new Exception("Cannot build houses unevenly");
		}
		_numHouses+=1;
		//add houses back to pile
		if(_numHouses<5) {
			game.decrementNumHousesLeft(1);
		}
		else {
			game.decrementNumHotelsLeft(1);
			game.incrementNumHousesLeft(4);
		}
		
	}
	
	/**
	 * 
	 * @return if you can build another house
	 */
	public boolean canBuildHouse(Game game){
		if(_isMortgaged) {
			return false;
		}
		if(_p1!=null&&(_p1.getNumHouses()<_numHouses||_p1.getMortgagedState())){
			return false;
		}
		if(_p2!=null&&(_p2.getNumHouses()<_numHouses||_p2.getMortgagedState())){
			return false;
		}
		if(_numHouses<4 &&game.getNumHousesLeft()==0) {
			return false;
		}
		if(_numHouses==4 &&game.getNumHotelsLeft()==0) {
			return false;
		}
		return (_numHouses<5);
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
	
	/**
	 * 
	 * @param newMonopolyState
	 */
	public void setMonopolyState(boolean newMonopolyState){
		_isMonopoly=newMonopolyState;
	}
	
	/**
	 * 
	 * @return if it's a monopoly
	 */
	public boolean getMonopolyState(){
		return _isMonopoly;
	}
	
	/**
	 * sells a house off the property
	 * @return amount made
	 */
	public int sellHouse(Game game) throws Exception{
		if(_p1!=null&&_p1.getNumHouses()>_numHouses){
			throw new Exception("Cannot sell houses unevenly");
		}
		else if(_p2!=null&&_p2.getNumHouses()>_numHouses){
			throw new Exception("Cannot sell houses unevenly");
		}
		else if(_numHouses==0){
			throw new Exception("No houses to sell");
		}
		//add houses back to pile
		if(_numHouses<5) {
			game.incrementNumHousesLeft(1);
		}
		else {
			game.incrementNumHotelsLeft(1);
			game.decrementNumHousesLeft(4);
		}
		_numHouses--;
		return CostPerHouse/2;
	}
	
	/**
	 * 
	 * @return if you can sell a house on this property
	 */
	public boolean canSellHouse(){
		if(_p1!=null&&_p1.getNumHouses()>_numHouses){
			return false;
		}
		if(_p2!=null&&_p2.getNumHouses()>_numHouses){
			return false;
		}
		return (_numHouses>0);
	}
	
	/**
	 * Sets the other properties of the same color
	 * @param p1
	 * @param p2
	 */
	public void setSiblings(Property p1, Property p2){
		_p1=p1;
		_p2=p2;
	}
	
	/**
	 * 
	 * @return property of same color, only null for utilities and railroads
	 */
	public Property getSibling1(){
		return _p1;
	}
	
	/**
	 * 
	 * @return property of the same color, can be null
	 */
	public Property getSibling2(){
		return _p2;
	}
	
	/**
	 * 
	 * @param sib
	 * @return if the property is a sibling
	 */
	public boolean isSibling(Property sib) {
		return (sib==_p1||sib==_p2);
	}
	
	/**
	 * 
	 * @return owner if both siblings have same owner or if only 1 sibling, null otherwise
	 */
	public GamePlayer getCloseToMonopoly() {
		if(_p1==null) {
			return null;
		}
		else if(_p2==null && _p1.getOwner()==null) {
			return null;
		}
		else if(_p2==null && _p1.getOwner()!=null) {
			return _p1.getOwner();
		}
		else if(_p2.getOwner()==_p1.getOwner()) {
			return _p1.getOwner();
		}
		return null;
	}
	
	/**
	 * add revenue
	 * @param rent
	 */
	public void addRevenue(int rent){
		if(_numHouses==0){
			_revenueWithoutHouses+=rent;
			_personalRevenueWithout+=rent;
		}
		else{
			_revenueWithHouses+=rent;
			_personalRevenueWith+=rent;
		}
	}
	
	/**
	 * subtract revenue from the property
	 * @param payment
	 */
	public void loseRevenue(int payment){
		if(_numHouses==0){
			_revenueWithoutHouses-=payment;
			_personalRevenueWithout-=payment;
		}
		else{
			_revenueWithHouses-=payment;
			_personalRevenueWith-=payment;
		}
	}
	
	/**
	 * @return the _revenueWithoutHouses
	 */
	public int getTotalRevenueWithoutHouses() {
		return _revenueWithoutHouses;
	}

	/**
	 * @return the _revenueWithHouses
	 */
	public int getTotalRevenueWithHouses() {
		return _revenueWithHouses;
	}

	/**
	 * @return the _personalRevenueWithout
	 */
	public int getPersonalRevenueWithout() {
		return _personalRevenueWithout;
	}

	/**
	 * @return the _personalRevenueWith
	 */
	public int getPersonalRevenueWith() {
		return _personalRevenueWith;
	}
	
	@Override
	public String toString(){
		return Name;
	}

	
	

}
