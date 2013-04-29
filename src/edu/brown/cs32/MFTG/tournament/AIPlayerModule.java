package edu.brown.cs32.MFTG.tournament;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.ClientRequestContainer;
import edu.brown.cs32.MFTG.tournament.data.GameDataReport;

public class AIPlayerModule extends PlayerModule{
	// data from previous set
	GameDataReport _lastGameData;
	
	// player from previous set
	Player _lastPlayer;

	public AIPlayerModule(String host, int port) {
		super(host, port);
	}
	
	/**
	 * Responds to a request sent over the server to display game data by displaying the data received
	 * @param the request which is being responded to
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Override
	protected void respondToDisplayData(ClientRequestContainer request) throws JsonParseException, JsonMappingException, IOException{
		List<String> arguments = request._arguments;
		
		if (arguments == null){
			// error
		} else if (arguments.size() < 1){
			// error
		}
		
		GameDataReport gameDataReport = _oMapper.readValue(arguments.get(0), GameDataReport.class);
		_lastGameData = gameDataReport;
		
		displayGameData(gameDataReport);
	}
	
	
	
	@Override
	public Player getPlayer(){
		// TODO write dat shit
		
		Player p = null;
		_lastPlayer = p;
		return p;
	}

}
