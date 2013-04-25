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
	
	DummyGUI _gui;
	private static final int NUM_THREADS=10;
	private static final int DATA_PACKET_SIZE=10;
	private int _nextDisplaySize;
	private List<GameData> _data;
	
	public PlayerModule(){
		_data = new ArrayList<>();
		_gui = new DummyGUI();
	}
	
	/**
	 * Gets the player associated with this object
	 * @return
	 */
	public Player getPlayer(){
		//TODO calls method in the GUI to get player
		return _gui.getPlayer();
	}
	

	public List<GameData> playGames(List<Player> players, Settings settings, List<Long> seeds){
		_data.clear();
		_nextDisplaySize = DATA_PACKET_SIZE;
		
		//construct a game from the settings
		GameRunnerFactory gameRunnerFactory = new GameRunnerFactory(this, 1000,-1,false,false,(Player[])players.toArray());
		
		ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
		for(int i = 0; i < seeds.size(); i++){ //execute games and record data
			pool.execute(gameRunnerFactory.build(seeds.get(i)));
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