package edu.brown.cs32.MFTG.tournament;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.tournament.Settings.Turns;
import edu.brown.cs32.MFTG.tournament.Settings.WinningCondition;

public class Profile {
	public final String _name;
	Map<String, Player> _players;
	Map<String, Settings> _settings;

	@JsonCreator
	public Profile(@JsonProperty("name") String name){
		_name = name;
		buildPlayersMap();
		buildSettingsMap();
	}
	
	/**
	 * Builds the Players that are included in every profile 
	 */
	private void buildPlayersMap(){
		Player timid = new Player(1);
		Player balanced = new Player(2);
		Player aggressive = new Player(3);
		
		Map<String, Player> players = new HashMap<>();
		
		players.put("timid", timid);
		players.put("balanced", balanced);
		players.put("aggressive", aggressive);
		
		_players = players;
	}
	
	/**
	 * Builds the settings that are included in every profile
	 */
	private void buildSettingsMap(){
		Map<String, Settings> settings = new HashMap<>();
		
		Settings defaultSettings = new Settings(10, 100, 1000, false, false, false, Turns.BUNCHED, WinningCondition.MOST_SETS_WON, null);
		
		settings.put("default", defaultSettings);
		
		_settings = settings;
	}
	
	@Override
	public boolean equals(Object o){
		if (o == null || !(o instanceof Profile))
			return false;
		
		Profile that = (Profile) o;
		
		return Objects.equals(_name, that._name) && Objects.equals(_players, that._players) 
			&& Objects.equals(_settings, that._settings);
	}

	/**
	 * 
	 * @return _players
	 */
	public Map<String, Player> getPlayers() {
		return _players;
	}

	/**
	 * Setter for the players attribute
	 * @param _players
	 */
	public void setPlayers(Map<String, Player> players) {
		_players = players;
	}

	/**
	 * 
	 * @return _settings;
	 */
	public Map<String, Settings> getSettings() {
		return _settings;
	}

	/**
	 * Setter for the settings attribute
	 * @param _settings
	 */
	public void setSettings(Map<String, Settings> settings) {
		_settings = settings;
	}
	
}
