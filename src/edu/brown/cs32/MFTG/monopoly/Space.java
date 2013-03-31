package edu.brown.cs32.MFTG.monopoly;


/**
 * Class modeling a space on the board
 * @author JudahSchvimer
 *
 */
public abstract class Space {
	private int _position;
	/**
	 * constructs the space with a positon
	 * @param position
	 */
	public Space(int position){
		_position=position;
	}
	
	/**
	 * react to landing on space
	 * @param game
	 * @param currentPlayer
	 */
	public void react(Game game, GamePlayer currentPlayer) throws Exception{}
	
	/**
	 * 
	 * @return position on board of space
	 */
	public int getPosition(){
		return _position;
	}
	
	/**
	 * 
	 * @return property on space, or null if it's not a property space
	 */
	public Property getProperty(){
		return null;
	}
}
