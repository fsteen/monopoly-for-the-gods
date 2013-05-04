package edu.brown.cs32.MFTG.networking;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.monopoly.Player.Aggression;
import edu.brown.cs32.MFTG.monopoly.Player.Amount;
import edu.brown.cs32.MFTG.monopoly.Player.Balance;
import edu.brown.cs32.MFTG.monopoly.Player.Expense;
import edu.brown.cs32.MFTG.monopoly.PlayerWealthData;
import edu.brown.cs32.MFTG.monopoly.PropertyData;
import edu.brown.cs32.MFTG.monopoly.TimeStamp;
import edu.brown.cs32.MFTG.tournament.Profile;
import edu.brown.cs32.MFTG.tournament.Settings;
import edu.brown.cs32.MFTG.tournament.Settings.Turns;
import edu.brown.cs32.MFTG.tournament.Settings.WinningCondition;
import edu.brown.cs32.MFTG.tournament.data.GameDataReport;
import edu.brown.cs32.MFTG.tournament.data.PlayerWealthDataReport;
import edu.brown.cs32.MFTG.tournament.data.PropertyDataAccumulator;
import edu.brown.cs32.MFTG.tournament.data.PropertyDataReport;
import edu.brown.cs32.MFTG.tournament.data.TimeStampReport;

public class ProtocolTests {
	PlayerWealthDataReport _playerWealthDataReport;
	TimeStampReport _timeStampReport;
	PropertyDataAccumulator _propertyDataAccumulator;
	
	@Before
	public void setUp(){
		_playerWealthDataReport = new PlayerWealthDataReport(1, 10, 100, 50);
		
		Map<Integer, PlayerWealthDataReport> wealthData = new HashMap<>();
		wealthData.put(1, _playerWealthDataReport);
		
		_timeStampReport = new TimeStampReport(1, wealthData);
		
		
		
	}
	
	class MyNullKeySerializer extends JsonSerializer<Object>
	{
	  @Override
	  public void serialize(Object nullKey, JsonGenerator jsonGenerator, SerializerProvider unused) 
	      throws IOException, JsonProcessingException
	  {
	    jsonGenerator.writeFieldName("");
	  }
	}	

	@Test
	public void testPlayer() throws IOException {
		ObjectMapper oMapper = new ObjectMapper();
		
//		oMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
//	    oMapper.setSerializationInclusion(Include.NON_NULL);
//	    oMapper.getSerializerProvider().setNullKeySerializer(new MyNullKeySerializer());
		
		Player p = new Player(1);
		Player p2 = new Player(1);
		
		// make sure that all necessary getters and setters exist
		HashMap<String, Integer> propertyValues = p.getPropertyValues();
		propertyValues.put(null, 76);
		p2.setPropertyValues(propertyValues);
		
		HashMap<String, Double[]> colorValues = p.getColorValues();
		p2.setColorValues(colorValues);
		
		double liquidity = p.getLiquidity();
		p2.setLiquidity(liquidity);
		
		double timeChange = p.getTimeChange();
		p2.setTimeChange(timeChange);
		
		int minBuyCash = p.getMinBuyCash();
		p2.setMinBuyCash(minBuyCash);
		
		int minBuildCash = p.getMinBuildCash();
		p2.setMinBuildCash(minBuildCash);
		
		int minUnmortgageCash = p.getMinUnmortgageCash();
		p2.setMinUnmortgageCash(minUnmortgageCash);
		
		int jailWait = p.getJailWait();
		p2.setJailWait(jailWait);
		
		Expense mortgageChoice = p.getMortgageChoice();
		p2.setMortgageChoice(mortgageChoice);
		
		Amount houseSelling = p.getHouseSelling();
		p2.setHouseSelling(houseSelling);
		
		Expense sellingChoice = p.getSellingChoice();
		p2.setSellingChoice(sellingChoice);
		
		Balance buildingEvenness = p.getBuildingEvenness();
		p2.setBuildingEvenness(buildingEvenness);
		
		Expense buildingChoice = p.getBuildingChoice();
		p2.setBuildingChoice(buildingChoice);
		
		Aggression buyAggression = p.getBuildAggression();
		p2.setBuildAggression(buyAggression);
		
		int jailPoor = p.getJailPoor();
		p2.setJailPoor(jailPoor);
		
		int jailRich = p.getJailRich();
		p2.setJailRich(jailRich);
		
		String playerJson = oMapper.writeValueAsString(p);
		Player newPlayer = oMapper.readValue(playerJson, Player.class);

		assertEquals(p, p2);
		assertEquals(p, newPlayer);
	}
	
