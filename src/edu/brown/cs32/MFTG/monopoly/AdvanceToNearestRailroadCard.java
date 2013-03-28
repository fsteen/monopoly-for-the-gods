package edu.brown.cs32.MFTG.monopoly;

/**
 * models a community chest card that advances player to a utility.
 * @author JudahSchvimer
 *
 */
public class AdvanceToNearestRailroadCard implements Card {
	public AdvanceToNearestRailroadCard() {}

	@Override
	public void react(Game game, GamePlayer currentPlayer) {
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
			try {
				if(p.getMortgagedState()){
					return;
				}
				int rent =p.getRent()*2;
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