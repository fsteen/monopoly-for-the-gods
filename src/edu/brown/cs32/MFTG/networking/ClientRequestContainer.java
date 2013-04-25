package edu.brown.cs32.MFTG.networking;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.tournament.Settings;

public class ClientRequestContainer {
	public enum Method {GETPLAYER, PLAYGAMES, GETGAMEDATA, SENDGAMEDATA};
	
	public final Method _method;
	public final Collection<Player> _players;
	public final int _numGames;
	public final List<GameData> _gameData;
	
	@JsonCreator
	public ClientRequestContainer(@JsonProperty("method") Method method, 
								  @JsonProperty("players") Collection<Player> players, 
								  @JsonProperty("numGames") int numGames,
								  @JsonProperty("gameData") List<GameData> gameData ){
		_method = method;
		_players = players;
		_numGames = numGames;
		_gameData = gameData;
	}
	
}
