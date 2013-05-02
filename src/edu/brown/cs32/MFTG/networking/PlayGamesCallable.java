package edu.brown.cs32.MFTG.networking;

import java.util.List;
import java.util.concurrent.Callable;

import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.tournament.Settings;
import edu.brown.cs32.MFTG.tournament.data.GameDataReport;

public class PlayGamesCallable implements Callable<GameDataReport>{
	
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
	
	public GameDataReport call() throws Exception {
		GameDataReport gameData =  _clientHandler.playGames(_players, _seeds, _settings);
		return gameData;
	}

}
