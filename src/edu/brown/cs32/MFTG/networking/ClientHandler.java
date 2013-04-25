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
			
		_oMapper.writeValue(_output, request);
		ClientRequestContainer c = _oMapper.readValue(_input, ClientRequestContainer.class);
		
		if (c._method != Method.SENDPLAYER){
			// throw an exception or some shit
		}
		
		if (c._arguments.size() != 1){
			// throw an exception or some shit
		}
		
		
		
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
	public List<GameData> playGames(List<Player> players, Settings settings, List<Long> seeds){
		//TODO implement
		//set up a data structure for collecting game data
		//create a thread pool for playing the games
		//
		return null;
	}
	
//	public List<GameData> getGameData(){
//		//TODO
//		return null;
//	}

	public void setGameData(List<GameData> combinedData) {
		// TODO Auto-generated method stub
		
	}
}
