package edu.brown.cs32.MFTG.tournament;

import java.util.concurrent.Callable;

/**
 * Callable for the main loop of the client
 * @author arkleima
 *
 */
public class RequestCallable implements Callable<Void>{

	private final Client _client;

	public RequestCallable(Client client){
		_client = client;
	}

	public Void call() throws Exception {
		while(true){
			_client.handleRequest();
		}
	}
}

