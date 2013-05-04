package edu.brown.cs32.MFTG.networking;

import java.util.concurrent.Callable;

import edu.brown.cs32.MFTG.monopoly.Player;

/**
 * Allows calls to getPlayer() to be threaded
 * @author arkleima
 *
 */
public class getPlayerCallable implements Callable<Player>{

	private final ClientHandler _clientHandler;
	
	public getPlayerCallable(ClientHandler client){
		_clientHandler = client;
	}
	
	public Player call() throws Exception{
		return _clientHandler.getPlayer();
	}
}
