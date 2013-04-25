package edu.brown.cs32.MFTG.tournament;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.ClientRequestContainer;
import edu.brown.cs32.MFTG.networking.ClientRequestContainer.Method;

public class PlayerModule {

	private static final int DEFAULT_PORT = 8080;

	/* Networking variables */
	final String _host;
	final int _port;
	private Socket _server;
	private BufferedReader _input;
	private BufferedWriter _output;
	final ObjectMapper _oMapper;


	DummyGUI _gui;
	private final int NUM_THREADS=10;
	private final int DATA_PACKET_SIZE=10;
	private int _nextDisplaySize;
	private List<GameData> _data;



	public PlayerModule(String host, int port){
		_oMapper = new ObjectMapper();
		_host = host;
		_port = port;

		_data = new ArrayList<>();
		_gui = new DummyGUI();
	}

	public void run(){
		while(true){
			try {
				respondToGetPlayer();
				respondToPlayGames();
				respondToDisplayData();
			} catch (Exception e){
				// TODO handle this
			}
		}
	}

	/***************Networking Methods *************************/

	private void connect() throws IOException{
		_server = new Socket(_host, _port);
		_input = new BufferedReader(new InputStreamReader(_server.getInputStream()));
		_output = new BufferedWriter(new OutputStreamWriter(_server.getOutputStream()));
	}

	private void respondToGetPlayer() throws JsonParseException, JsonMappingException, IOException{
		ClientRequestContainer request = _oMapper.readValue(_input, ClientRequestContainer.class);

		if (request == null){
			// throw error
		} else if (request._method != Method.GETPLAYER){
			// throw different error
		}
		
		Player p = getPlayer();
		
		_oMapper.writeValue(_output, p);
	}

	private void respondToPlayGames(){

	}

	private void respondToDisplayData(){

	}

	/************************************************************/

	/**
	 * Gets the player associated with this object
	 * @return
	 */
	public Player getPlayer(){
		//TODO calls method in the GUI to get player
		return _gui.getPlayer();
	}


	public List<GameData> playGames(List<Player> players, List<Long> seeds){
		_data.clear();
		_nextDisplaySize = DATA_PACKET_SIZE;

		//construct a game from the settings
		GameRunnerFactory gameRunnerFactory = new GameRunnerFactory(this, 1000,-1,false,false,(Player[])players.toArray());

		ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
		for(int i = 0; i < seeds.size(); i++){ //execute games and record data
			pool.execute(gameRunnerFactory.build(seeds.get(i)));
		}

		// Isn't this a pretty serious race condition? I think you'll always reach the return statement before
		// _data has actually been updated
		return _data;
	}

	public synchronized void addGameData(GameData gameData){
		_data.add(gameData);
		if(_data.size() >= _nextDisplaySize){
			//display some data
			_nextDisplaySize += DATA_PACKET_SIZE; //set next point at which to display
		}
	}

	public List<GameData> getGameData(){
		//TODO
		return null;
	}

	public void setGameData(List<GameData> combinedData) {
		// TODO Auto-generated method stub
	}

	public static void main (String[] args){
		int port;

		if(args.length == 1){
			port = DEFAULT_PORT;
		} else if (args.length == 2){
			try {
				port = Integer.parseInt(args[1]);
			} catch (NumberFormatException e){
				System.out.println("ERROR: please enter a valid number");
				System.out.println("Usage: <hostname> [serverport]");
				return;
			}
		} else {
			System.out.println("Usage: <hostname> [serverport]");
			return;
		}

		PlayerModule pm = new PlayerModule(args[0], port);

		try {
			pm.connect();
		} catch (IOException e){
			System.out.println("Unable to connect to the server. Program will not exit.");
		}

		pm.run();
	}
}