package edu.brown.cs32.MFTG.tournament;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.google.common.collect.Lists;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.tournament.data.DataProcessor;
import edu.brown.cs32.MFTG.tournament.data.GameDataAccumulator;

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
		
		Client p = new HumanClient();
//		List<GameData> data = p.playGames(players, seeds, null);
//		
//		assertTrue(data.size() == seeds.size());
	}
	
//	@Test
	public void shouldOverflowTest(){
		
		//must comment out gui stuff in addGameData method
		int numGames = 100000;
		Random rand = new Random();
		List<Long> seeds = new ArrayList<>();
		for(int i = 0; i < numGames; i++){
			seeds.add(rand.nextLong());
		}
		
		List<Player> players = new ArrayList<>();

		players.add(new Player(0));
		players.add(new Player(1));
		players.add(new Player(2));
		players.add(new Player(3));
		
		Client p = new HumanClient();
		p.playGames(players, seeds, new Settings(0, 0, false, -1, false, null, null, -1, -1));
	}
	
	@Test
	public void shouldNotOverflowTest(){
		int numGames = 100000;
		Random rand = new Random();
		List<List<Long>> seeds = new ArrayList<>();
		List<Long> temp = new ArrayList<>();
		for(int i = 0; i < numGames; i++){
			temp.add(rand.nextLong());
			if(temp.size() >= 500){
				seeds.add(temp);
				temp = new ArrayList<>();
			}
		}
		
		List<Player> players = new ArrayList<>();

		players.add(new Player(0));
		players.add(new Player(1));
		players.add(new Player(2));
		players.add(new Player(3));
		
		Client p = new HumanClient();

		GameDataAccumulator g = null;
		Settings settings = new Settings(0, 0, false, -1, false, null, null, -1, -1);
		
//		for(int i = 0; i < seeds.size(); i++){
//			if(g == null){
//				g = DataProcessor.aggregate(p.playGames(players, seeds.get(i), settings), 50);
//			} else {
//				DataProcessor.aggregate(g, DataProcessor.aggregate(p.playGames(players, seeds.get(i), settings), 50));
//			}
//		}
//		System.out.println(g.toGameDataReport().toString());
	}
	
//	@Test
	public void playManyGamesTest(){
		int numGames = 10000;
		//TODO get exception when numGames gets large
		Client p = new HumanClient();
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
		
//		List<GameData> data = p.playGames(players, seeds, null);
//		
//		assertTrue(data.size() == seeds.size());
	}
	
}
