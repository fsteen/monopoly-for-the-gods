package edu.brown.cs32.MFTG.monopoly;

import java.util.List;

/**
 * models a community chest card that makes player given each player money
 * @author JudahSchvimer
 *
 */
public class LoseMoneyToEachPlayerCard implements Card {
	private int _amountLost;
	private Deck _deck;
	public LoseMoneyToEachPlayerCard(int amountLost, Deck deck) {
		_amountLost=amountLost;
		_deck=deck;
	}

	@Override
	public void react(Game game, GamePlayer currentPlayer) throws Exception {
		int total=currentPlayer.payMoney(_amountLost);
		List<GamePlayer> others =game.getOtherPlayers(currentPlayer);
		int numPlayers=others.size();
		for (GamePlayer p: game.getOtherPlayers(currentPlayer)){
			p.payMoney(total/numPlayers);
		}
		if(total<_amountLost){
			game.bankruptPlayer(currentPlayer, null);
		}
		_deck.putCardOnBottom(this);
	}

}

