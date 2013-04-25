package edu.brown.cs32.MFTG.tournament;

import java.util.List;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.iClientHandler;

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
	public List<GameData> playGames(List<Player> players, Settings settings,
			List<Long> seeds) {
		return _module.playGames(players, settings, seeds);
	}

	@Override
	public void setGameData(List<GameData> combinedData) {
		_module.setGameData(combinedData);
	}
}
