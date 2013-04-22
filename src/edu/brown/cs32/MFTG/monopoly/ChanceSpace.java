package edu.brown.cs32.MFTG.monopoly;


/**
 * models a chance space on board
 * @author JudahSchvimer
 *
 */
public class ChanceSpace extends Space{
	
	/**
	 * creates chance space
	 * @param position
	 */
	public ChanceSpace(int position) {
		super(position, "Chance");
	}
	
	@Override
	/**
	 * Reacts by allowing the user to pick a card
	 */
	public void react(Game game, GamePlayer currentPlayer) throws Exception {
		Card c=game.getChanceDeck().drawCard();
		c.react(game, currentPlayer);	
	}

}
