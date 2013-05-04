package edu.brown.cs32.MFTG.networking;

import edu.brown.cs32.MFTG.networking.ClientRequestContainer.Method;

@SuppressWarnings("serial")
public class InvalidResponseException extends Exception{
	Method _expectedMethod = null;
	Method _receivedMethod = null;
	String _message;
	
	public InvalidResponseException(Method expectedMethod, Method receivedMethod){
		_expectedMethod = expectedMethod;
		_receivedMethod = receivedMethod;
	}
	
	public InvalidResponseException(String message){
		_message = message;
	}
	
	@Override
	public String getMessage(){
		if (_expectedMethod != null){
			return "Expected " + _expectedMethod.name() + " but got " + 
					(_receivedMethod != null) != null ? _receivedMethod.name() : "nothing";
		} else {
			return _message;
		}
	}
}
