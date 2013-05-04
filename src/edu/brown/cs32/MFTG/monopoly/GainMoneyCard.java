package edu.brown.cs32.MFTG.monopoly;

/**
 * models a community chest card that gives player money.
 * @author JudahSchvimer
 *
 */
public class GainMoneyCard implements Card {
	private int _amountReceived;
	private Deck _deck;
	public GainMoneyCard(int amountReceived, Deck deck) {
		_amountReceived=amountReceived;
		_deck=deck;
	}

	@Override
	public void react(Game game, GamePlayer currentPlayer) {
		currentPlayer.addMoney(_amountReceived);
		_deck.putCardOnBottom(this);
	}

}

