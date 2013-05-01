package edu.brown.cs32.MFTG.tournament.data;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import edu.brown.cs32.MFTG.monopoly.Game;
import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.monopoly.PlayerWealthData;
import edu.brown.cs32.MFTG.monopoly.PropertyData;
import edu.brown.cs32.MFTG.monopoly.TimeStamp;


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
		int numGames1 = 1;
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
		
		List<GameData> data = new ArrayList<>();
		data.add(g.getGameData());
		
		System.out.println(DataProcessor.aggregate(data, 500).toString());
	}
	
	@Test
	public void generateGameDataReportMany(){
		Random rand = new Random();
		Player p0=new Player(0);
		Player p1=new Player(1);
		
		List<GameData> data = new ArrayList<>();
		Game g;
		
		for(int i = 0; i < 400; i++){ //play 400 games
			g = new Game(rand.nextLong(),1000,500, false, false, p0, p1);
			g.run();
			data.add(g.getGameData());
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

		List<GameData> data = new ArrayList<>();
		Game g;
		
		for(int i = 0; i < 400; i++){ //play 400 games
			g = new Game(rand.nextLong(),1000,500, false, false, p0, p1, p2, p3);
			g.run();
			data.add(g.getGameData());
		}
		
		GameDataReport r = DataProcessor.aggregate(data, 50);
		double totalGameWealth = 3000;
		double actualGameWealth;
//		for(int i = 0; i < r._timeStamps.size();i++){ //make sure the totalGameWealth is approx correct
//			actualGameWealth = 0;
//			for(PlayerWealthDataReport w : r._timeStamps.get(i).wealthData.values()){
//				actualGameWealth += w.accTotalWealth;
//				totalGameWealth += 200;
//			}
//			System.out.println(actualGameWealth + " " + totalGameWealth);
////			assertTrue(actualGameWealth > totalGameWealth * .9 && actualGameWealth < totalGameWealth * 1.1);
//		}
//		
		System.out.println(DataProcessor.aggregate(data, 50).toString());
	}
	
	public List<GameData> createRandomGameData(){
		int num = 100;
		int numTimestamps = 100;
		int numPlayers = 4;
		int numProperties = 5; 
		List<GameData> g = new ArrayList<>();
		
		List<List<Double>> cash = new ArrayList<>(); //for each timestamp for all playes
		List<Double> cashAtT;
		
		List<List<Double>> wealth = new ArrayList<>(); //for each timestamp for all playes
		List<Double> wealthAtT;
		for(int i = 0; i < numTimestamps; i++){
			cashAtT = new ArrayList<>();
			wealthAtT = new ArrayList<>();
			for(int j = 0; j < numPlayers; j++){
				cashAtT.add(Math.random() * 200);
				wealthAtT.add(Math.random() * 1000);
			}
			wealth.add(wealthAtT);
			cash.add(cashAtT);
		}
		
		return null;
//		private ArrayList<TimeStamp> _data;
//		private int _time;
//		public final int _numPlayers;
//		private int _winner;
		
//		List<PropertyData> _propertyData;
//		List<PlayerWealthData> _wealthData;
//		int _time;
		
//		public final String propertyName;
//		public final int ownerID;
//		public final double numHouses;
//		public final double personalRevenueWithHouses;
//		public final double personalRevenueWithoutHouses;
//		public final double totalRevenueWithHouses;
//		public final double totalRevenueWithoutHouses;
//		public final boolean mortgaged;
		
//		public final int ownerID;
//		public final double cash;
//		public final double totalWealth;
	}
	

}