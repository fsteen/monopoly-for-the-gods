package edu.brown.cs32.MFTG.monopoly;


/**
 * models a go space on board
 * @author JudahSchvimer
 *
 */
public class GoSpace extends Space {

	/**
	 * Constructs a go space with a position
	 * @param position
	 */
	public GoSpace(int position) {
		super(position);
		
	}
	
	@Override
	/**
	 * Models a go space
	 */
	public void react(Game game, GamePlayer player){
		if(game.playWithDoubleOnGo())
			player.addMoney(200);
	}

}
