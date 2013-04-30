package edu.brown.cs32.MFTG.networking;

import java.util.List;
import java.util.concurrent.Callable;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;

public class PlayGamesCallable implements Callable<List<GameData>>{
	
	private final ClientHandler _clientHandler;
	private final List<Player> _players;
	private final List<Long> _seeds;

	public PlayGamesCallable(ClientHandler clientHandler, List<Player> players, List<Long> seeds){
		_clientHandler = clientHandler;
		_players = players;
		_seeds = seeds;
	}
	
	public List<GameData> call() throws Exception {
		return _clientHandler.playGames(_players, _seeds);
	}

}
