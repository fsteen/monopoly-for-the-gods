package edu.brown.cs32.MFTG.tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.tournament.data.PlayerWealthDataReport;
import edu.brown.cs32.MFTG.tournament.data.PropertyDataReport;

public class DummyGUI {

	public Player getPlayer(){
		return new Player(0);
	}
	
	public void setWealthData(List<PlayerWealthDataReport> data){
		System.out.println(data);
	}

	public void setPropertyData(Map<String, PropertyDataReport> data){ //this is the cumulative property data
		System.out.println(new ArrayList<PropertyDataReport>(data.values()));
	}

	public void setPlayerSpecificPropertyData(Map<String, PropertyDataReport> data){
		System.out.println(new ArrayList<PropertyDataReport>(data.values()));
	}
	
	public void setRoundWinner(int playerID){
		System.out.println("player " + playerID + " is the round winner!");
	}
	
	public void printSpaces(){
		System.out.println("\n\n\n");
	}

	public void roundCompleted(){
		System.out.println("round over");
	}
}