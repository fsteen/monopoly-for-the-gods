package edu.brown.cs32.MFTG.tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.brown.cs32.MFTG.monopoly.Game;
import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.iClientHandler;

public class PlayerModule {
	
//	GUI gui;
	private static final int NUM_THREADS=10;
	private static final int DATA_PACKET_SIZE=10;
	private int _nextDisplaySize;
	private List<GameData> _data;
	
	public PlayerModule(){
		_data = new ArrayList<>();
	}
	
	/**
	 * Gets the player associated with this object
	 * @return
	 */
	public Player getPlayer(){
		//TODO calls method in the GUI to get player
		return null;
	}
	
	/**
	 * Sends an encoding of the players to the client and a request to play numGame games
	 * @param players
	 * @param numGames
	 */
	public List<GameData> playGames(List<Player> players, Settings settings, int numGames){
		_data.clear();
		_nextDisplaySize = DATA_PACKET_SIZE;
		
		//construct a game from the settings
		Game game = new Game(-1,false, false, (Player[])players.toArray()); //TODO must change later!
		GameRunnerFactory gameRunnerFactory = new GameRunnerFactory(game,this);
		
		ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
		for(int i = 0; i < numGames; i++){ //execute games and record data
			pool.execute(gameRunnerFactory.build());
		}
		
		return _data;
	}
	
	public synchronized void addGameData(GameData gameData){
		_data.add(gameData);
		if(_data.size() >= _nextDisplaySize){
			//display some data
			_nextDisplaySize += DATA_PACKET_SIZE; //set next point at which to display
		}
	}
	
	public List<GameData> getGameData(){
		//TODO
		return null;
	}

	public void setGameData(List<GameData> combinedData) {
		// TODO Auto-generated method stub
		
	}
}