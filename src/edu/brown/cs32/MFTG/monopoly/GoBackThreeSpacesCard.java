package edu.brown.cs32.MFTG.monopoly;

/**
 * models a community chest card that moves play back three spaces.
 * @author JudahSchvimer
 *
 */
public class GoBackThreeSpacesCard implements Card {
	private Deck _deck;
	public GoBackThreeSpacesCard(Deck deck) {
		_deck=deck;
	}

	@Override
	public void react(Game game, GamePlayer currentPlayer) throws Exception {
		currentPlayer.setPosition(currentPlayer.getPosition()-3);
		game.getSpace(currentPlayer.getPosition()-3).react(game, currentPlayer);
		_deck.putCardOnBottom(this);
	}

}
