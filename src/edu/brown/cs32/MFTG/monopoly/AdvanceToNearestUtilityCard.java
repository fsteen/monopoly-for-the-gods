package edu.brown.cs32.MFTG.monopoly;

/**
 * models a community chest card that advances player to a utility.
 * @author JudahSchvimer
 *
 */
public class AdvanceToNearestUtilityCard implements Card {
	private Deck _deck;
	public AdvanceToNearestUtilityCard(Deck deck) {
		_deck=deck;
	}

	@Override
	public void react(Game game, GamePlayer currentPlayer) throws Exception {
		String space;
		if(currentPlayer.getPosition()<12){
			space="electric company";
		}
		else{
			space="water works";
		}
		Property p=game.movePlayer(currentPlayer, space).getProperty();

		if(p.getOwner()!=null){
			if(p.getMortgagedState()){
				return;
			}
			int rent =game.getDice().rollDice()*10;
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
