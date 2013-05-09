package edu.brown.cs32.MFTG.tournament;

import java.util.List;
import java.util.Random;

import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.tournament.data.GameDataReport;

public class CheatingClient extends HumanClient {

	public CheatingClient(boolean music) {
		super(music);
	}
	
	/**
	 * Play seeds.size games in separate threads
	 * @param players the player heuristics
	 * @param seeds the game seeds
	 * @param settings the game settings
	 * @return the data collected from the games
	 */
	public GameDataReport playGames(List<Player> players, List<Long> seeds, Settings settings){
		/* reset variables */
		Random rand = new Random();
		_numGamesPlayed = 0;
		_data = null;
		_nextDisplaySize = BackendConstants.DATA_PACKET_SIZE;
		_numThreadsDone.set(0);
		setPlayerNames(players);

		/* launch the games in separate threads */
		GameRunnerFactory gameRunnerFactory = new GameRunnerFactory(_numThreadsDone, this, BackendConstants.MAX_NUM_TURNS,
				settings.freeParking,settings.doubleOnGo,settings.auctions,players.toArray(new Player[players.size()]));

		for(int i = 0; i < seeds.size(); i++){
			_pool.execute(gameRunnerFactory.build(i,rand.nextLong()));
		}

		/* wait for the games to finish */
		synchronized (this){
			while(_numThreadsDone.get() < seeds.size()){
				try{
					this.wait();
				} catch (InterruptedException e){}
			}
		}

		return _data.toGameDataReport();
	}
}
