package edu.brown.cs32.MFTG.monopoly;

/**
 * models a community chest card that gives player get out of jail free card.
 * @author JudahSchvimer
 *
 */
public class GetOutOfJailFreeCard implements Card {
	private Deck _deck;
	public GetOutOfJailFreeCard(Deck deck) {
		_deck=deck;
	}

	@Override
	public void react(Game game, GamePlayer currentPlayer) {
		currentPlayer.addGetOutOfJailFreeCard();
	}

}
