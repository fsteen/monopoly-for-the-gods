package edu.brown.cs32.MFTG.networking;

public class ClientLostException extends Exception{

	public final int culprit; 
	/**
	 * 
	 */
	private static final long serialVersionUID = -3234202213048698882L;
	
	public ClientLostException(int culprit){
		this.culprit = culprit;
	}

}
