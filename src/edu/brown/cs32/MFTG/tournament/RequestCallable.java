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
		while(true){
			try {
			_client.handleRequest();
			} catch (InvalidRequestException e){
				_client.displayMessage("An invalid request has been received from the server. Now exiting the game.");
				_client.returnToWelcomeScreen();
				return null;
			} catch (IOException e){
				_client.displayMessage("An error communicating with the server has occured. Now exiting the game.");
				_client.returnToWelcomeScreen();
				return null;
			} catch (Exception e){
				e.printStackTrace();
				_client.returnToWelcomeScreen();
				return null;
			}
		}
	}
}

