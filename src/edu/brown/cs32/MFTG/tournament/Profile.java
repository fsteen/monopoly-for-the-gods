package edu.brown.cs32.MFTG.tournament;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.brown.cs32.MFTG.monopoly.Player;

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

	private void setTimidValues(Player p) {
		p.setColorValue("purple", 1.2, 1.1, 1.1, 1.05);
		p.setColorValue("light blue", 1.2, 1.1, 1.1, 1.05);
		p.setColorValue("pink", 1.2, 1.1, 1.1, 1.05);
		p.setColorValue("orange", 1.2, 1.1, 1.1, 1.05);
		p.setColorValue("red", 1.2, 1.1, 1.1, 1.05);
		p.setColorValue("yellow", 1.2, 1.1, 1.1, 1.05);
		p.setColorValue("green", 1.2, 1.1, 1.1, 1.05);
		p.setColorValue("dark blue", 1.2, 1.1, 1.1, 1.05);

		p.setPropertyValue("oriental avenue", 150);
		p.setPropertyValue("vermont place", 140);
		p.setPropertyValue("connecticut avenue", 170);

		p.setPropertyValue("tennessee avenue", 220);
		p.setPropertyValue("st. james place", 220);
		p.setPropertyValue("new york avenue", 240);

		p.setJailWait(1);
		p.setMinBuildCash(200);
		p.setMinBuyCash(150);
		p.setMinUnmortgageCash(300);
		p.setTradingFear(1.3);
		p.setLiquidity(7);
	}

	private void setAggressiveValues(Player p) {

	}

	private void setBalancedValues(Player p) {

	}

	@Override
	public String toString() {
		return _name;
	}

}
