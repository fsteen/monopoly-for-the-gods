package edu.brown.cs32.MFTG.tournament;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.tournament.Settings.WinningCondition;

public class Profile {
	public final String _name;
	Map<String, Player> _players;
	private Record _record;

	@JsonCreator
	public Profile(@JsonProperty("name") String name){
		_name = name;
		buildPlayersMap();
		_record = new Record();
	}
	
	/**
	 * 
	 * @return palyer names
	 */
	@JsonIgnore
	public Set<String> getPlayerNames(){
		return _players.keySet();
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
	
	@Override
	public boolean equals(Object o){
		if (o == null || !(o instanceof Profile))
			return false;
		
		Profile that = (Profile) o;
		
		return Objects.equals(_name, that._name) && Objects.equals(_players, that._players) 
			   && Objects.equals(_record, that.getRecord()); 
	}

	/**
	 * 
	 * @return player
	 */
	@JsonIgnore
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
	
	@Override
	public String toString() {
		return _name;
	}
	
}
