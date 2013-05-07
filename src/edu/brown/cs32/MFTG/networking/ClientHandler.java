package edu.brown.cs32.MFTG.networking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.ClientRequestContainer.Method;
import edu.brown.cs32.MFTG.tournament.Settings;
import edu.brown.cs32.MFTG.tournament.data.GameDataReport;

public class ClientHandler {	
	public final int _id;

	final Socket _client;
	private BufferedReader _input;
	private BufferedWriter _output;
	private ObjectMapper _oMapper;
	private Settings _settings;
	private boolean _isFirstRound;
	private boolean _doubleRead;

	JavaType _listOfPlayers;

	public ClientHandler(Socket client, int id, Settings settings) throws IOException{
		_id = id;
		_isFirstRound = true;
		_doubleRead = false;

		_client = client;
		_input = new BufferedReader(new InputStreamReader(_client.getInputStream()));
		_output = new BufferedWriter(new OutputStreamWriter(_client.getOutputStream()));
		_oMapper = new ObjectMapper();
		_settings = settings;

		_listOfPlayers = _oMapper.getTypeFactory().constructCollectionType(List.class, Player.class);
	}

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
	 * Reads a response form the client
	 * @return
	 * @throws IOException
	 */
	private ClientRequestContainer readResponse() throws IOException{
		String json = _input.readLine();
		ClientRequestContainer c = _oMapper.readValue(json, ClientRequestContainer.class);
		return c;
	}

	/**
	 * Gets the player associated with this object's client
	 * @return the player returned by the client
	 * @throws InvalidResponseException 
	 * @throws IOException 
	 * @throws ClientSideException 
	 */
	public Player getPlayer() throws InvalidResponseException, IOException, SocketTimeoutException, ClientSideException{
		int time = _isFirstRound ? _settings.beginningTimeout : _settings.duringTimeout;
		_isFirstRound = false;

		ClientRequestContainer request = new ClientRequestContainer(Method.GETPLAYER, Arrays.asList(String.valueOf(time)));

		// ask the client for gameData
		write(request);

		// set the timeout and attempt to read the response from the client
		_client.setSoTimeout(0 /*(time + 10) * 1000*/);
		ClientRequestContainer response = readResponse();

		//check for fatal client errors
		if (response._method == Method.GOODBYE){
			throw new ClientSideException();
		}
		
		// check for bad responses 
		if (response._method != Method.SENDPLAYER){
			throw new InvalidResponseException(Method.SENDPLAYER, response._method);
		} else if (response._arguments == null || response._arguments.size() < 1){
			throw new InvalidResponseException("Not enough arguments");
		}

		Player p = _oMapper.readValue(response._arguments.get(0), Player.class);

		return p;
	}

	public List<GameData> readPlayGamesResponse() throws IOException {
		List<GameData> toRet = new ArrayList<>();

		String line;
		while(!(line = _input.readLine()).equals("DONE")){
			GameData g = _oMapper.readValue(line, GameData.class);
			toRet.add(g);
		}

		return toRet;
	}
	
	/**
	 * Sets the doubleRead boolean to true
	 */
	public void setDoubleRead(){
		_doubleRead = true;
	}
	
	/**
	 * 
	 * @return the timeout, in milliseconds, for requests to play games
	 */
	private int gameTimeout(){
		return _settings.getNumGames() * (int) (50./10000. * 1000.) + 2000;
	}

	/**
	 * Sends an encoding of the players to the client and a request to play numGame games
	 * @param players
	 * @param numGames
	 * @return the GameData collected from playing the round of games
	 * @throws InvalidResponseException 
	 * @throws IOException 
	 * @throws ClientSideException 
	 */
	public GameDataReport playGames(List<Player> players, List<Long> seeds, Settings settings) throws InvalidResponseException, IOException, ClientSideException{
		String playerList = _oMapper.writeValueAsString(players);
		String seedList = _oMapper.writeValueAsString(seeds);
		String settingsString = _oMapper.writeValueAsString(settings);
		List<String> arguments = Arrays.asList(playerList, seedList, settingsString);

		ClientRequestContainer request = new ClientRequestContainer(Method.PLAYGAMES, arguments);
		// request that the client play the games
		write(request);

		_client.setSoTimeout(gameTimeout()); // 50 seconds per 10000 games
		
		if (_doubleRead){
			readResponse();
			_doubleRead = false;
		}
		
		ClientRequestContainer response = readResponse();

		//check for fatal client errors
		if (response._method == Method.GOODBYE){
			throw new ClientSideException();
		}
		
		if (response._method != Method.SENDGAMEDATA){
			throw new InvalidResponseException(Method.SENDGAMEDATA, response._method);
		} else if (response._arguments == null || response._arguments.size() < 1){
			throw new InvalidResponseException("Not enough arguments");
		}

		GameDataReport gameData= _oMapper.readValue(response._arguments.get(0), GameDataReport.class);

		return gameData;
	}

	public void setGameData(GameDataReport combinedData) throws IOException {
		String data = _oMapper.writeValueAsString(combinedData);

		ClientRequestContainer request = new ClientRequestContainer(Method.DISPLAYGAMEDATA, Arrays.asList(data));

		// request that the client display the data
		write(request);
	}

	public void sendErrorMessage(String errorMessage) {
		ClientRequestContainer request = new ClientRequestContainer(Method.DISPLAYERROR, Arrays.asList(errorMessage));

		// request that the client display the error message
		try {
			write(request);
		} catch (IOException e) {
			// do nothing -- you are already fucked
		}
	}

	/**
	 * Sends the client it's id and the timeouts that it will need
	 * @throws IOException
	 */
	public void sendIDAndTimeouts() throws IOException {
		String stringID = String.valueOf(_id);
		String duringTimeout = String.valueOf(_settings.duringTimeout * 1000);
		String gt = String.valueOf(gameTimeout());
		
		List<String> args = Arrays.asList(stringID, duringTimeout, gt);
		ClientRequestContainer request = new ClientRequestContainer(Method.SENDCONSTANTS, args);

		// request that the client display the error message
		write(request);
	}

	public Reader getInputReader(){
		return _input;
	}

	public Writer getOutputWriter(){
		return _output;
	}
	
	/**
	 * Performs all necessary cleanup
	 */
	public void shutDown(){
		try {
			_client.close();
		} catch (IOException e) {
			// shrug and say, "fuck it"
		}
	}
}
