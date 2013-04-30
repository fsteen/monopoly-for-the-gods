package edu.brown.cs32.MFTG.networking;

import java.util.concurrent.Callable;

import edu.brown.cs32.MFTG.monopoly.Player;

/**
 * Allows calls to getPlayer() to be threaded
 * @author arkleima
 *
 */
public class getPlayerCallable implements Callable<Player>{

	private final ClientHandler _client;
	
	public getPlayerCallable(ClientHandler client){
		_client = client;
	}
	
	public Player call() throws Exception{
		return _client.getPlayer();
	}
}
