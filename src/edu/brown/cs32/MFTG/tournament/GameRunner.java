package edu.brown.cs32.MFTG.tournament;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import edu.brown.cs32.MFTG.monopoly.Game;
import edu.brown.cs32.MFTG.monopoly.GameData;

/**
 * GameRunners are games that update the data in the Client
 * once they finish running
 * @author frances
 */
class GameRunner implements Callable<GameData>{
	private Game _game;
	
	public GameRunner(Game game){
		_game = game;
	}
	
	@Override
	public GameData call() {
		_game.run();
		return _game.getGameData();
	}
}