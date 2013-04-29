package edu.brown.cs32.MFTG.networking;

public class ClientCommunicationException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1575040800219490852L;

	public final int culprit;
	
	public ClientCommunicationException(int culprit){
		this.culprit = culprit;
	}
}
