package edu.brown.cs32.MFTG.networking;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.tournament.Settings;

public class ClientRequestContainer {
	public enum Method {GETPLAYER, SENDPLAYER, PLAYGAMES, GETGAMEDATA, SENDGAMEDATA};
	
	public final Method _method;
	public final List<String> _arguments;
	
	@JsonCreator
	public ClientRequestContainer(@JsonProperty("method") Method method, @JsonProperty("arguments") List<String> arguments){
		_method = method;
		_arguments = arguments;
	}
}

/*
Collection<Player> players, 
int numGames,
List<GameData> gameData )
*/