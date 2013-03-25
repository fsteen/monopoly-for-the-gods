package edu.brown.cs32.MFTG.monopoly;

public class UtilityProperty extends Property {

	public UtilityProperty(String name) {
		super(name, 150, 75, 0, Integer.MAX_VALUE, Integer.MAX_VALUE,Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	@Override
	/**
	 * adds a house to the property
	 * @throws Exception 
	 */
	public void addHouse() throws Exception{
		throw new Exception ("Cannot add house to a utility");
	}

	@Override
	/**
	 * 
	 * @return rent owed
	 */
	public int getRent() throws Exception{
		int numUtil=this.getOwner().getNumUtilities();
		Dice dice = new Dice();
		switch(numUtil){
		case 1:  return dice.rollDice()*4;
		case 2:  return dice.rollDice()*10;
		default: throw new Exception ("Cannot have "+numUtil+" utilities");
		}
	}
}
