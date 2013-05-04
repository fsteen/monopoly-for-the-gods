package edu.brown.cs32.MFTG.monopoly;


/**
 * Class modeling a space on the board
 * @author JudahSchvimer
 *
 */
public abstract class Space {
	private int _position;
	private String _name;
	/**
	 * constructs the space with a positon
	 * @param position
	 */
	public Space(int position, String name){
		_position=position;
		_name=name;
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
	
	@Override
	public String toString() {
		return String.format("%d: %s", _position, _name);
		
	}
}
