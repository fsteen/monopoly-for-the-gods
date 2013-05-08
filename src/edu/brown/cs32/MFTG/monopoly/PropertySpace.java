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
		super(position, property.Name);
		_property=property;
	}

	@Override
	/**
	 * Reacts by allowing the user to buy it and if it'snot bought auction it
	 */
	public void react(Game game, GamePlayer currentPlayer) throws Exception {
		if(_property.getOwner()!=null){
			if(_property.getOwner()==currentPlayer) {
				return;
			}
			int rent =_property.getRent();
			game.transferMoney(currentPlayer, _property.getOwner(), rent);
			_property.addRevenue(rent);

		}
		else{
			boolean bought=currentPlayer.buyProperty(_property);
			if(!bought){
				game.auction(_property);
			}
			else{
				//System.out.println(String.format("%s bought %s for %d", currentPlayer, _property, _property.Price));
			}
		}

	}

	@Override
	public Property getProperty(){
		return _property;
	}

}
