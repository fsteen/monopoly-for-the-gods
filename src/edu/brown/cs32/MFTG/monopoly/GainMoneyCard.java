package edu.brown.cs32.MFTG.monopoly;

/**
 * models a community chest card that gives player money.
 * @author JudahSchvimer
 *
 */
public class GainMoneyCard implements Card {
	private int _amountReceived;
	public GainMoneyCard(int amountReceived) {
		_amountReceived=amountReceived;
	}

	@Override
	public void react(Game game, GamePlayer currentPlayer) {
		currentPlayer.addMoney(_amountReceived);
	}

}

