package edu.brown.cs32.MFTG.tournament;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.Lists;

import edu.brown.cs32.MFTG.gui.MonopolyGui;
import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.ClientRequestContainer;
import edu.brown.cs32.MFTG.networking.InvalidRequestException;
import edu.brown.cs32.MFTG.networking.getPlayerCallable;
import edu.brown.cs32.MFTG.tournament.data.DataProcessor;
import edu.brown.cs32.MFTG.tournament.data.GameDataAccumulator;
import edu.brown.cs32.MFTG.tournament.data.GameDataReport;

public class HumanClient extends Client{

	private static final int DEFAULT_PORT = 8080;
	
	private MonopolyGui _gui;
	private ExecutorService _executor = Executors.newCachedThreadPool();
//	private DummyGUI _dummyGui;

	public HumanClient(/*String host, int port*/){
		//super(host, port);
		super();
		_gui = new MonopolyGui(this);
//		_dummyGui = new DummyGUI();
	}
	
	/**
	 * Connects the Client to a socket
	 * @throws IOException
	 */
	public void connectAndRun(int port, String host){
		try {
			_server = new Socket(host, port);
			//_server = new Socket(_host, _port);
			_input = new BufferedReader(new InputStreamReader(_server.getInputStream()));
			_output = new BufferedWriter(new OutputStreamWriter(_server.getOutputStream()));
		} catch (UnknownHostException e) {
			displayMessage("Unknown host. Unable to connect to server :( WHAT THE FUCK, MAN!!!");
			return;
		} catch (IOException e) {
			displayMessage("Unable to connect to server :(");
			return;
		}
		try {
			_id = respondToSendID();
			_gui.createBoard(_id);
		} catch (IOException | InvalidRequestException e1) {
			displayMessage("Unable to retrieve a unique ID from the server :(");
			return;
		}
		
		Callable<Void> worker = new RequestCallable(this);
		_executor.submit(worker);
	}

	/***************Networking Methods*************************/

	/**
	 * 
	 * @param the request which is being responded to
	 */
	protected void respondToDisplayError(ClientRequestContainer request){
		List<String> arguments = request._arguments;

		if (arguments == null){
			// throw an error
		} else if (arguments.size() < 1){
			// throw a different error
		}

		displayMessage(arguments.get(0));
	}

	/**
	 * Responds to a request sent over the server to display game data by displaying the data received
	 * @param the request which is being responded to
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	protected void respondToDisplayData(ClientRequestContainer request) throws JsonParseException, JsonMappingException, IOException{
		List<String> arguments = request._arguments;

		if (arguments == null){
			// error
		} else if (arguments.size() < 1){
			// error
		}

		GameDataReport gameDataReport = _oMapper.readValue(arguments.get(0), GameDataReport.class);

		displayGameData(gameDataReport);
	}

	/*******************************************************/

	/**
	 * Set and display the combined GameData
	 * @param combinedData
	 */
	public void displayGameData(GameDataReport combinedData) {
		System.out.println("displaying end of round data");
		displayDataToGui(combinedData);
		_gui.getBoard().roundCompleted();
		displayMessage("Time to choose heuristics!");
		
	}
	
	private void displayDataToGui(GameDataReport combinedData){
		_gui.getBoard().setPlayerSpecificPropertyData(getPlayerPropertyData(combinedData._overallPlayerPropertyData));
		_gui.getBoard().setPropertyData(combinedData._overallPropertyData);
		_gui.getBoard().setWealthData(getPlayerWealthData(combinedData._timeStamps));
//		_dummyGui.setPlayerSpecificPropertyData(getPlayerPropertyData(combinedData._overallPlayerPropertyData));
//		_dummyGui.setPropertyData(combinedData._overallPropertyData);
//		_dummyGui.setWealthData(getPlayerWealthData(combinedData._timeStamps));
	}
	
	/**
	 * Called by the game runnables to update GameData info
	 * when they finish playing
	 * @param gameData
	 */
	public synchronized void addGameData(GameData gameData){
		List<GameData> temp = new ArrayList<>();
		temp.add(gameData);
		GameDataAccumulator a = DataProcessor.aggregate(temp,NUM_DATA_POINTS);
		if(_data == null){
			_data = a;
		} else {
			DataProcessor.combineAccumulators(_data, DataProcessor.aggregate(temp,NUM_DATA_POINTS));
		}
		_numGamesPlayed++;
		if(_numGamesPlayed >= _nextDisplaySize){
			displayDataToGui(_data.toGameDataReport());
			_nextDisplaySize += DATA_PACKET_SIZE; //set next point at which to display
		}
	}
	
	/**
	 * Gets the player associated with this object
	 * @param the time, in seconds, to wait before requesting the player
	 * @return
	 */
	public Player getPlayer(int time){
		System.out.println(_gui.getBoard().getPlayer());
		return _gui.getBoard().getPlayer();
	}

	/**
	 * Displays an error message in the gui
	 * @param errorMessage the error message to be displayed
	 */
	private void displayMessage(String message){
		JOptionPane.showMessageDialog(_gui, message);
	}

	/*******************************************************/
}
