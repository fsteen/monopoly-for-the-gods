package edu.brown.cs32.MFTG.tournament;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.ClientRequestContainer;
import edu.brown.cs32.MFTG.tournament.data.GameDataReport;
import edu.brown.cs32.MFTG.tournament.data.PropertyDataReport;
import edu.brown.cs32.MFTG.tournament.data.TimeStampReport;

public class AIPlayerModule extends PlayerModule{
	// data from previous set
	GameDataReport _currentGameData;
	GameDataReport _previousGameData;
	
	// player from previous set
	Player _player;
	Player _previousPlayer;

	public AIPlayerModule(String host, int port) {
		super(host, port);
		_player = new Player(_id);
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
		_previousGameData=_currentGameData;
		_currentGameData = gameDataReport;
		
		displayGameData(gameDataReport);
	}
	
	
	
	@Override
	public Player getPlayer(){
		Player temp = new Player(_player);
		for(String key: _currentGameData._overallPlayerPropertyData.keySet()) {
			PropertyDataReport mine =   findMyReport(_currentGameData._overallPlayerPropertyData.get(key));
			PropertyDataReport minePrev =   findMyReport(_previousGameData._overallPlayerPropertyData.get(key));
			//If i'm doing better than previously, then I can value it more or less according to what i've previously done
			if(mine.accTotalRevenueWithoutHouses+mine.accTotalRevenueWithHouses>minePrev.accTotalRevenueWithHouses+minePrev.accTotalRevenueWithoutHouses) {
				int valDif = _player.getPropertyValue(key)-_previousPlayer.getPropertyValue(key);
				if(valDif>0) {
					_player.setPropertyValue(key, (int) (_player.getPropertyValue(key)+valDif*.9));
				}
				else if(valDif<0){
					_player.setPropertyValue(key, (int) (_player.getPropertyValue(key)-valDif*.9));
				}
			}
			else {
				if(mine.accTotalRevenueWithoutHouses+mine.accTotalRevenueWithHouses>minePrev.accTotalRevenueWithHouses+minePrev.accTotalRevenueWithoutHouses) {
					int valDif = _player.getPropertyValue(key)-_previousPlayer.getPropertyValue(key);
					if(valDif>0) {
						_player.setPropertyValue(key, (int) (_player.getPropertyValue(key)-valDif*.9));
					}
					else if(valDif<0){
						_player.setPropertyValue(key, (int) (_player.getPropertyValue(key)+valDif*.9));
					}
				}
			}
			
			if(mine.accTotalRevenueWithHouses>minePrev.accTotalRevenueWithHouses) {
				double monopolyDif = _player.getMonopolyValue(key)-_previousPlayer.getMonopolyValue(key);
				if(monopolyDif>0) {
					_player.setMonopolyValue(key, _player.getMonopolyValue(key)+monopolyDif*.9);
				}
				else if(monopolyDif<0){
					_player.setMonopolyValue(key, _player.getMonopolyValue(key)-monopolyDif*.9);
				}
				
				double houseDif = _player.getHouseValueOfColor(key)-_previousPlayer.getHouseValueOfColor(key);
				if(houseDif>0) {
					_player.setHouseValueOfColor(key, _player.getHouseValueOfColor(key)+houseDif*.9);
				}
				else if(houseDif<0){
					_player.setHouseValueOfColor(key, _player.getHouseValueOfColor(key)-houseDif*.9);
				}
			}

		}
		for(TimeStampReport t : _currentGameData._timeStamps){
			
		}
		_previousPlayer=temp;
		return _player;
	}
	
	private PropertyDataReport findMyReport(List<PropertyDataReport> reports) {
		for(PropertyDataReport r: reports) {
			if(r.ownerID==_id) {
				return r;
			}
		}
		return null;
	}

}
