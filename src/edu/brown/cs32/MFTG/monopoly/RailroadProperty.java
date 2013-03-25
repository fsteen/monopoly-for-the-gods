package edu.brown.cs32.MFTG.monopoly;

public class RailroadProperty extends Property {

	public RailroadProperty(String name) {
		super(name, 200, 100, 25, Integer.MAX_VALUE, Integer.MAX_VALUE,Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	@Override
	/**
	 * adds a house to the property
	 * @throws Exception 
	 */
	public void addHouse() throws Exception{
		throw new Exception ("Cannot add house to a railroad");
	}

	@Override
	/**
	 * 
	 * @return rent owed
	 */
	public int getRent() throws Exception{
		int numRail=this.getOwner().getNumRailroads();
		switch(numRail){
		case 1:  return 25;
		case 2:  return 50;
		case 3:  return 100;
		case 4:  return 200;
		default: throw new Exception ("Cannot have "+numRail+" railroads");
		}
	}
}
