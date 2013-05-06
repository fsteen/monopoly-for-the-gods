package edu.brown.cs32.MFTG.tournament;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.monopoly.Player.Aggression;
import edu.brown.cs32.MFTG.monopoly.Player.Amount;
import edu.brown.cs32.MFTG.monopoly.Player.Balance;

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
		Player timid = new Player(1,"timid");
		Player balanced = new Player(2,"balanced");
		Player aggressive = new Player(3,"aggressive");
		setTimidValues(timid);
		setBalancedValues(balanced);
		setAggressiveValues(aggressive);

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

		p.setJailWait(3);
		p.setJailRich(3);
		p.setJailPoor(3);
		p.setMinBuildCash(400);
		p.setMinBuyCash(300);
		p.setMinUnmortgageCash(600);
		p.setTradingFear(1.5);
		p.setLiquidity(3);
		p.setBuildAggression(Aggression.PASSIVE);
		p.setBuildingEvenness(Balance.UNEVEN);
		p.setHouseSelling(Amount.MORE);
		
		p.setPropertyValue("mediterranean avenue", 50);
		p.setPropertyValue("baltic avenue", 50);
		p.setPropertyValue("reading railroad", 190);
		p.setPropertyValue("oriental avenue", 90);
		p.setPropertyValue("vermont avenue", 90);
		p.setPropertyValue("connecticut avenue", 110);
		p.setPropertyValue("st. charles place", 130);
		p.setPropertyValue("electric company", 140);
		p.setPropertyValue("states avenue", 150);
		p.setPropertyValue("virginia avenue", 150);
		p.setPropertyValue("pennsylvania railroad", 190);
		p.setPropertyValue("st. james place", 170);
		p.setPropertyValue("tennessee avenue", 170);	
		p.setPropertyValue("new york avenue", 190);	
		p.setPropertyValue("kentucky avenue", 210);	
		p.setPropertyValue("indiana avenue", 210);	
		p.setPropertyValue("illinois avenue", 230);		
		p.setPropertyValue("b and o railroad",190);		
		p.setPropertyValue("atlantic avenue", 250);		
		p.setPropertyValue("ventnor avenue", 250);		
		p.setPropertyValue("water works",140);		
		p.setPropertyValue("marvin gardens", 270);	
		p.setPropertyValue("pacific avenue", 290);
		p.setPropertyValue("north carolina avenue", 290);
		p.setPropertyValue("pennsylvania avenue", 310);
		p.setPropertyValue("short line", 190);
		p.setPropertyValue("park place", 340);
		p.setPropertyValue("boardwalk", 390);
	}

	private void setAggressiveValues(Player p) {
		p.setColorValue("purple", 2.5, 1.5, 2.25, 2);
		p.setColorValue("light blue", 2.5, 1.5, 2.25, 2);
		p.setColorValue("pink", 2.5, 1.5, 2.25, 2);
		p.setColorValue("orange", 2.5, 1.5,2.25, 2);
		p.setColorValue("red", 2.5, 1.5, 2.25, 2);
		p.setColorValue("yellow", 2.5, 1.5, 2.25, 2);
		p.setColorValue("green", 2.5, 1.5, 2.25, 2);
		p.setColorValue("dark blue", 2.5, 1.5, 2.25, 2);

		p.setJailWait(1);
		p.setJailRich(1);
		p.setJailPoor(1);
		p.setMinBuildCash(0);
		p.setMinBuyCash(0);
		p.setMinUnmortgageCash(0);
		p.setTradingFear(1.1);
		p.setLiquidity(10);
		p.setBuildAggression(Aggression.AGGRESSIVE);
		p.setBuildingEvenness(Balance.EVEN);
		p.setHouseSelling(Amount.FEWER);
		
		p.setPropertyValue("mediterranean avenue", 70);
		p.setPropertyValue("baltic avenue", 70);
		p.setPropertyValue("reading railroad", 210);
		p.setPropertyValue("oriental avenue", 110);
		p.setPropertyValue("vermont avenue", 110);
		p.setPropertyValue("connecticut avenue", 130);
		p.setPropertyValue("st. charles place", 150);
		p.setPropertyValue("electric company", 160);
		p.setPropertyValue("states avenue", 150);
		p.setPropertyValue("virginia avenue", 170);
		p.setPropertyValue("pennsylvania railroad", 210);
		p.setPropertyValue("st. james place", 190);
		p.setPropertyValue("tennessee avenue", 190);	
		p.setPropertyValue("new york avenue", 210);	
		p.setPropertyValue("kentucky avenue", 230);	
		p.setPropertyValue("indiana avenue", 230);	
		p.setPropertyValue("illinois avenue", 250);		
		p.setPropertyValue("b and o railroad",210);		
		p.setPropertyValue("atlantic avenue", 270);		
		p.setPropertyValue("ventnor avenue", 270);		
		p.setPropertyValue("water works",160);		
		p.setPropertyValue("marvin gardens", 290);	
		p.setPropertyValue("pacific avenue", 310);
		p.setPropertyValue("north carolina avenue", 310);
		p.setPropertyValue("pennsylvania avenue", 330);
		p.setPropertyValue("short line", 210);
		p.setPropertyValue("park place", 360);
		p.setPropertyValue("boardwalk", 410);
	}

	private void setBalancedValues(Player p) {
		p.setColorValue("purple", 1.7, 1.2, 1.5, 1.3);
		p.setColorValue("light blue", 1.2, 1.2, 1.5, 1.3);
		p.setColorValue("pink", 1.7, 1.2, 1.5, 1.3);
		p.setColorValue("orange", 1.7, 1.2, 1.5, 1.3);
		p.setColorValue("red", 1.7, 1.2, 1.5, 1.3);
		p.setColorValue("yellow", 1.7, 1.2, 1.5, 1.3);
		p.setColorValue("green", 1.7, 1.2, 1.5, 1.3);
		p.setColorValue("dark blue", 1.7, 1.2, 1.5, 1.3);

		p.setJailWait(2);
		p.setJailRich(2);
		p.setJailPoor(2);
		p.setMinBuildCash(200);
		p.setMinBuyCash(150);
		p.setMinUnmortgageCash(300);
		p.setTradingFear(1.3);
		p.setLiquidity(6);

	}

	@Override
	public String toString() {
		return _name;
	}

}
