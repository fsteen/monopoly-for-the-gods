package edu.brown.cs32.MFTG.networking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.ClientRequestContainer.Method;

public class ClientHandler implements iClientHandler{
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
	 */
	public Player getPlayer() throws ClientCommunicationException{
		ClientRequestContainer request = new ClientRequestContainer(Method.GETPLAYER, new ArrayList<String>());

		// ask the client for gameData
		try {
			_oMapper.writeValue(_output, request);

			// read in the response
			ClientRequestContainer response = _oMapper.readValue(_input, ClientRequestContainer.class);

			if (response._method != Method.SENDPLAYER){
				// throw an exception or some shit
			}

			if (response._arguments == null || response._arguments.size() < 1){
				// throw an exception or some shit
			}

			Player p = _oMapper.readValue(response._arguments.get(0), Player.class);

			return p;

		} catch (IOException e) {
			throw new ClientCommunicationException();
		}
	}

	/**
	 * Sends an encoding of the players to the client and a request to play numGame games
	 * @param players
	 * @param numGames
	 * @return the GameData collected from playing the round of games
	 */
	public List<GameData> playGames(List<Player> players, List<Long> seeds) throws ClientCommunicationException{
		try {
			String playerList = _oMapper.writeValueAsString(players);
			String seedList = _oMapper.writeValueAsString(seeds);

			ClientRequestContainer request = new ClientRequestContainer(Method.PLAYGAMES, Arrays.asList(playerList, seedList));

			// request that the client play the games
			_oMapper.writeValue(_output, request);


			// read in the response
			ClientRequestContainer response = _oMapper.readValue(_input, ClientRequestContainer.class);

			if (response._method != Method.SENDGAMEDATA){
				// do something
			}

			if (response._arguments == null || response._arguments.size() < 1){
				// shit fucked up, son!
			}

			JavaType listOfGameData = _oMapper.getTypeFactory().constructCollectionType(List.class, GameData.class);
			List<GameData> gameData = _oMapper.readValue(response._arguments.get(0), listOfGameData);

			return gameData;

		} catch (IOException e){
			throw new ClientCommunicationException();
		}
	}

	//TODO alex : i think i realized this should be just 1 GameData not a list ... i let you change it though
	public void setGameData(GameData combinedData) {
		try {
			String data = _oMapper.writeValueAsString(combinedData);

			ClientRequestContainer request = new ClientRequestContainer(Method.DISPLAYGAMEDATA, Arrays.asList(data));

			// request that the client display the data
			_oMapper.writeValue(_output, request);

		} catch (Exception e){
			// TODO fix this
		}
	}
}
