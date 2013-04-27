package edu.brown.cs32.MFTG.tournament;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;

public class DummyGUI {

	public Player getPlayer(){
		return new Player(0);
	}
	
	public void displayGameData(GameData data){
		System.out.println(data);
	}
}
