package edu.brown.cs32.MFTG.monopoly;

/**
 * models a community chest card that advances player to jail
 * @author JudahSchvimer
 *
 */
public class GoToJailCard implements Card {

	private Deck _deck;
	public GoToJailCard(Deck deck) {
		_deck=deck;
	}

	@Override
	public void react(Game game, GamePlayer currentPlayer) {
		game.sendPlayerToJail(currentPlayer);
		_deck.putCardOnBottom(this);
	}

}
