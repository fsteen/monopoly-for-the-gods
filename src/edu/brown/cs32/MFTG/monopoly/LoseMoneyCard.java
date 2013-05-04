package edu.brown.cs32.MFTG.monopoly;

/**
 * models a community chest card that takes players money.
 * @author JudahSchvimer
 *
 */
public class LoseMoneyCard implements Card {
	private int _amountLost;
	private Deck _deck;
	public LoseMoneyCard(int amountLost, Deck deck) {
		_amountLost=amountLost;
		_deck=deck;
	}

	@Override
	public void react(Game game, GamePlayer currentPlayer) throws Exception {
		game.transferMoney(currentPlayer, null, _amountLost);
		_deck.putCardOnBottom(this);
		
	}

}

