package edu.brown.cs32.MFTG.tournament;

import java.util.concurrent.atomic.AtomicInteger;

import edu.brown.cs32.MFTG.monopoly.Game;
import edu.brown.cs32.MFTG.monopoly.Player;

/**
 * GameRunnerFactory creates GameRunners from the template game
 * @author frances
 */
public class GameRunnerFactory {
	private int _maxNumTurns, _freeParking;
	private boolean _doubleOnGo, _auctions;
	private Player[] _players;
	private Client _module;
	private AtomicInteger _numThreadsDone;
	
	public GameRunnerFactory(AtomicInteger numThreadsDone, Client module,
			int maxNumTurns, int freeParking, boolean doubleOnGo, boolean auctions, Player...players){
		_numThreadsDone = numThreadsDone;
		_module = module;
		_maxNumTurns = maxNumTurns;
		_freeParking = freeParking;
		_doubleOnGo = doubleOnGo;
		_auctions = auctions;
		_players = players;
	}
	
	public GameRunner build(long seed){
		return new GameRunner(
				new Game(seed,_maxNumTurns,_freeParking,_doubleOnGo,_auctions,_players),
				_numThreadsDone,
				_module);
		
//		//for testing
//		return new DummyGameRunner(null, _threadsDone, _module);
	}
}