package edu.brown.cs32.MFTG.monopoly;

/**
 * models a community chest card that advances player to jail
 * @author JudahSchvimer
 *
 */
public class GoToJailCard implements Card {

	public GoToJailCard() {}

	@Override
	public void react(Game game, GamePlayer currentPlayer) {
		game.sendPlayerToJail(currentPlayer);
	}

}
