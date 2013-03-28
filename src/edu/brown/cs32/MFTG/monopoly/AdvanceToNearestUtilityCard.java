package edu.brown.cs32.MFTG.monopoly;

/**
 * models a community chest card that advances player to a utility.
 * @author JudahSchvimer
 *
 */
public class AdvanceToNearestUtilityCard implements Card {
	public AdvanceToNearestUtilityCard() {}

	@Override
	public void react(Game game, GamePlayer currentPlayer) {
		String space;
		if(currentPlayer.getPosition()<12){
			space="electric company";
		}
		else{
			space="water works";
		}
		Property p=game.movePlayer(currentPlayer, space).getProperty();
		
		if(p.getOwner()!=null){
			try {
				if(p.getMortgagedState()){
					return;
				}
				Dice dice = new Dice();
				int rent =dice.rollDice()*10;
				p.addRevenue(rent);
				game.transferMoney(currentPlayer, p.getOwner(), rent);
			} catch (Exception e) {
				System.out.println("ERROR: "+e.getMessage());
			}

		}
		else{
			boolean bought=currentPlayer.buyProperty(p);
			if(!bought){
				game.auction(p);
			}
		}
	}

}
