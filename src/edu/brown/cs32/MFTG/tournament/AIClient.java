package edu.brown.cs32.MFTG.tournament;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.monopoly.Player.Aggression;
import edu.brown.cs32.MFTG.monopoly.Player.Amount;
import edu.brown.cs32.MFTG.monopoly.Player.Balance;
import edu.brown.cs32.MFTG.networking.ClientRequestContainer;
import edu.brown.cs32.MFTG.networking.InvalidRequestException;
import edu.brown.cs32.MFTG.tournament.data.DataProcessor;
import edu.brown.cs32.MFTG.tournament.data.GameDataAccumulator;
import edu.brown.cs32.MFTG.tournament.data.GameDataReport;
import edu.brown.cs32.MFTG.tournament.data.PlayerWealthDataReport;
import edu.brown.cs32.MFTG.tournament.data.PropertyDataReport;
import edu.brown.cs32.MFTG.tournament.data.TimeStampReport;

public class AIClient extends Client{
	// data from previous set
	private GameDataReport _currentGameData;
	private GameDataReport _previousGameData;
	private HashMap<String,String> _colorKeys;

	// player from previous set
	private Player _player;
	private Player _previousPlayer;

	public AIClient() {
		super();
//		_player = new Player(_id,"Computer");
		_previousPlayer=null;
		_colorKeys = new HashMap<>(30);
		initializeColorKeys();
	}
	
	private void initializeColorKeys() {
		_colorKeys.put("mediterranean avenue", "purple");
		_colorKeys.put("baltic avenue", "purple");
		
		_colorKeys.put("oriental avenue", "light blue");
		_colorKeys.put("vermont avenue", "light blue");
		_colorKeys.put("connecticut avenue", "light blue");
		
		_colorKeys.put("st. charles place", "pink");
		_colorKeys.put("states avenue", "pink");
		_colorKeys.put("virginia avenue", "pink");
		
		_colorKeys.put("st. james place", "orange");
		_colorKeys.put("tennessee avenue", "orange");
		_colorKeys.put("new york avenue", "orange");
		
		_colorKeys.put("kentucky avenue", "red");
		_colorKeys.put("indiana avenue", "red");
		_colorKeys.put("illinois avenue", "red");
		
		_colorKeys.put("atlantic avenue", "yellow");
		_colorKeys.put("ventnor avenue", "yellow");
		_colorKeys.put("marvin gardens", "yellow");
		
		_colorKeys.put("pacific avenue", "green");
		_colorKeys.put("north carolina avenue", "green");
		_colorKeys.put("pennsylvania avenue", "green");
		
		_colorKeys.put("park place", "dark blue");
		_colorKeys.put("boardwalk", "dark blue");
		
		_colorKeys.put("reading railroad", "black");
		_colorKeys.put("pennsylvania railroad", "black");
		_colorKeys.put("b and o railroad", "black");
		_colorKeys.put("short line", "black");
		_colorKeys.put("electric company", "black");
		_colorKeys.put("water works", "black");
		
	}

	
	public void run() {
		try {
			_server = new Socket(_host,_port);
			_input = new BufferedReader(new InputStreamReader(_server.getInputStream()));
			_output = new BufferedWriter(new OutputStreamWriter(_server.getOutputStream()));
		} catch (UnknownHostException e) {
			return;
		} catch (IOException e) {
			return;
		}
		try {
			respondToSendConstants();
		} catch (IOException | InvalidRequestException e1) {
			System.err.println("An error has occured. AI will now exit");
			sayGoodbye(true);
			return;
		}
		_running = true;
		while(_running){
			try {
				handleRequest();
			} catch (IOException | InvalidRequestException e){
				System.err.println("An error has occured. AI will now exit");
				sayGoodbye(false);
				return;
			} catch (Exception e){
				e.printStackTrace();
				System.err.println("An error has occured. AI will now exit");
				sayGoodbye(false);
				return;
			}
		}
	}

