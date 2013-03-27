package edu.brown.cs32.MFTG.monopoly;


/**
 * This class models a space with a property on it
 * @author JudahSchvimer
 *
 */
public class PropertySpace extends Space {
	private Property _property;
	
	/**
	 * Constructs the space with a property and position
	 * @param position
	 * @param property
	 */
	public PropertySpace(int position, Property property) {
		super(position);
		_property=property;
	}

	@Override
	/**
	 * Reacts by allowing the user to buy it and if it'snot bought auction it
	 */
	public void react(Game game, GamePlayer currentPlayer) {
		if(_property.getOwner()==null){
			try {
				currentPlayer.payMoney(_property.getRent());
				_property.getOwner().addMoney(_property.getRent());
			} catch (Exception e) {
				System.out.println("ERROR: "+e.getMessage());
			}

		}
		boolean bought=currentPlayer.buyProperty(_property);
		if(!bought){
			game.auction(_property);
		}
	}

}
