package edu.brown.cs32.MFTG.networking;

import java.util.List;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.tournament.Settings;

public interface iClientHandler {
	
	public Player getPlayer();
	
	public void playGames(List<Player> players, Settings settings, int numGames);
	
	public List<GameData> getGameData();

	public void sendGameData(List<GameData> combinedData);
}
