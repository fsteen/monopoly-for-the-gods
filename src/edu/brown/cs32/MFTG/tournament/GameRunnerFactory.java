package edu.brown.cs32.MFTG.tournament;

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
	
	public GameRunnerFactory(int maxNumTurns, int freeParking, boolean doubleOnGo, boolean auctions, Player...players){
		_maxNumTurns = maxNumTurns;
		_freeParking = freeParking;
		_doubleOnGo = doubleOnGo;
		_auctions = auctions;
		_players = players;
	}
	
	public GameRunner build(int gameNum, long seed){
		return new GameRunner(
				new Game(gameNum,seed,_maxNumTurns,_freeParking,_doubleOnGo,_auctions,_players));
	}
}