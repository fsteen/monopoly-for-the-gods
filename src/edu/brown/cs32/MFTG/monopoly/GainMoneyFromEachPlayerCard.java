package edu.brown.cs32.MFTG.monopoly;

/**
 * models a community chest card that gives player money From Each Player.
 * @author JudahSchvimer
 *
 */
public class GainMoneyFromEachPlayerCard implements Card {
	private int _amountReceived;
	private Deck _deck;
	public GainMoneyFromEachPlayerCard(int amountReceived, Deck deck) {
		_amountReceived=amountReceived;
		_deck=deck;
	}

	@Override
	public void react(Game game, GamePlayer currentPlayer) throws Exception {
		for (GamePlayer p: game.getOtherPlayers(currentPlayer)){
			game.transferMoney(p, currentPlayer, _amountReceived);
		}
		_deck.putCardOnBottom(this);
	}

}

