package edu.brown.cs32.MFTG.networking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.ClientRequestContainer.Method;
import edu.brown.cs32.MFTG.tournament.Settings;

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
	 */
	public Player getPlayer(){
		ClientRequestContainer request = new ClientRequestContainer(Method.GETPLAYER, new ArrayList<String>());
		try {
		
		// ask the client for gameData
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
		
		} catch (Exception e){
			// TODO fix this
			return null;
		}
	}
	
	/**
	 * Sends an encoding of the players to the client and a request to play numGame games
	 * @param players
	 * @param numGames
	 * @return the GameData collected from playing the round of games
	 */
	public List<GameData> playGames(List<Player> players, List<Long> seeds){
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
		
		} catch (Exception e){
			// TODO fix this
			return null;
		}
	}

	public void setGameData(GameData aggregatedData) {
		// TODO Auto-generated method stub
	}
}
