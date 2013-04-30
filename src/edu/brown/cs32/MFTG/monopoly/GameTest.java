package edu.brown.cs32.MFTG.monopoly;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class GameTest {

	//@Test
	//tests two players with no freeparking, double on go, or auctions.
	public void simpletest() {
		Player p0=new Player(0);
		Player p1=new Player(1);
		
		Game g = new Game(13,1000, 500,false, false, p0,p1);
		g.run();
		g.getGameData().printData();
		
	}
	
	@Test
	//tests two players with no freeparking, double on go, or auctions.
	public void fourplayertest() {
		Player p0=new Player(0);
		Player p1=new Player(1);
		Player p2=new Player(2);
		Player p3=new Player(3);
		long rand = new Random().nextLong();
		Game g1 = new Game(rand,1000, 500,true, true, p0,p1,p2,p3);
		g1.run();
		
	}
	
	@Test
	public void thousandTest() {
		List<Exception> exc = new ArrayList<>();
		for (int i=0; i<100000; i++) {
			try {
				Player p0=new Player(0);
				Player p1=new Player(1);
				Player p2=new Player(2);
				Player p3=new Player(3);
				long rand = new Random().nextLong();
				Game g1 = new Game(rand,1000, 500,true, true, p0,p1,p2,p3);
				g1.run();
			}catch(Exception e) {
				exc.add(e);
			}
		}
		for (Exception e: exc) {
			System.out.println(e.getMessage());
		}
	}
	
	//@Test
	//1player just moving 0, landing on go each time
	public void landongo_move_test() {
		Player p0=new Player(0);
		
		Game g = new Game(13,1000, 500,true, false, p0);
		g.setDice(new MockDice(0,false));
		g.run();
		assertTrue(g.getGameData().getWinner()==-1);
		assertTrue(g.getGameData().getData().get(g.getGameData().getData().size()-1).getWealthData().get(0).cash==201500);				
	}
	
	//@Test
	//1player just moving 0, landing on go each time
	public void jail_test() {
		Player p0=new Player(0);
		
		Game g = new Game(13,5, -1,false, false, p0);
		g.setDice(new MockDice(10,false));
		g.run();
		assertTrue(g.getOtherPlayers(g.getBanker()).get(0).getTurnsInJail()==2);	
	}
	
	//@Test
	//1player just moving 0, landing on go each time
	public void freeparking_test() {
		Player p0=new Player(0);
		
		Game g = new Game(13,2, 100,false, false, p0);
		g.setDice(new MockDice(10,false));
		g.run();
		assertTrue(g.getOtherPlayers(g.getBanker()).get(0).getCash()==1600);	
	}
	
	//@Test
	//1player just moving 0, landing on go each time
	public void jailpay_test() {
		Player p0=new Player(0);
		
		Game g = new Game(13,6, -1,false, false, p0);
		g.setDice(new MockDice(10,false));
		g.run();
		assertTrue(g.getOtherPlayers(g.getBanker()).get(0).getCash()==1450);	
	}
	
	//@Test
	//1player just moving 0, landing on go each time
	//broken seed: -78727290482445436093
	
	public void equalgamestest() {
		Player p0=new Player(0);
		Player p1=new Player(1);
		Player p2=new Player(2);
		Player p3=new Player(3);
		long rand = new Random().nextLong();
		System.out.println("SEED: "+rand);
		Game g1 = new Game(rand,1000, 500,true, true, p0,p1,p2,p3);
		g1.run();
		Game g2 =  new Game(rand,1000, 500,true, true, p0,p1,p2,p3);
		g2.run();
		assertEquals(g1.getGameData(),g2.getGameData());			
	}

}
