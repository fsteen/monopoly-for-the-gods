package edu.brown.cs32.MFTG.monopoly;


/**
 * models a GoToJail space on board
 * @author JudahSchvimer
 *
 */
public class GoToJailSpace extends Space {

	/**
	 * Constructs a go space with a position
	 * @param position
	 */
	public GoToJailSpace(int position) {
		super(position, "Go To Jail");
		
	}
	
	@Override
	/**
	 * Models a FreeParking space
	 */
	public void react(Game game, GamePlayer player){
		game.sendPlayerToJail(player);
	}

}
