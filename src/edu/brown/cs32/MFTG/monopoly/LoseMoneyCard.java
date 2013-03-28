package edu.brown.cs32.MFTG.monopoly;

/**
 * models a community chest card that takes players money.
 * @author JudahSchvimer
 *
 */
public class LoseMoneyCard implements Card {
	private int _amountLost;
	public LoseMoneyCard(int amountLost) {
		_amountLost=amountLost;
	}

	@Override
	public void react(Game game, GamePlayer currentPlayer) {
		game.transferMoney(currentPlayer, null, _amountLost);
		
	}

}

