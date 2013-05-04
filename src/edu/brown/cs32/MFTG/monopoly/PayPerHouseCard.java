package edu.brown.cs32.MFTG.monopoly;

import java.util.List;

/**
 * models a community chest card that takes players money based on number of houses and hotels.
 * @author JudahSchvimer
 *
 */
public class PayPerHouseCard implements Card {
	private int _houseCost,_hotelCost;
	private Deck _deck;
	public PayPerHouseCard(int houseCost, int hotelCost, Deck deck) {
		_houseCost=houseCost;
		_hotelCost=hotelCost;
		_deck=deck;
	}

	@Override
	public void react(Game game, GamePlayer currentPlayer) throws Exception {
		int amountToPay=0;
		List<Property> props=currentPlayer.getProperties();
		for(Property p: props){
			if(p.getNumHouses()==5){
				amountToPay+=_hotelCost;
				p.loseRevenue(_hotelCost);
			}
			else{
				int payment=p.getNumHouses()*_houseCost;
				amountToPay+=payment;
				p.loseRevenue(payment);
			}
		}
		game.transferMoney(currentPlayer, null, amountToPay);
		_deck.putCardOnBottom(this);
	}

}

