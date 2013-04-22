package edu.brown.cs32.MFTG.monopoly;


/**
 * models a IncomeTax space on board
 * @author JudahSchvimer
 *
 */
public class LuxuryTaxSpace extends Space {

	/**
	 * Constructs a  LuxuryTax space with a position
	 * @param position
	 */
	public LuxuryTaxSpace(int position) {
		super(position, "Luxury Tax");
		
	}
	
	@Override
	/**
	 * Models a  LuxuryTax space
	 */
	public void react(Game game, GamePlayer player) throws Exception{
		game.transferMoney(player, null, 75);
	}

}
