package edu.brown.cs32.MFTG.tournament;

import java.util.concurrent.atomic.AtomicInteger;

import edu.brown.cs32.MFTG.monopoly.Game;

public class DummyGameRunner extends GameRunner{
	private AtomicInteger _numThreadsDone;
	private Client _module;
	
	public DummyGameRunner(Game game, AtomicInteger numThreadsDone, Client module) {
		super(game, numThreadsDone, module);
		_numThreadsDone = numThreadsDone;
		_module = module;
	}
	
	@Override
	public void run() {
		System.out.println("running");
		synchronized(_module){
			System.out.println("runnable # " + _numThreadsDone.incrementAndGet());
			_module.notifyAll();
		}
	}
}