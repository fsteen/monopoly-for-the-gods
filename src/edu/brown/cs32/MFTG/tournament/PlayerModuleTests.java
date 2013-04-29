package edu.brown.cs32.MFTG.tournament;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;

public class PlayerModuleTests {

//	@Test
	public void threadingTest(){
		//NOTE : have GameRunnerFactory construct DummyGameRunners for this test
		
		List<Long> seeds = new ArrayList<>();
		seeds.add((long) 1);
		seeds.add((long) 1);
		seeds.add((long) 1);
		seeds.add((long) 1);
		seeds.add((long) 1);

		PlayerModule p = new PlayerModule("fakehost", -1);
		p.playGames(new ArrayList<Player>(), seeds);
		
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
		
		PlayerModule p = new PlayerModule("fakehost", -1);
		List<GameData> data = p.playGames(players, seeds);
		
		assertTrue(data.size() == seeds.size());
	}
	
	public static void playManyGamesTest(){
		int numGames = 1000;
		//TODO get exception when numGames gets large
		
		List<Player> players = new ArrayList<>();
		players.add(new Player(0));
		players.add(new Player(1));
		players.add(new Player(2));
		players.add(new Player(3));
		
		Random rand = new Random();
		List<Long> seeds = new ArrayList<>();
		for(int i = 0; i < numGames; i++){
			seeds.add(rand.nextLong());
		}
		
		PlayerModule p = new PlayerModule("fakehost", -1);
		List<GameData> data = p.playGames(players, seeds);
		
		assertTrue(data.size() == seeds.size());
	}
	
	public static void main (String[] args) {
		playManyGamesTest();
		System.out.println("IM DONE!");
	}
	
}
