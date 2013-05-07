package edu.brown.cs32.MFTG.networking;

public class InvalidRequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2136429318734216526L;
	
	public InvalidRequestException(){
		
	}

	public InvalidRequestException(String message){
		super(message);
	}
}
