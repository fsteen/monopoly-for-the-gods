package edu.brown.cs32.MFTG.networking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.tournament.Settings;

public class ClientHandler implements iClientHandler{
	final Socket _client;
	private BufferedReader _input;
	private BufferedWriter _output;
	
	public ClientHandler(Socket client) throws IOException{
		_client = client;
		
		_input = new BufferedReader(new InputStreamReader(_client.getInputStream()));
		_output = new BufferedWriter(new OutputStreamWriter(_client.getOutputStream()));
	}
	
	/**
	 * Gets the player associated with this object's client
	 * @return
	 */
	public Player getPlayer(){
		//TODO calls method in the GUI to get player
		return null;
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
