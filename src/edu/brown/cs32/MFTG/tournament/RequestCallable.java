package edu.brown.cs32.MFTG.tournament;

import java.io.IOException;
import java.util.concurrent.Callable;

import edu.brown.cs32.MFTG.networking.InvalidRequestException;

/**
 * Callable for the main loop of the client
 * @author arkleima
 *
 */
public class RequestCallable implements Callable<Void>{

	private final HumanClient _client;

	public RequestCallable(HumanClient client){
		_client = client;
	}

	public Void call() throws Exception {
		while(_client.isRunning()){
			try {
			_client.handleRequest();
			} catch (InvalidRequestException e){
				if (_client.isRunning()) _client.displayMessage("An invalid request has been received from the server. Now exiting the game.");
				_client.returnToWelcomeScreen();
				_client._gamePool.shutdown();
				return null;
			} catch (IOException e){
				if (_client.isRunning()) _client.displayMessage("An error communicating with the server has occured. Now exiting the game.");
				_client.returnToWelcomeScreen();
				_client._gamePool.shutdown();
				return null;
			} catch (Exception e){
				if (_client.isRunning()) _client.displayMessage("An error has occured. Now exiting the game.");
				_client.returnToWelcomeScreen();
				_client._gamePool.shutdown();
				return null;
			}
		}
		return null;
	}
}

