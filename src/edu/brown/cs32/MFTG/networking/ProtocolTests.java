package edu.brown.cs32.MFTG.networking;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.monopoly.Player.Amount;
import edu.brown.cs32.MFTG.monopoly.Player.Balance;
import edu.brown.cs32.MFTG.monopoly.Player.Expense;

public class ProtocolTests {

	@Test
	public void testPlayer() throws IOException {
		ObjectMapper oMapper = new ObjectMapper();
		Player p = new Player(1);
		Player p2 = new Player(1);
		
		// make sure that all necessary getters and setters exist
		HashMap<String, Integer> propertyValues = p.getPropertyValues();
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
		
//		Aggression buyAggression = p.getBuyAggression();
//		p2.setBuyAggression(buyAggression);
		
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

}
