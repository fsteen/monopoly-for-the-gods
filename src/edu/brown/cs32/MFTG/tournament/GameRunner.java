package edu.brown.cs32.MFTG.tournament;

import java.util.concurrent.atomic.AtomicInteger;

import edu.brown.cs32.MFTG.monopoly.Game;

/**
 * GameRunners are games that update the data in the Client
 * once they finish running
 * @author frances
 */
class GameRunner implements Runnable{
	private Game _game;
	private AtomicInteger _numThreadsDone;
	private Client _module;
	
	public GameRunner(Game game, AtomicInteger numThreadsDone, Client module){
		_game = game;
		_numThreadsDone = numThreadsDone;
		_module = module;
	}
	
	@Override
	public void run() {
		_game.run();
		_module.addGameData(_game.getGameData());
				
		synchronized(_module){
			_numThreadsDone.incrementAndGet();
			_module.notifyAll();
		}
	}
}