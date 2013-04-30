package edu.brown.cs32.MFTG.tournament;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.brown.cs32.MFTG.gui.MonopolyGui;
import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.ClientRequestContainer;
import edu.brown.cs32.MFTG.networking.ClientRequestContainer.Method;
import edu.brown.cs32.MFTG.networking.InvalidRequestException;
import edu.brown.cs32.MFTG.tournament.data.DataProcessor;
import edu.brown.cs32.MFTG.tournament.data.GameDataReport;
import edu.brown.cs32.MFTG.tournament.data.PlayerWealthDataReport;
import edu.brown.cs32.MFTG.tournament.data.PropertyDataReport;
import edu.brown.cs32.MFTG.tournament.data.TimeStampReport;

public class Client {

	private static final int DEFAULT_PORT = 8080;

	/* Networking variables */
	final String _host;
	final int _port;
	private Socket _server;
	private BufferedReader _input;
	private BufferedWriter _output;
	final ObjectMapper _oMapper;
	private Method _lastRequest;

	/* Module variables */
	private MonopolyGui _gui;
//	private DummyGUI _dummyGui;
	private final int NUM_THREADS=25;
	private final int DATA_PACKET_SIZE=100;
	private final int NUM_DATA_POINTS=100;	
	private final int MAX_NUM_TURNS=1000;
	private int _nextDisplaySize;
	private List<GameData> _data;
	private AtomicInteger _numThreadsDone;
	private ExecutorService _pool;
	protected int _id;

	/* Temporary variables - replace later */
	private final int FREE_PARKING=-1;
	private final boolean DOUBLE_ON_GO=false;
	private final boolean AUCTIONS=false;

	public Client(String host, int port){
		_oMapper = new ObjectMapper();
		_host = host;
		_port = port;
		_lastRequest = Method.DISPLAYGAMEDATA;

		_pool = Executors.newFixedThreadPool(NUM_THREADS);
		_data = new ArrayList<>();
		_numThreadsDone = new AtomicInteger(0);

		_gui = new MonopolyGui(this);
	//	_dummyGui = new DummyGUI();
	}

	/***************Networking Methods*************************/

	/**
	 * Writes a request to the output stream
	 * @param request the request to write
	 * @throws IOException
	 */
	private void write(ClientRequestContainer request) throws IOException{
		String json = _oMapper.writeValueAsString(request);
		_output.write(json);
		_output.write("\n");
		_output.flush();
	}
	
	/**
	 * Reads a request from the client
	 * @return
	 * @throws IOException
	 */
	private ClientRequestContainer readRequest() throws IOException{
		String json = _input.readLine();
		return _oMapper.readValue(json, ClientRequestContainer.class);
	}
	
	/**
	 * @throws IOException
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	private void handleRequest() throws InvalidRequestException, IOException{
		ClientRequestContainer request = readRequest();

		Method method = request._method;

		if (method == null)
			throw new InvalidRequestException();

		if (method == Method.GETPLAYER){
			if (_lastRequest == Method.DISPLAYGAMEDATA){ 
				respondToGetPlayer(request);
				_lastRequest = Method.GETPLAYER;
			} else {
				throw new InvalidRequestException();
			}

		} else if (method == Method.PLAYGAMES){
			if (_lastRequest == Method.GETPLAYER){
				respondToPlayGames(request);
				_lastRequest = Method.PLAYGAMES;
			} else {
				throw new InvalidRequestException();
			}

		} else if (method == Method.DISPLAYGAMEDATA){
			if (_lastRequest == Method.PLAYGAMES){
				respondToDisplayData(request);
				_lastRequest = Method.DISPLAYGAMEDATA;
			} else {
				throw new InvalidRequestException();
			}

		} else if (method == Method.DISPLAYERROR){
			respondToDisplayError(request);
		}
	}

	/**
	 * Connects the Client to a socket
	 * @throws IOException
	 */
	public void connectAndRun(){
		try {
			_server = new Socket(_host, _port);
			_input = new BufferedReader(new InputStreamReader(_server.getInputStream()));
			_output = new BufferedWriter(new OutputStreamWriter(_server.getOutputStream()));
		} catch (UnknownHostException e) {
			displayError("Unknown host. Unable to connect to server :( WHAT THE F*CK, MAN!!!");
			return;
		} catch (IOException e) {
			displayError("Unable to connect to server :(");
			return;
		}
		try {
			_id = respondToSendID();
			System.out.println("creading board");
			_gui.createBoard(_id);
			System.out.println("created board");
		} catch (IOException | InvalidRequestException e1) {
			displayError("Unable to retrieve a unique ID from the server :(");
			return;
		}
		
		while(true){
			try {
				handleRequest();
			} catch (Exception e){
				// TODO handle this
			}
		}
	}

	private int respondToSendID() throws IOException, InvalidRequestException{
		
		ClientRequestContainer request = readRequest();

		if (request == null || request._method != Method.SENDID)
			throw new InvalidRequestException();
		

		List<String> arguments = request._arguments;

		if (arguments == null){
			// throw an error
		} else if (arguments.size() < 1){
			// throw a different error
		}

		try {
			return Integer.parseInt(arguments.get(0));
		} catch (NumberFormatException e){
			throw new InvalidRequestException();
		}
	}

	/**
	 * 
	 * @param the request which is being responded to
	 */
	private void respondToDisplayError(ClientRequestContainer request){
		List<String> arguments = request._arguments;

		if (arguments == null){
			// throw an error
		} else if (arguments.size() < 1){
			// throw a different error
		}

		displayError(arguments.get(0));
	}

