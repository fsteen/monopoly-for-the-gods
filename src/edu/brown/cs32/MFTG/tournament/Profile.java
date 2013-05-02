package edu.brown.cs32.MFTG.tournament;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.tournament.Settings.Turns;
import edu.brown.cs32.MFTG.tournament.Settings.WinningCondition;

public class Profile {
	public final String _name;
	Map<String, Player> _players;
	Map<String, Settings> _settings;
	private Record _record;

	@JsonCreator
	public Profile(@JsonProperty("name") String name){
		_name = name;
		buildPlayersMap();
		buildSettingsMap();
		_record = new Record();
	}
	
	/**
	 * 
	 * @return palyer names
	 */
	public Set<String> getPlayerNames(){
		return _players.keySet();
	}
	
	/**
	 * 
	 * @return get settings names
	 */
	public Set<String> getSettingsNames(){
		return _settings.keySet();
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
		
		Settings defaultSettings = new Settings(10000, 10, false, -1, false, Turns.BUNCHED, WinningCondition.MOST_SETS_WON, 300, 180);
		
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
	 * @return player
	 */
	public Player getPlayer(String s) {
		return _players.get(s);
	}
	
	/**
	 * 
	 * @param p
	 * @param name
	 * @return if it happened
	 */
	public boolean addPlayer(Player p, String name) {
		if(_players.get(name)!=null) {
			return false;
		}
		_players.put(name,p);
		return true;
	}
	
	/**
	 * remove a player
	 * @param name
	 * @return if it happened
	 */
	public boolean removePlayer(String name) {
		if(_players.get(name)==null) {
			return false;
		}
		_players.remove(name);
		return true;
	}
	
	/**
	 * adds a player or replaces previous if it exists
	 * @param p
	 * @param name
	 */
	public void replacePlayer(Player p, String name) {
		if(_players.get(name)!=null) {
			_players.remove(name);
		}
		_players.put(name,p);
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

	/**
	 * @return the _record
	 */
	public Record getRecord() {
		return _record;
	}

	/**
	 * @param _record the _record to set
	 */
	public void setRecord(Record record) {
		_record = record;
	}
	
}
