package edu.brown.cs32.MFTG.monopoly;


/**
 * models a FreeParking space on board
 * @author JudahSchvimer
 *
 */
public class FreeParkingSpace extends Space {

	/**
	 * Constructs a freeparking space with a position
	 * @param position
	 */
	public FreeParkingSpace(int position) {
		super(position, "Free Parking");
		
	}
	
	@Override
	/**
	 * Models a FreeParking space
	 */
	public void react(Game game, GamePlayer player){
		game.giveFreeParkingMoney(player);
	}

}
