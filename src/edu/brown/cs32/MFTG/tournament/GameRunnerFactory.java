package edu.brown.cs32.MFTG.tournament;

import edu.brown.cs32.MFTG.monopoly.Game;

/**
 * GameRunnerFactory creates GameRunners from the template game
 * @author frances
 */
public class GameRunnerFactory {
	private Game _gameTemplate;
	private PlayerModule _module;
	
	public GameRunnerFactory(Game gameTemplate, PlayerModule module){
		_gameTemplate = gameTemplate;
		_module = module;
	}
	
	public GameRunner build(){
		return new GameRunner(_gameTemplate.copy());
		//TODO avoid resource conflicts
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