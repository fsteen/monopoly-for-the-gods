package edu.brown.cs32.MFTG.networking;

import java.util.List;
import java.util.concurrent.Callable;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.tournament.Settings;

public class PlayGamesCallable implements Callable<List<GameData>>{
	
	private final ClientHandler _clientHandler;
	private final List<Player> _players;
	private final List<Long> _seeds;
	private final Settings _settings;

	public PlayGamesCallable(ClientHandler clientHandler, List<Player> players, List<Long> seeds, Settings settings){
		_clientHandler = clientHandler;
		_players = players;
		_seeds = seeds;
		_settings = settings;
	}
	
	public List<GameData> call() throws Exception {
		List<GameData> gameData =  _clientHandler.playGames(_players, _seeds, _settings);
		return gameData;
	}

}
