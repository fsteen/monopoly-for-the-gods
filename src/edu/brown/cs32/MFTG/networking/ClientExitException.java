package edu.brown.cs32.MFTG.networking;

@SuppressWarnings("serial")
public class ClientExitException extends Exception {
	public final boolean cleanExit;
	
	public ClientExitException(String message){
		this.cleanExit = message.equals("true");
	}
}