	/**
	 * Responds to a request sent over the socket for player data, and sends the player information back in response
	 * @param the request which is being responded to
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private void respondToGetPlayer(ClientRequestContainer request) throws JsonParseException, JsonMappingException, IOException{
		List<String> arguments = request._arguments;
		
		if (arguments == null){
			// throw an error
		} else if (arguments.size() < 1){
			// throw a different error
		}
		
		int time = Integer.parseInt(arguments.get(0));
		
		Player p = getPlayer(time);
		
		String playerString = _oMapper.writeValueAsString(p);
		ClientRequestContainer response = new ClientRequestContainer(Method.SENDPLAYER, Arrays.asList(playerString));
		write(response);
	}

	/**
	 * Responds to a request sent over the socket to play games, and sends the game data back in response
	 * @param the request which is being responded to
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private void respondToPlayGames(ClientRequestContainer request) throws JsonParseException, JsonMappingException, IOException{
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

		write(response);
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

	public void launchTournament(int numPlayers, Settings settings, int port){
		try {
			_pool.execute((new Tournament(numPlayers, settings, port)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*******************************************************/

	/***************Module Methods *************************/

	/**
	 * Play seeds.size games in separate threads
	 * @param players the player heuristics
	 * @param seeds the game seeds
	 * @return the data collected from the games
	 */
	public List<GameData> playGames(List<Player> players, List<Long> seeds){
		_data.clear();
		_nextDisplaySize = DATA_PACKET_SIZE;
		synchronized(this){ _numThreadsDone.set(0); }

		GameRunnerFactory gameRunnerFactory = new GameRunnerFactory(
				_numThreadsDone, this, 
				MAX_NUM_TURNS,FREE_PARKING,DOUBLE_ON_GO,
				AUCTIONS,players.toArray(new Player[players.size()]));

		for(Long seed : seeds){
			_pool.execute(gameRunnerFactory.build(seed)); //launch games
		}

		synchronized (this){
			while(_numThreadsDone.get() < seeds.size()){
				try{
					this.wait(); //wait for games to finish
				} catch (InterruptedException e){}
			}
		}

		return _data;
	}

	/**
	 * Called by the game runnables to update GameData info
	 * when they finish playing
	 * @param gameData
	 */
	public synchronized void addGameData(GameData gameData){
		_data.add(gameData);
		if(_data.size() >= _nextDisplaySize){
			//TODO display some data
			GameDataReport r = DataProcessor.aggregate(_data, NUM_DATA_POINTS);
			//_dummyGui.setPlayerSpecificPropertyData(getPlayerPropertyData(r._overallPlayerPropertyData));
		//	_dummyGui.setPropertyData(r._overallPropertyData);
		//	_dummyGui.setWealthData(getPlayerWealthData(r._timeStamps));
			_gui.getBoard().setPlayerSpecificPropertyData(getPlayerPropertyData(r._overallPlayerPropertyData));
			_gui.getBoard().setPropertyData(r._overallPropertyData);
			_gui.getBoard().setWealthData(getPlayerWealthData(r._timeStamps));
			_nextDisplaySize += DATA_PACKET_SIZE; //set next point at which to display
		}
	}

	/**
	 * Find the player property data specific to this player
	 * @param allPlayerPropertyData
	 * @return
	 */
	private Map<String, PropertyDataReport> getPlayerPropertyData(Map<String,List<PropertyDataReport>> allPlayerPropertyData){
		Map<String, PropertyDataReport> playerPropertyData = new HashMap<>();
		for(List<PropertyDataReport> l : allPlayerPropertyData.values()){
			for(PropertyDataReport d : l){
				if(d.ownerID == _id){
					playerPropertyData.put(d.propertyName, d);
					break;
				}
			}
		}
		return playerPropertyData;
	}

	/**
	 * Find the player wealth data specific to this player
	 * @param timeStamps
	 * @return
	 */
	private List<PlayerWealthDataReport> getPlayerWealthData(List<TimeStampReport> timeStamps){
		List<PlayerWealthDataReport> playerWealthData = new ArrayList<>();
		for(TimeStampReport t : timeStamps){
			playerWealthData.add(t.wealthData.get(_id));
		}
		return playerWealthData;
	}

	/**
	 * Gets the player associated with this object
	 * @param the time, in seconds, to wait before requesting the player
	 * @return
	 */
	public Player getPlayer(int time){
		return _gui.getBoard().getPlayer();
//		return _dummyGui.getPlayer();
	}

	/**
	 * Set and display the combined GameData
	 * @param combinedData
	 */
	public void displayGameData(GameDataReport combinedData) {
		//TODO implement
		_gui.getBoard().setPlayerSpecificPropertyData(getPlayerPropertyData(combinedData._overallPlayerPropertyData));
		_gui.getBoard().setPropertyData(combinedData._overallPropertyData);
		_gui.getBoard().setWealthData(getPlayerWealthData(combinedData._timeStamps));
		//_dummyGui.setPlayerSpecificPropertyData(getPlayerPropertyData(combinedData._overallPlayerPropertyData));
	//	_dummyGui.setPropertyData(combinedData._overallPropertyData);
	//	_dummyGui.setWealthData(getPlayerWealthData(combinedData._timeStamps));
		_gui.getBoard().roundCompleted();	
	}

	/**
	 * Displays an error message in the gui
	 * @param errorMessage the error message to be displayed
	 */
	private void displayError(String errorMessage){
		JOptionPane.showMessageDialog(_gui, errorMessage);
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

		Client pm = new Client(args[0], port);

		pm.connectAndRun();
	}
}
