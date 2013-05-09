package edu.brown.cs32.MFTG.tournament;

import java.util.concurrent.atomic.AtomicInteger;

import edu.brown.cs32.MFTG.monopoly.Game;
import edu.brown.cs32.MFTG.monopoly.GameData;

/**
 * GameRunners are games that update the data in the Client
 * once they finish running
 * @author frances
 */
class GameRunner implements Runnable{
	private Game _game;
	private AtomicInteger _numThreadsDone;
	private Client _client;

	public GameRunner(Game game, AtomicInteger numThreadsDone, Client client){
		_game = game;
		_numThreadsDone = numThreadsDone;
		_client = client;
	}

	@Override
	public void run() {
		try{
			_game.run();
			_client.addGameData(_game.getGameData());
		} catch (Exception e){
			GameData g = new GameData(_game.gameNum, BackendConstants.MAX_NUM_PLAYERS);
			g.setWinner(-1); //tie
			_client.addGameData(g);
		}
		synchronized(_client){
			_numThreadsDone.incrementAndGet();
			_client.notifyAll();
		}
	}
}