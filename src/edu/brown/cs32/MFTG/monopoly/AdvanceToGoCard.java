package edu.brown.cs32.MFTG.monopoly;

/**
 * models a community chest card that advances player to go.
 * @author JudahSchvimer
 *
 */
public class AdvanceToGoCard implements Card {

	public AdvanceToGoCard() {}

	@Override
	public void react(Game game, GamePlayer currentPlayer) throws Exception {
		Space s=game.movePlayer(currentPlayer, "go");
		s.react(game, currentPlayer);
	}

}
