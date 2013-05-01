package edu.brown.cs32.MFTG.tournament.data;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.brown.cs32.MFTG.monopoly.Game;
import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.monopoly.PlayerWealthData;
import edu.brown.cs32.MFTG.monopoly.PropertyData;
import edu.brown.cs32.MFTG.monopoly.TimeStamp;


public class DataProcessorTests {

//	@Test
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
	
//	@Test
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
	
//	@Test
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
	
//	@Test
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
		System.out.println(DataProcessor.aggregate(data, 50).toString());
	}
	
	@Test
	public void createRandomGameData(){
		int numGames = 1000;
		int numTimestamps = 100;
		int numPlayers = 4;
		
		List<List<List<Double>>> totCash = new ArrayList<>();
		List<List<Double>> cash; //for each timestamp for all players
		List<Double> cashAtT;
		
		List<List<List<Double>>> totWealth = new ArrayList<>();
		List<List<Double>> wealth; //for each timestamp for all players
		List<Double> wealthAtT;
		
		List<PlayerWealthData> tPWDList;
		
		ArrayList<TimeStamp> timeStamps;
		TimeStamp tTS;
		
		List<GameData> gameData = new ArrayList<>();
		GameData tGD;
		for(int gameNum = 0; gameNum < numGames; gameNum ++){
			cash = new ArrayList<>();
			wealth = new ArrayList<>();
			timeStamps = new ArrayList<>();
			for(int i = 0; i < numTimestamps; i++){
				cashAtT = new ArrayList<>();
				wealthAtT = new ArrayList<>();
				tPWDList = new ArrayList<>();
				for(int j = 0; j < numPlayers; j++){
					cashAtT.add(Math.random() * 200);
					wealthAtT.add(Math.random() * 1000);
	
					tPWDList.add(new PlayerWealthData(j,cashAtT.get(j),wealthAtT.get(j))); //add the new player wealth data
				}
				wealth.add(wealthAtT);
				cash.add(cashAtT);
				tTS = new TimeStamp(i);
				tTS.setWealthData(tPWDList);
				timeStamps.add(tTS);
			}
			
			tGD = new GameData(numPlayers);
			tGD.setData(timeStamps);
			gameData.add(tGD);
			totCash.add(cash);
			totWealth.add(wealth);
		}
		
		GameDataReport calculated = DataProcessor.aggregate(gameData, numTimestamps);
		
		GameDataReport actual = createGDR(totCash,totWealth);
		
		assertTrue(calculated._timeStamps.size() == actual._timeStamps.size());
		Map<Integer,PlayerWealthDataReport> c;
		Map<Integer,PlayerWealthDataReport> a;
		for(int i = 0; i < actual._timeStamps.size(); i++){
			c = calculated._timeStamps.get(i).wealthData;
			a = actual._timeStamps.get(i).wealthData;
			
			assertTrue(c.values().size() == a.values().size());
			
			PlayerWealthDataReport temp;
			for(Entry<Integer, PlayerWealthDataReport> d : a.entrySet()){
				temp = c.get(d.getKey());
				assertTrue(temp != null);
				assertTrue(temp.accCash == d.getValue().accCash);
				assertTrue(temp.accTotalWealth == d.getValue().accTotalWealth);
				assertTrue(temp.numDataPoints == d.getValue().numDataPoints);
			}
		}
	}
	
	public GameDataReport createGDR(List<List<List<Double>>> cash, List<List<List<Double>>> wealth){ 
		double cashSum;
		double wealthSum;
		PlayerWealthDataReport w;
		Map<Integer,PlayerWealthDataReport> wAtT;
		List<TimeStampReport> tStamps = new ArrayList<>();

		for(int time = 0; time < cash.get(0).size(); time ++){
			wAtT = new HashMap<>();
			
			for(int player = 0; player < cash.get(0).get(0).size(); player ++){
				 cashSum = 0;
				 wealthSum = 0;
				
				for(int gameNum = 0; gameNum < cash.size(); gameNum ++){
					cashSum += cash.get(gameNum).get(time).get(player);
					wealthSum += wealth.get(gameNum).get(time).get(player);
				}

				w = new PlayerWealthDataReport(-1, cashSum/cash.size(), wealthSum/cash.size(), cash.size());
				wAtT.put(player,w);
			}
				
			tStamps.add(new TimeStampReport(time, wAtT));
		}
		
		return new GameDataReport(tStamps, 0, null, null);		
	}
}