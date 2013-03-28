package edu.brown.cs32.MFTG.monopoly;


/**
 * models a IncomeTax space on board
 * @author JudahSchvimer
 *
 */
public class IncomeTaxSpace extends Space {

	/**
	 * Constructs a income tax space with a position
	 * @param position
	 */
	public IncomeTaxSpace(int position) {
		super(position);
		
	}
	
	@Override
	/**
	 * Models a income tax space
	 */
	public void react(Game game, GamePlayer player){
		game.transferMoney(player, null, Math.min(200, player.getTotalWealth()/10));
	}

}
