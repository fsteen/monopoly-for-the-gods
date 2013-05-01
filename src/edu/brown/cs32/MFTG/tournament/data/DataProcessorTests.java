package edu.brown.cs32.MFTG.tournament.data;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	
	@Test
	public void createRandomGameData(){
		int numGames = 100;
		int numTimestamps = 4;
		int numPlayers = 4;
		int numProperties = 5; 
		
		List<List<List<Double>>> totCash = new ArrayList<>();
		List<List<Double>> cash; //for each timestamp for all players
		List<Double> cashAtT;
		
		List<List<List<Double>>> totWealth = new ArrayList<>();
		List<List<Double>> wealth; //for each timestamp for all players
		List<Double> wealthAtT;
		
//		List<List<List<Double>>> totHouses = new ArrayList<>();
//		List<List<Double>> houses; //for each timestamp for all players
//		List<Double> housesAtT;
//		
//		List<List<List<Double>>> totPRevW = new ArrayList<>();
//		List<List<Double>> pRevW; //for each timestamp for all players
//		List<Double> pRevWAtT;
//		
//		List<List<List<Double>>> totPRevWO = new ArrayList<>();
//		List<List<Double>> pRevWO; //for each timestamp for all players
//		List<Double> pRevWOAtT;
//		
//		List<List<List<Double>>> totTRevW = new ArrayList<>();
//		List<List<Double>> tRevW; //for each timestamp for all players
//		List<Double> tRevWAtT;
//		
//		List<List<List<Double>>> totTRevWO = new ArrayList<>();
//		List<List<Double>> tRevWO; //for each timestamp for all players
//		List<Double> tRevWOAtT;
		
//		List<PropertyData> tPDLsist;
		List<PlayerWealthData> tPWDList;
		
		ArrayList<TimeStamp> timeStamps;
		TimeStamp tTS;
		
		List<GameData> gameData = new ArrayList<>();
		GameData tGD;
		
//		int randPlayer;
//		List<List<Integer>> randPlayers = new ArrayList<>(); //for each 
//		List<Integer> tempRandPlayers;
		
		for(int gameNum = 0; gameNum < numGames; gameNum ++){
			cash = new ArrayList<>();
			wealth = new ArrayList<>();
//			houses = new ArrayList<>();
//			pRevW = new ArrayList<>();
//			pRevWO = new ArrayList<>();
//			tRevW = new ArrayList<>();
//			tRevWO = new ArrayList<>();
			timeStamps = new ArrayList<>();
//			tempRandPlayers = new ArrayList<>();
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
				
//				housesAtT = new ArrayList<>();
//				pRevWAtT = new ArrayList<>();
//				pRevWOAtT = new ArrayList<>();
//				tRevWAtT = new ArrayList<>();
//				tRevWOAtT = new ArrayList<>();
//				tPDList = new ArrayList<>();
//				for(int k = 0; k < numProperties; k++){
//					housesAtT.add(Math.random() * 5);
//					pRevWAtT.add(Math.random() * 100);
//					pRevWOAtT.add(Math.random() * 50);
//					tRevWAtT.add(Math.random() * 200);
//					tRevWOAtT.add(Math.random() * 75);
//					randPlayer = (int)(Math.random() * numPlayers);
//					tempRandPlayers.add(randPlayer);
//					tPDList.add(new PropertyData("Property" + k,randPlayer,housesAtT.get(k),pRevWAtT.get(k),pRevWOAtT.get(k),tRevWAtT.get(k),tRevWOAtT.get(k),false));
//				}
//				pRevW.add(pRevWAtT);
//				pRevWO.add(pRevWOAtT);
//				tRevW.add(tRevWAtT);
//				tRevWO.add(tRevWOAtT);
//				houses.add(housesAtT);
//				randPlayers.add(tempRandPlayers);
				
				tTS = new TimeStamp(i);
//				tTS.setPropertyData(tPDList);
				tTS.setWealthData(tPWDList);
				timeStamps.add(tTS);
			}
			
			tGD = new GameData(numPlayers);
			tGD.setData(timeStamps);
			gameData.add(tGD);
			totCash.add(cash);
			totWealth.add(wealth);
//			totHouses.add(houses);
//			totPRevW.add(pRevW);
//			totPRevWO.add(pRevWO);
//			totTRevW.add(tRevW);
//			totTRevWO.add(tRevWO);
		}
		
		GameDataReport calculated = DataProcessor.aggregate(gameData, numTimestamps);
		
		GameDataReport actual = createGDR(totCash,totWealth);
		
		System.out.println(calculated);
		System.out.println(actual);
	}
	
	public GameDataReport createGDR(List<List<List<Double>>> cash, List<List<List<Double>>> wealth){ //, List<List<List<Double>>> houses, 
//			List<List<List<Double>>> pRevW, List<List<List<Double>>> pRevWO, List<List<List<Double>>> tRevW,
//			List<List<List<Double>>> tRevWO, List<List<List<Integer>>> randPlayers){
	
		List<List<Double>> aveCash = new ArrayList<>();
		List<List<Double>> aveWealth = new ArrayList<>();
//		List<List<Double>> aveHouses = new ArrayList<>();
//		List<List<Double>> avePRevW = new ArrayList<>();
//		List<List<Double>> avePRevWO = new ArrayList<>();
//		List<List<Double>> aveTRevW = new ArrayList<>();
//		List<List<Double>> aveTRevWO = new ArrayList<>();
		
		List<Double> cashAtT;
		List<Double> wealthAtT;
//		List<Double> housesAtT;
//		List<Double> pRevWAtT;
//		List<Double> pRevWOAtT;
//		List<Double> tRevWAtT;
//		List<Double> tRevWOAtT;
		double cashSum;
		double wealthSum;
		
//		Map<Integer, Double> pRevWSum;
//		Map<Integer, Double> pRevWOSum;
//		Map<Integer, Double> tRevWSum;
//		Map<Integer, Double> tRevWOSum;
//		double housesSum;
//		
//		double housesMax;
//		Map<Integer, Double> pRevWMax;
//		Map<Integer, Double> pRevWOMax;
//		Map<Integer, Double> tRevWMax;
//		Map<Integer, Double> tRevWOMax;
//		
//		PropertyDataReport p;
		PlayerWealthDataReport w;
		Map<Integer,PlayerWealthDataReport> wAtT;
//		Map<String,PropertyDataReport> pAtT = new HashMap<>();
//		Map<String,List<PropertyDataReport>> playerPAtT = new HashMap<>();
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
		///////////////////////////////////////////////////////////////
//		
//		for(int property = 0; property < houses.get(0).get(0).size(); property ++){
//			
//			 pRevWSum = new HashMap<>();
//			 pRevWOSum = new HashMap<>();
//			 tRevWSum = new HashMap<>();
//			 tRevWOSum = new HashMap<>();
//			 
//			 housesSum = 0;
//			for(int gameNum = 0; gameNum < cash.size(); gameNum ++){
//				housesMax = 0;
//				pRevWMax = new HashMap<>();
//				pRevWOMax = new HashMap<>();
//				 tRevWMax = new HashMap<>();
//				 tRevWOMax = new HashMap<>();
//
//
//				for(int time = 0; time < cash.get(0).size(); time ++){
//					
//					housesMax = Math.max(houses.get(gameNum).get(time).get(property),housesMax);
//					pRevWMax.put(randPlayers.get(gameNum).get(time).get(property), )
//					
//					pRevWMax = Math.max(pRevW.get(gameNum).get(time).get(property),pRevWMax);
//					pRevWOMax = Math.max(pRevWO.get(gameNum).get(time).get(property),pRevWOMax);
//					tRevWMax = Math.max(tRevW.get(gameNum).get(time).get(property),tRevWMax);
//					tRevWOMax = Math.max(tRevWO.get(gameNum).get(time).get(property),tRevWOMax);
//				}
//				housesSum += housesMax;
//				pRevWSum += pRevWMax;
//				pRevWOSum += pRevWOMax;
//				tRevWSum += tRevWMax;
//				tRevWOSum += tRevWOMax;
//			}
//			
//			p = new PropertyDataReport("Property" + property, -1, housesSum/cash.size(), tRevWSum/cash.size(), tRevWOSum/cash.size(), 0.0, 0.0, cash.size());
//			pAtT.put(p.propertyName, p);
//		}
		
		return new GameDataReport(tStamps, 0, null, null);		
	}
	

}