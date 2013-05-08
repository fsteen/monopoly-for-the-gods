package edu.brown.cs32.MFTG.networking;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientRequestContainer {
	public enum Method {GETPLAYER, SENDPLAYER, PLAYGAMES, SENDGAMEDATA, DISPLAYGAMEDATA, DISPLAYERROR, SENDCONSTANTS, GOODBYE, GAMECLOSED};
	
	public final Method _method;
	public final List<String> _arguments;
	
	@JsonCreator
	public ClientRequestContainer(@JsonProperty("method") Method method, @JsonProperty("arguments") List<String> arguments){
		_method = method;
		_arguments = arguments;
	}
}