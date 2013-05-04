package edu.brown.cs32.MFTG.monopoly;

/**
 * models a community chest card that advances player to a utility.
 * @author JudahSchvimer
 *
 */
public class AdvanceToNearestRailroadCard implements Card {
	private Deck _deck;
	public AdvanceToNearestRailroadCard(Deck deck) {
		_deck=deck;
	}

	@Override
	public void react(Game game, GamePlayer currentPlayer) throws Exception {
		String space;
		if(currentPlayer.getPosition()<5){
			space="reading railroad";
		}
		else if(currentPlayer.getPosition()<15){
			space="pennsylvania railroad";
		}
		else if(currentPlayer.getPosition()<25){
			space="b and o railroad";
		}
		else{
			space="short line";
		}

		Property p=game.movePlayer(currentPlayer, space).getProperty();

		if(p.getOwner()!=null){
			if(p.getMortgagedState()){
				return;
			}
			int rent =p.getRent()*2;
			p.addRevenue(rent);
			game.transferMoney(currentPlayer, p.getOwner(), rent);

		}
		else{
			boolean bought=currentPlayer.buyProperty(p);
			if(!bought){
				game.auction(p);
			}
		}
		_deck.putCardOnBottom(this);
	}

}