	/**
	 * Responds to a request sent over the server to display game data by displaying the data received
	 * @param the request which is being responded to
	 * @throws IOException
	 * @throws InvalidRequestException 
	 */
	protected void respondToDisplayData(ClientRequestContainer request) throws IOException, InvalidRequestException{
		if (request == null){
			throw new InvalidRequestException("Null request");
		}
		
		List<String> arguments = request._arguments;

		if (arguments == null || arguments.size() < 1){
			throw new InvalidRequestException("Wrong number of arguments");
		}

		GameDataReport gameDataReport = _oMapper.readValue(arguments.get(0), GameDataReport.class);
		_previousGameData=_currentGameData;
		_currentGameData = gameDataReport;
		
		if (gameDataReport._matchIsOver)
			_running = false;
	}
	
	protected void respondToGameClosed(){
		_running = false;
	}
	
	public synchronized void addGameData(GameData gameData){
		List<GameData> temp = new ArrayList<>();
		temp.add(gameData);
		GameDataAccumulator a = DataProcessor.aggregate(temp,BackendConstants.NUM_DATA_POINTS);
		if(_data == null){
			_data = a;
		} else {
			DataProcessor.combineAccumulators(_data, DataProcessor.aggregate(temp,BackendConstants.NUM_DATA_POINTS));
		}
	}

	public void startGetPlayer(int time){
		finishRespondToGetPlayer();
	}

