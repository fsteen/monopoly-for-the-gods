package edu.brown.cs32.MFTG.tournament;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

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
	
//	@Test
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
		p.playGames(players, seeds);
	}
	
}
