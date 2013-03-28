package edu.brown.cs32.MFTG.monopoly;

/**
 * models a community chest card that moves play back three spaces.
 * @author JudahSchvimer
 *
 */
public class GoBackThreeSpacesCard implements Card {
	public GoBackThreeSpacesCard() {}

	@Override
	public void react(Game game, GamePlayer currentPlayer) {
		currentPlayer.setPosition(currentPlayer.getPosition()-3);
		game.getSpace(currentPlayer.getPosition()-3).react(game, currentPlayer);
	}

}
