package edu.brown.cs32.MFTG.tournament;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;

public class ClientTests {

//	@Test
	public void threadingTest(){
		//NOTE : have GameRunnerFactory construct DummyGameRunners for this test
		
		List<Long> seeds = new ArrayList<>();
		seeds.add((long) 1);
		seeds.add((long) 1);
		seeds.add((long) 1);
		seeds.add((long) 1);
		seeds.add((long) 1);

		Client p = new HumanClient();
		p.playGames(new ArrayList<Player>(), seeds, null);
		
	}
	
	@Test
	public void playGamesTest(){
		List<Player> players = new ArrayList<>();
		players.add(new Player(0));
		players.add(new Player(1));
		players.add(new Player(2));
		players.add(new Player(3));
		
		List<Long> seeds = new ArrayList<>();
		seeds.add((long) 12345);
		seeds.add((long) 54321);
		seeds.add((long) -22222);
		seeds.add((long) 98989);
		seeds.add((long) 77733);
		
		Client p = new HumanClient();
		List<GameData> data = p.playGames(players, seeds, null);
		
		assertTrue(data.size() == seeds.size());
	}
	
	public static void playManyGamesTest(){
		int numGames = 10000;
		//TODO get exception when numGames gets large
		Client p = new HumanClient();

		System.out.println("Starting to wait");
//		try {
//			Thread.sleep(30000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println("Hope you set your heuristics ...");
		
		Player player = p.getPlayer(0);
		
		List<Player> players = new ArrayList<>();
		players.add(new Player(0));
		players.add(player);
		players.add(new Player(2));
		players.add(new Player(3));
		
		Random rand = new Random();
		List<Long> seeds = new ArrayList<>();
		for(int i = 0; i < numGames; i++){
			seeds.add(rand.nextLong());
		}
		
		List<GameData> data = p.playGames(players, seeds, null);
		
		assertTrue(data.size() == seeds.size());
	}
	
	public static void main (String[] args) {
		playManyGamesTest();
		System.out.println("IM DONE!");
	}
	
}
