package edu.brown.cs32.MFTG.tournament.data;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import edu.brown.cs32.MFTG.monopoly.Game;
import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;


public class DataProcessorTests {

	@Test
	public void generateConfirmationIndicesTest(){
		List<Integer> confirmationIndices1 = DataProcessor.generateConfirmationIndices(100, .10);
		assertTrue(confirmationIndices1.size() == 10);
		for(int i = 1; i < confirmationIndices1.size(); i++){
			assertTrue(confirmationIndices1.get(i-1) < confirmationIndices1.get(i));
		}
//		System.out.println(confirmationIndices1);
		
		List<Integer> confirmationIndices2 = DataProcessor.generateConfirmationIndices(10, .09);
		assertTrue(confirmationIndices2.size() == 1);
		for(int i = 1; i < confirmationIndices2.size(); i++){
			assertTrue(confirmationIndices2.get(i-1) < confirmationIndices2.get(i));
		}
//		System.out.println(confirmationIndices2);
	}
	
	@Test
	public void generateSeedsTest(){
		int numGames1 = 100;
		int numPlayers1 = 4;
		List<Integer> confirmationIndices1 = DataProcessor.generateConfirmationIndices(numGames1, .10);
		List<List<Long>> seeds1 = DataProcessor.generateSeeds(numGames1, numPlayers1, confirmationIndices1);
		
		long seed1;
		for(int i = 0; i < confirmationIndices1.size(); i++){
			seed1 = seeds1.get(confirmationIndices1.get(i)).get(0);
			for(int j = 0; j < numPlayers1; j++){
				assertTrue(seeds1.get(confirmationIndices1.get(i)).get(j) == seed1);
			}
		}
//		System.out.println(seeds1);
	}
	
//	@Test
	public void generateGameDataReportSingleGame(){
		Player p0=new Player(0);
		Player p1=new Player(1);
		
		Game g = new Game(13,1000,500, false, false, p0, p1);
		g.run();
		
		List<List<GameData>> data = new ArrayList<>();
		List<GameData> temp = new ArrayList<>();
		temp.add(g.getGameData());
		data.add(temp);
		
		System.out.println(DataProcessor.aggregate(data, 500).toString());
	}
	
//	@Test
	public void generateGameDataReportMany(){
		Random rand = new Random();
		Player p0=new Player(0);
		Player p1=new Player(1);
		
		List<List<GameData>> data = new ArrayList<>();
		List<GameData> temp;
		Game g;
		
		for(int i = 0; i < 100; i++){ //play 400 games
			temp = new ArrayList<>();
			for(int j = 0; j < 4; j++){
				g = new Game(rand.nextLong(),1000,500, false, false, p0, p1);
				g.run();
				temp.add(g.getGameData());
			}
			data.add(temp);
		}
		System.out.println(DataProcessor.aggregate(data, 50).toString());
	}
	
	@Test
	public void generateGameDataReportMany4Player(){
		Random rand = new Random();
		Player p0=new Player(0);
		Player p1=new Player(1);
		Player p2=new Player(2);
		Player p3=new Player(3);

		
		List<List<GameData>> data = new ArrayList<>();
		List<GameData> temp;
		Game g;
		
		for(int i = 0; i < 100; i++){ //play 400 games
			temp = new ArrayList<>();
			for(int j = 0; j < 4; j++){
				g = new Game(rand.nextLong(),1000,500, false, false, p0, p1, p2, p3);
				g.run();
				temp.add(g.getGameData());
			}
			data.add(temp);
		}
		System.out.println(DataProcessor.aggregate(data, 50).toString());
	}
}