package edu.brown.cs32.MFTG.tournament;

import java.util.List;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.iClientHandler;
import edu.brown.cs32.MFTG.tournament.data.GameDataReport;

public class DummyClient implements iClientHandler{
	
	PlayerModule _module;

	public DummyClient(PlayerModule module){
		_module = module;
	}
	
	@Override
	public Player getPlayer() {
		return _module.getPlayer();
	}

	@Override
	public List<GameData> playGames(List<Player> players, List<Long> seeds) {
		return _module.playGames(players, seeds);
	}

	@Override
	public void setGameData(GameDataReport aggregatedData) {
		_module.displayGameData(aggregatedData);
	}

//	@Override
//	public void setGameData(GameData aggregatedData) {
//		_module.setGameData(aggregatedData);
//	}
}
