package edu.brown.cs32.MFTG.networking;

import java.util.Collection;

import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.tournament.Settings;

public class ClientRequestContainer {
	public enum Method {GETPLAYER, PLAYGAMES, GETGAMEDATA, SENDGAMEDATA};
	
	private Method _method;
	private Collection<Player> _players;
	private Settings _settings;
	private int _numGames;
	
	public ClientRequestContainer(Method method, Collection<Player> players, Settings settings, int numGames){
		_method = method;
		_players = players;
		_settings = settings;
		_numGames = numGames;
	}
	
	// getters for JSON serialization
	public Method getMethod() { return _method; }
	public Collection<Player> getPlayers() { return _players; }
	public Settings getSettings() { return _settings; }
	public int getNumGames() { return _numGames; }
	
	// setters for JSON deserialization
	public void setMethod(Method m) { _method = m; }
	public void setPlayers(Collection<Player> p) { _players = p; }
	public void setSettings(Settings s) { _settings = s; }
	public void setNumGames(int x) { _numGames = x; }
}
