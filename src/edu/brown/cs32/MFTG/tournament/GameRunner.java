package edu.brown.cs32.MFTG.tournament;

import java.util.concurrent.atomic.AtomicInteger;

import edu.brown.cs32.MFTG.monopoly.Game;

/**
 * GameRunners are games that update the data in the PlayerModule
 * once they finish running
 * @author frances
 */
class GameRunner implements Runnable{
	private Game _game;
	private AtomicInteger _threadsDone;
	private PlayerModule _module;
	
	public GameRunner(Game game, AtomicInteger threadsDone, PlayerModule module){
		_game = game;
		_threadsDone = threadsDone;
		_module = module;
	}
	
	@Override
	public void run() {
		_game.run();
		_module.addGameData(_game.getGameData());
				
		synchronized(_module){
			_threadsDone.incrementAndGet();
			_module.notifyAll();
		}
	}
}