	public Player finishGetPlayer(){
		if(_player == null){
			_player = new Player(_id,"Computer");
		}
		Player temp = new Player(_player);
		if(_currentGameData==null) {
			

			_player.setColorValue("purple", 2.5, 75, 2.25, 2);
			_player.setColorValue("light blue", 2.5, 75, 2.25, 2);
			_player.setColorValue("pink", 2.5, 150, 2.25, 2);
			_player.setColorValue("orange", 2.5, 150,2.25, 2);
			_player.setColorValue("red", 2.5, 225, 2.25, 2);
			_player.setColorValue("yellow", 2.5, 225, 2.25, 2);
			_player.setColorValue("green", 2.5, 300, 2.25, 2);
			_player.setColorValue("dark blue", 2.5, 300, 2.25, 2);

			_player.setJailWait(1);
			_player.setJailRich(1);
			_player.setJailPoor(1);
			_player.setMinBuildCash(0);
			_player.setMinBuyCash(0);
			_player.setMinUnmortgageCash(0);
			_player.setTradingFear(1.1);
			_player.setLiquidity(10);
			_player.setBuildAggression(Aggression.AGGRESSIVE);
			_player.setBuildingEvenness(Balance.UNEVEN);
			_player.setHouseSelling(Amount.FEWER);
			
			_player.setPropertyValue("mediterranean avenue", 70);
			_player.setPropertyValue("baltic avenue", 70);
			_player.setPropertyValue("reading railroad", 210);
			_player.setPropertyValue("oriental avenue", 110);
			_player.setPropertyValue("vermont avenue", 110);
			_player.setPropertyValue("connecticut avenue", 130);
			_player.setPropertyValue("st. charles place", 150);
			_player.setPropertyValue("electric company", 160);
			_player.setPropertyValue("states avenue", 150);
			_player.setPropertyValue("virginia avenue", 170);
			_player.setPropertyValue("pennsylvania railroad", 210);
			_player.setPropertyValue("st. james place", 190);
			_player.setPropertyValue("tennessee avenue", 190);	
			_player.setPropertyValue("new york avenue", 210);	
			_player.setPropertyValue("kentucky avenue", 230);	
			_player.setPropertyValue("indiana avenue", 230);	
			_player.setPropertyValue("illinois avenue", 250);		
			_player.setPropertyValue("b and o railroad",210);		
			_player.setPropertyValue("atlantic avenue", 270);		
			_player.setPropertyValue("ventnor avenue", 270);		
			_player.setPropertyValue("water works",160);		
			_player.setPropertyValue("marvin gardens", 290);	
			_player.setPropertyValue("pacific avenue", 310);
			_player.setPropertyValue("north carolina avenue", 310);
			_player.setPropertyValue("pennsylvania avenue", 330);
			_player.setPropertyValue("short line", 210);
			_player.setPropertyValue("park place", 360);
			_player.setPropertyValue("boardwalk", 410);
			
			
			

		}
		else if(_previousGameData==null) {	
			
			_player.setPropertyValue("baltic avenue", 90);
			_player.setPropertyValue("oriental avenue", 130);
			_player.setPropertyValue("vermont avenue", 130);
			_player.setPropertyValue("connecticut avenue", 140);
			_player.setPropertyValue("st. james place", 200);
			_player.setPropertyValue("tennessee avenue", 200);	
			_player.setPropertyValue("new york avenue", 220);	
			_player.setPropertyValue("park place", 360);
			_player.setPropertyValue("boardwalk", 430);


		}
		else {
			for(String key: _currentGameData._overallPlayerPropertyData.keySet()) {
				PropertyDataReport others =  _currentGameData._overallPropertyData.get(key);
				PropertyDataReport mine =   findMyReport(_currentGameData._overallPlayerPropertyData.get(key));
				PropertyDataReport minePrev =   findMyReport(_previousGameData._overallPlayerPropertyData.get(key));
				String color = _colorKeys.get(key);
				//If i'm doing better than previously, then I can value it more or less according to what i've previously done
				if(mine.accTotalRevenueWithoutHouses+mine.accTotalRevenueWithHouses>minePrev.accTotalRevenueWithHouses+minePrev.accTotalRevenueWithoutHouses) {
					int valDif = _player.getPropertyValue(key)-_previousPlayer.getPropertyValue(key);
					_player.setPropertyValue(key, (int) (_player.getPropertyValue(key)+valDif*.7));

				}
				else {
					int valDif = _player.getPropertyValue(key)-_previousPlayer.getPropertyValue(key);
					_player.setPropertyValue(key, (int) (_player.getPropertyValue(key)-valDif*.7));
				}
				if(color.equals("black")) {
					continue;
				}
				if(mine.accTotalRevenueWithHouses>minePrev.accTotalRevenueWithHouses) {
					double monopolyDif = _player.getMonopolyValue(color)-_previousPlayer.getMonopolyValue(color);
					_player.setMonopolyValue(color, _player.getMonopolyValue(color)+monopolyDif*.7);

					double houseDif = _player.getHouseValueOfColor(color)-_previousPlayer.getHouseValueOfColor(color);
					_player.setHouseValueOfColor(color, _player.getHouseValueOfColor(color)+houseDif*.7);


					double sameDif = _player.getSameColorEffect(color)-_previousPlayer.getSameColorEffect(color);
					_player.setSameColorEffect(color, _player.getSameColorEffect(color)+sameDif*.7);


				}
				else {
					double monopolyDif = _player.getMonopolyValue(color)-_previousPlayer.getMonopolyValue(color);
					_player.setMonopolyValue(color, _player.getMonopolyValue(color)-monopolyDif*.7);

					double houseDif = _player.getHouseValueOfColor(color)-_previousPlayer.getHouseValueOfColor(color);
					_player.setHouseValueOfColor(color, _player.getHouseValueOfColor(color)+houseDif*.7);


					double sameDif = _player.getSameColorEffect(color)-_previousPlayer.getSameColorEffect(color);
					_player.setSameColorEffect(color, _player.getSameColorEffect(color)+sameDif*.7);
				}

			}
			double mycash=0;
			double mywealth=0;
			double prevcash=0;
			double prevwealth=0;
			for(TimeStampReport t : _currentGameData._timeStamps){
				PlayerWealthDataReport r=t.wealthData.get(t.wealthData.size()-1);
				if(r.ownerID==_id) {
					mycash=r.accCash;
					mywealth=r.accTotalWealth;
				}
			}
			for(TimeStampReport t : _previousGameData._timeStamps){
				PlayerWealthDataReport r=t.wealthData.get(t.wealthData.size()-1);
				if(r.ownerID==_id) {
					prevcash=r.accCash;
					prevwealth=r.accTotalWealth;
				}
			}

			if(_previousPlayer==null) {
				_player.setLiquidity(Math.min(_player.getLiquidity()+1,10));
				_player.setTimeChange(Math.min(_player.getTimeChange()+1,10));
				_player.setTradingFear(Math.min(_player.getTradingFear()+1,10));
			}
			//if I did poorly
			if(prevwealth>=mywealth) {
				//I did really poorly and lost in cash and wealth
				if(prevcash>=mycash) {
					_player.setLiquidity(Math.min(Math.max(1,_previousPlayer.getLiquidity()-.9*(_previousPlayer.getLiquidity()-_player.getLiquidity())),10));
					_player.setTimeChange(Math.min(Math.max(1,_previousPlayer.getTimeChange()-.9*(_previousPlayer.getTimeChange()-_player.getTimeChange())),10));
					_player.setMinBuyCash((int) Math.max(0,_previousPlayer.getMinBuyCash()-.9*(_previousPlayer.getMinBuyCash()-_player.getMinBuyCash())));
					_player.setMinUnmortgageCash((int) Math.max(0,_previousPlayer.getMinUnmortgageCash()-.9*(_previousPlayer.getMinUnmortgageCash()-_player.getMinUnmortgageCash())));
					_player.setMinBuildCash((int) Math.max(0,_previousPlayer.getMinBuildCash()-.9*(_previousPlayer.getMinBuildCash()-_player.getMinBuildCash())));
					_player.setTradingFear(Math.min(Math.max(1,_previousPlayer.getTradingFear()-.9*(_previousPlayer.getTradingFear()-_player.getTradingFear())),10));
				}
				//I hoarded cash way too much
				else {
					_player.setMinBuildCash(Math.max(0, _previousPlayer.getMinBuildCash()-50));
					_player.setMinBuyCash(Math.max(0,_previousPlayer.getMinBuyCash()-50));
					_player.setMinUnmortgageCash(Math.max(0,_previousPlayer.getMinUnmortgageCash()-50));
				}
			}
			//if I did well
			else {
				_player.setLiquidity(Math.min(Math.max(1,_previousPlayer.getLiquidity()+.9*(_previousPlayer.getLiquidity()-_player.getLiquidity())),10));
				_player.setTimeChange(Math.min(Math.max(1,_previousPlayer.getTimeChange()+.9*(_previousPlayer.getTimeChange()-_player.getTimeChange())),10));
				_player.setMinBuyCash((int) Math.max(0,_previousPlayer.getMinBuyCash()+.9*(_previousPlayer.getMinBuyCash()-_player.getMinBuyCash())));
				_player.setMinUnmortgageCash((int) Math.max(0,_previousPlayer.getMinUnmortgageCash()+.9*(_previousPlayer.getMinUnmortgageCash()-_player.getMinUnmortgageCash())));
				_player.setMinBuildCash((int) Math.max(0,_previousPlayer.getMinBuildCash()+.9*(_previousPlayer.getMinBuildCash()-_player.getMinBuildCash())));
				_player.setTradingFear(Math.min(Math.max(1,_previousPlayer.getTradingFear()+.9*(_previousPlayer.getTradingFear()-_player.getTradingFear())),10));
			}
		}
		_previousPlayer=temp;
		return _player;
	}

	private PropertyDataReport findMyReport(List<PropertyDataReport> reports) {
		if(reports==null) {
			return null;
		}
		for(PropertyDataReport r: reports) {
			if(r.ownerID==_id) {
				return r;
			}
		}
		return null;
	}

	protected void respondToDisplayError(ClientRequestContainer request) {} /* do nothing, there is no error to display */

	protected void setPlayerNames(List<Player> players){} /* do nothing */
}
