package edu.brown.cs32.MFTG.monopoly;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameTest {

	@Test
	//tests two players with no freeparking, double on go, or auctions.
	public void simpletest() {
		Player p0=new Player(0);
		Player p1=new Player(1);
		
		Game g = new Game(10, -1,false, false, p0,p1);
		g.run();
		
	}

}
