package edu.brown.cs32.MFTG.tournament;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
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

	/* Module variables */
	DummyGUI _gui;
	private final int NUM_THREADS=10;
	private final int DATA_PACKET_SIZE=10;
	private int _nextDisplaySize;
	private List<GameData> _data;
	private AtomicInteger _threadsDone;

	public PlayerModule(String host, int port){
		_oMapper = new ObjectMapper();
		_host = host;
		_port = port;

		_data = new ArrayList<>();
		_threadsDone = new AtomicInteger(0);
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

	/**
	 * Connects the PlayerModule to a socket
	 * @throws IOException
	 */
	private void connect() throws IOException{
		_server = new Socket(_host, _port);
		_input = new BufferedReader(new InputStreamReader(_server.getInputStream()));
		_output = new BufferedWriter(new OutputStreamWriter(_server.getOutputStream()));
	}

	/**
	 * Responds to a request sent over the socket for player data, and sends the player information back in response
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
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

	/**
	 * Responds to a request sent over the socket to play games, and sends the game data back in response
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private void respondToPlayGames() throws JsonParseException, JsonMappingException, IOException{
		ClientRequestContainer request = _oMapper.readValue(_input, ClientRequestContainer.class);
		
		if (request == null){
			// throw error
		} else if (request._method != Method.PLAYGAMES){
			// throw different error
		} 
		
		List<String> arguments = request._arguments;
		
		if (arguments == null){
			// throw a different different error
		} else if (arguments.size() < 2){
			// you get the idea
		}
		
		JavaType listOfPlayers = _oMapper.getTypeFactory().constructCollectionType(List.class, Player.class);
		JavaType listOfSeeds = _oMapper.getTypeFactory().constructCollectionType(List.class, Long.class);
		
		List<Player> players = _oMapper.readValue(arguments.get(0), listOfPlayers);
		List<Long> seeds = _oMapper.readValue(arguments.get(1), listOfSeeds);
		
		List<GameData> gameData = playGames(players, seeds);
		String gameDataString = _oMapper.writeValueAsString(gameData);
		
		ClientRequestContainer response = new ClientRequestContainer(Method.SENDGAMEDATA, Arrays.asList(gameDataString));
		
		_oMapper.writeValue(_output, response);
	}

	/**
	 * Responds to a request sent over the server to display game data by displaying the data received
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private void respondToDisplayData() throws JsonParseException, JsonMappingException, IOException{
		ClientRequestContainer request = _oMapper.readValue(_input, ClientRequestContainer.class);
		
		if (request == null){
			// throw error
		} else if (request._method != Method.DISPLAYGAMEDATA){
			// throw different error
		}
		
		List<String> arguments = request._arguments;
		
		if (arguments == null){
			// error
		} else if (arguments.size() < 1){
			// error
		}
		
//		JavaType listOfGameData = _oMapper.getTypeFactory().constructCollectionType(List.class, GameData.class);
		
		GameData gameData = _oMapper.readValue(arguments.get(0), GameData.class);
		
		setGameData(gameData);
	}

	/*******************************************************/

	/***************Module Methods *************************/

	/**
	 * Gets the player associated with this object
	 * @return
	 */
	public Player getPlayer(){
		return _gui.getPlayer();
	}


	public List<GameData> playGames(List<Player> players, List<Long> seeds){
		_data.clear();
		_nextDisplaySize = DATA_PACKET_SIZE;
		synchronized(this){ //synchronize on the playermodule
			_threadsDone.set(0);
		}

		//construct a game from the settings
		GameRunnerFactory gameRunnerFactory = new GameRunnerFactory(_threadsDone, this, 1000,-1,false,false,players.toArray(new Player[players.size()]));

		ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
		for(int i = 0; i < seeds.size(); i++){ //execute games and record data
			pool.execute(gameRunnerFactory.build(seeds.get(i)));
		}
		
		//wait for all of the runnables to complete
		synchronized (this){
			while(_threadsDone.get() < seeds.size()){
				try{
					this.wait();
				} catch (InterruptedException e){}
			}
		}
				
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

	public void setGameData(GameData combinedData) {
		// TODO Auto-generated method stub
	}
	
	/*******************************************************/

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
