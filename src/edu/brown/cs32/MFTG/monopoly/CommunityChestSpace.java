package edu.brown.cs32.MFTG.monopoly;


/**
 * models a community chest space on board
 * @author JudahSchvimer
 *
 */
public class CommunityChestSpace extends Space{
	
	/**
	 * creates community chest space
	 * @param position
	 */
	public CommunityChestSpace(int position) {
		super(position);
	}
	
	@Override
	/**
	 * Reacts by allowing the user to pick a card
	 */
	public void react(Game game, GamePlayer currentPlayer) throws Exception {
		Card c=game.getCommunityChestDeck().drawCard();
		c.react(game, currentPlayer);	
	}

}
