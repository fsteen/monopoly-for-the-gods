package edu.brown.cs32.MFTG.monopoly;

/**
 * models a community chest card that gives player money From Each Player.
 * @author JudahSchvimer
 *
 */
public class GainMoneyFromEachPlayerCard implements Card {
	private int _amountReceived;
	public GainMoneyFromEachPlayerCard(int amountReceived) {
		_amountReceived=amountReceived;
	}

	@Override
	public void react(Game game, GamePlayer currentPlayer) {
		for (GamePlayer p: game.getOtherPlayers(currentPlayer)){
			game.transferMoney(p, currentPlayer, _amountReceived);
		}
	}

}

