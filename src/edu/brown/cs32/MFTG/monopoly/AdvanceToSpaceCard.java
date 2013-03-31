package edu.brown.cs32.MFTG.monopoly;

/**
 * models a community chest card that advances player to a space.
 * @author JudahSchvimer
 *
 */
public class AdvanceToSpaceCard implements Card {
	private String _space;
	private Deck _deck;
	public AdvanceToSpaceCard(String space, Deck deck) {
		_space=space;
		_deck=deck;
	}

	@Override
	public void react(Game game, GamePlayer currentPlayer) throws Exception {
		Space s=game.movePlayer(currentPlayer, _space);
		s.react(game, currentPlayer);
		_deck.putCardOnBottom(this);
	}

}