	@Test
	public void testGameData() throws IOException{
		ObjectMapper oMapper = new ObjectMapper();
		GameData gd = new GameData(4);
		
		String gdJson = oMapper.writeValueAsString(gd);
		GameData newGD = oMapper.readValue(gdJson, GameData.class);
		
		assertEquals(gd, newGD);
	}
	
	@Test
	public void testGameDataReport() throws IOException {
		List<TimeStampReport> timeStamps = new ArrayList<>();
		Map<String, List<PropertyDataReport>> entireGameData = new HashMap<>();
		Map<String, PropertyDataReport> overallPropertyData = new HashMap<>();
		Map<Integer,Integer> playerWins = new HashMap<>();
		
		Map<Integer, PlayerWealthDataReport> wealthData = new HashMap<>();
		wealthData.put(1, new PlayerWealthDataReport(1, 2, 3, 4));
		
		TimeStampReport t = new TimeStampReport(100, wealthData);
		timeStamps.add(t);
		
		PropertyDataReport p = new PropertyDataReport("hi!", 1, 2, 3, 4, 5, 6, 7);
		entireGameData.put("test", Arrays.asList(p));
		
		overallPropertyData.put("test", p);
		
		playerWins.put(3, 4);
		playerWins.put(9, 12);
		List<Integer> winList = new ArrayList<>();
		winList.add(3);
		
		GameDataReport gdr = new GameDataReport(timeStamps, playerWins,winList, entireGameData, overallPropertyData);
		
		ObjectMapper oMapper = new ObjectMapper();
		
		String gdrJson = oMapper.writeValueAsString(gdr);
		GameDataReport newGDR = oMapper.readValue(gdrJson, GameDataReport.class);
		
		assertEquals(gdr, newGDR);
	}
	
	@Test
	public void testProfile() throws IOException {
		ObjectMapper oMapper = new ObjectMapper();
		
		Profile p = new Profile("test");
		
		String profileJson = oMapper.writeValueAsString(p);
		Profile newP = oMapper.readValue(profileJson, Profile.class);
		
		assertEquals(p, newP);
	}
	
	@Test
	public void testTimeStamp() throws IOException {
		ObjectMapper oMapper = new ObjectMapper();
		
		PropertyData p = new PropertyData("test", 0, 0, 0, 0, 0, 0, false);
		PlayerWealthData pwd = new PlayerWealthData(0, 0, 0);
		
		TimeStamp t = new TimeStamp(5);
		t.setPropertyData(Arrays.asList(p));
		t.setWealthData(Arrays.asList(pwd));
		t.setTime(10);
		
		String timeStampJson = oMapper.writeValueAsString(t);
		TimeStamp newT = oMapper.readValue(timeStampJson, TimeStamp.class);
		
		assertEquals(t, newT);
	}
	
	@Test
	public void testSettings() throws IOException {
		ObjectMapper oMapper = new ObjectMapper();
		
		Settings settings = new Settings(5, 10, false, 10, false, Turns.BUNCHED, WinningCondition.MOST_SETS_WON, 100, 100);
		String settingsJson = oMapper.writeValueAsString(settings);
		Settings newSettings = oMapper.readValue(settingsJson, Settings.class);
		
		assertEquals(settings, newSettings);
		
	}
}
