package edu.brown.cs32.MFTG.monopoly;

/**
 * models a community chest card that advances player to a space.
 * @author JudahSchvimer
 *
 */
public class AdvanceToSpaceCard implements Card {
	private String _space;
	public AdvanceToSpaceCard(String space) {
		_space=space;
	}

	@Override
	public void react(Game game, GamePlayer currentPlayer) {
		Space s=game.movePlayer(currentPlayer, _space);
		s.react(game, currentPlayer);
	}

}
