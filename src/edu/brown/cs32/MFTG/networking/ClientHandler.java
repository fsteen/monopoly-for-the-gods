package edu.brown.cs32.MFTG.networking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.ClientRequestContainer.Method;
import edu.brown.cs32.MFTG.tournament.data.GameDataReport;

public class ClientHandler implements iClientHandler{
	
	private final int GET_PLAYER_TIME = 180;
	private final int PLAY_GAMES_TIME = 15;
	
	final Socket _client;
	private BufferedReader _input;
	private BufferedWriter _output;
	private ObjectMapper _oMapper;

	JavaType _listOfPlayers;

	public ClientHandler(Socket client) throws IOException{
		_client = client;
		_input = new BufferedReader(new InputStreamReader(_client.getInputStream()));
		_output = new BufferedWriter(new OutputStreamWriter(_client.getOutputStream()));
		_oMapper = new ObjectMapper();

		_listOfPlayers = _oMapper.getTypeFactory().constructCollectionType(List.class, Player.class);
	}

	/**
	 * Gets the player associated with this object's client
	 * @return
	 * @throws ClientCommunicationException 
	 * @throws InvalidResponseException 
	 */
	public Player getPlayer() throws ClientCommunicationException, ClientLostException, InvalidResponseException{
		ClientRequestContainer request = new ClientRequestContainer(Method.GETPLAYER, new ArrayList<String>());

		// ask the client for gameData
		try {
			_oMapper.writeValue(_output, request);

			// read in the response
			ClientRequestContainer response = _oMapper.readValue(_input, ClientRequestContainer.class);

			// check for bad responses
			if (response._method != Method.SENDPLAYER){
				throw new InvalidResponseException(Method.SENDPLAYER, response._method);
			} else if (response._arguments == null || response._arguments.size() < 1){
				throw new InvalidResponseException("Not enough arguments");
			}
			
			// set the timeout and attempt to read the response from the client
			try {
				_client.setSoTimeout(GET_PLAYER_TIME * 1000);
				Player p = _oMapper.readValue(response._arguments.get(0), Player.class);
				_client.setSoTimeout(0);
				return p;
			} catch (SocketTimeoutException | SocketException e){
				throw new ClientLostException();
			}

		} catch (IOException e) {
			throw new ClientCommunicationException();
		}
	}

	/**
	 * Sends an encoding of the players to the client and a request to play numGame games
	 * @param players
	 * @param numGames
	 * @return the GameData collected from playing the round of games
	 * @throws ClientLostException 
	 * @throws InvalidResponseException 
	 */
	public List<GameData> playGames(List<Player> players, List<Long> seeds) throws ClientCommunicationException, ClientLostException, InvalidResponseException{
		try {
			String playerList = _oMapper.writeValueAsString(players);
			String seedList = _oMapper.writeValueAsString(seeds);

			ClientRequestContainer request = new ClientRequestContainer(Method.PLAYGAMES, Arrays.asList(playerList, seedList));

			// request that the client play the games
			_oMapper.writeValue(_output, request);


			// read in the response
			ClientRequestContainer response = _oMapper.readValue(_input, ClientRequestContainer.class);

			// check for bad response
			if (response._method != Method.SENDGAMEDATA){
				throw new InvalidResponseException(Method.SENDGAMEDATA, response._method);
			} else if (response._arguments == null || response._arguments.size() < 1){
				throw new InvalidResponseException("Not enough arguments");
			}

			// construct the JavaType that will be read in
			JavaType listOfGameData = _oMapper.getTypeFactory().constructCollectionType(List.class, GameData.class);

			// set the timeout and attempt to read the response from the client
			try {
				_client.setSoTimeout(PLAY_GAMES_TIME * 1000);
				List<GameData> gameData = _oMapper.readValue(response._arguments.get(0), listOfGameData);
				_client.setSoTimeout(0);
				return gameData;
			}  catch (SocketTimeoutException | SocketException e){
				throw new ClientLostException();
			}

		} catch (IOException e){
			throw new ClientCommunicationException();
		}
	}

	public void setGameData(GameDataReport combinedData) throws ClientCommunicationException {
		try {
			String data = _oMapper.writeValueAsString(combinedData);

			ClientRequestContainer request = new ClientRequestContainer(Method.DISPLAYGAMEDATA, Arrays.asList(data));

			// request that the client display the data
			_oMapper.writeValue(_output, request);

		} catch (IOException e){
			throw new ClientCommunicationException();
		}
	}
}
