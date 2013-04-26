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
	private PlayerModule _module;
	
	public GameRunnerFactory(PlayerModule module, int maxNumTurns, int freeParking, boolean doubleOnGo, boolean auctions, Player...players){
		_module = module;
		_maxNumTurns = maxNumTurns;
		_freeParking = freeParking;
		_doubleOnGo = doubleOnGo;
		_auctions = auctions;
		_players = players;
	}
	
	public GameRunner build(long seed){
		return new GameRunner(new Game(seed,_maxNumTurns,_freeParking,_doubleOnGo,_auctions,_players));
	}
	
	/**
	 * GameRunners are games that update the data in the PlayerModule
	 * once they finish running
	 * @author frances
	 */
	private class GameRunner implements Runnable{
		private Game _game;
		
		public GameRunner(Game game){
			_game = game;
		}
		
		@Override
		public void run() {
			_game.run();
			_module.addGameData(_game.getGameData());
		}
	}
}