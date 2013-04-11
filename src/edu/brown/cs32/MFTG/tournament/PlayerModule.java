package edu.brown.cs32.MFTG.tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.brown.cs32.MFTG.monopoly.Game;
import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.iClientHandler;

public class PlayerModule implements iClientHandler{
	
//	GUI gui;
	private static final int NUM_THREADS=10;
	private static final int DATA_PACKET_SIZE=10;
	private List<GameData> _data;
	
	public PlayerModule(){
		_data = new ArrayList<>();
	}
	
	/**
	 * Gets the player associated with this object
	 * @return
	 */
	@Override
	public Player getPlayer(){
		//TODO calls method in the GUI to get player
		return null;
	}
	
	/**
	 * Sends an encoding of the players to the client and a request to play numGame games
	 * @param players
	 * @param numGames
	 */
	@Override
	public void playGames(List<Player> players, Settings settings, int numGames){
		//question: can i run a game over and over again, or must i create a new one each time?
		
		Game game = new Game(-1,false, false, (Player[])players.toArray()); //TODO must change later!
		ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
		
		for(int i = 0; i < numGames; i++){ //execute games and record data
			pool.execute(game);
			_data.add(game.getGameData());
		}
		
	}
	
	@Override
	public List<GameData> getGameData(){
		//TODO
		return null;
	}

	@Override
	public void setGameData(List<GameData> combinedData) {
		// TODO Auto-generated method stub
		
	}

}
