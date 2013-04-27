package edu.brown.cs32.MFTG.tournament;

import java.util.concurrent.atomic.AtomicInteger;

import edu.brown.cs32.MFTG.monopoly.Game;

public class DummyGameRunner extends GameRunner{
	private AtomicInteger _threadsDone;
	private PlayerModule _module;
	
	public DummyGameRunner(Game game, AtomicInteger threadsDone, PlayerModule module) {
		super(game, threadsDone, module);
		_threadsDone = threadsDone;
		_module = module;
	}
	
	@Override
	public void run() {
		System.out.println("running");
		synchronized(_module){
			System.out.println("runnable # " + _threadsDone.incrementAndGet());
			_module.notifyAll();
		}
	}
}