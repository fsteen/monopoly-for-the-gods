package edu.brown.cs32.MFTG.monopoly;

import java.util.List;

/**
 * models a community chest card that takes players money based on number of houses and hotels.
 * @author JudahSchvimer
 *
 */
public class PayPerHouseCard implements Card {
	private int _houseCost,_hotelCost;
	public PayPerHouseCard(int houseCost, int hotelCost) {
		_houseCost=houseCost;
		_hotelCost=hotelCost;
	}

	@Override
	public void react(Game game, GamePlayer currentPlayer) {
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
	}

}

