package edu.brown.cs32.MFTG.gui.gameboard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.ColorProperties;
import edu.brown.cs32.MFTG.gui.Constants.Colors;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.Properties;
import edu.brown.cs32.MFTG.gui.Constants.Railroads;
import edu.brown.cs32.MFTG.gui.Constants.StaticProperties;
import edu.brown.cs32.MFTG.gui.Constants.Utilities;
import edu.brown.cs32.MFTG.gui.Constants.View;
import edu.brown.cs32.MFTG.gui.MonopolyGui;
import edu.brown.cs32.MFTG.gui.center.Center;
import edu.brown.cs32.MFTG.gui.properties.AggregateColorProperty;
import edu.brown.cs32.MFTG.gui.properties.AggregateUtilityProperty;
import edu.brown.cs32.MFTG.gui.properties.CornerProperty;
import edu.brown.cs32.MFTG.gui.properties.Jail;
import edu.brown.cs32.MFTG.gui.properties.MyColorPropertyPanel;
import edu.brown.cs32.MFTG.gui.properties.MyUtilityProperty;
import edu.brown.cs32.MFTG.gui.properties.PropertyPanel;
import edu.brown.cs32.MFTG.gui.properties.StaticProperty;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.tournament.Client;
import edu.brown.cs32.MFTG.tournament.Profile;
import edu.brown.cs32.MFTG.tournament.data.PlayerWealthDataReport;
import edu.brown.cs32.MFTG.tournament.data.PropertyDataReport;

public class Board extends JPanel {
	
	protected Set<ColorGroup> _colorGroups = new HashSet<>();
	protected Set<Railroad> _railroads = new HashSet<>();
	protected Jail _jail;
	private JMenuBar _menu;
	protected Center _center;
	protected int _id;
	private Player _player = null;
	protected MonopolyGui _main;
	protected Profile _profile;
	private Client _client;
	
	public Board (int id, MonopolyGui main, Profile profile, Client client) throws IOException {
		super();
		//_menu = menu;
		_main=main;
		_id = id;
		_client = client;
		_profile = profile;
		
		/* Set the dimension */
		Dimension dimension = new Dimension(Constants.FULL_WIDTH, Constants.FULL_PANEL_HEIGHT);
		this.setMaximumSize(dimension);
		this.setMinimumSize(dimension);
		this.setPreferredSize(dimension);
		this.setSize(dimension);
		this.setLocation(0, 0);
		
		/* Set up the layout */
		BorderLayout layout = new BorderLayout(0, 0);
		this.setLayout(layout);
		
		initializeTop();
		initializeBottom();
		initializeLeft();
		initializeRight();
		initializeCenter();
		
		this.setToolTipText(null);
	
		initializeMenu();
		
		this.setVisible(true);
		this.repaint();
	}
	
	public void removeSetHeuristicsButton() {
		_center.removeSetHeuristicsButton();
	}
	
	public void switchToEndGameMenu() {
		JMenuBar menu = new EndGameMenu(this,_main, _profile);
		_main.setJMenuBar(menu);
	}
	
	public void enableToolTips(boolean enabled) {
		ToolTipManager.sharedInstance().setEnabled(enabled);
	}
	
	public void initializeMenu () {
		JMenuBar menu = new InGameMenu(this,_main, _profile);
		_main.setJMenuBar(menu);
	}
	
	public void initializeTop () throws IOException {
		JPanel top = new JPanel();
		Dimension dimension = new Dimension(Constants.FULL_WIDTH, Constants.HEIGHT);
		top.setSize(dimension);
		top.setPreferredSize(dimension);
		top.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridy = 0;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = 0;
		c.weightx = Constants.HEIGHT;
		top.add(new CornerProperty(StaticProperties.FREE_PARKING));
		
		c.gridx = 1;
		c.weightx = 4*Constants.WIDTH;
		List<PropertyPanel> myProperties = new ArrayList<>();
		List<PropertyPanel> aggregateProperties = new ArrayList<>();
		List<Properties> properties = new ArrayList<>();
		myProperties.add(new MyColorPropertyPanel(ColorProperties.KENTUCKY_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.KENTUCKY_AVENUE));
		properties.add(ColorProperties.KENTUCKY_AVENUE);
		myProperties.add(new StaticProperty(StaticProperties.BLUE_CHANCE));
		aggregateProperties.add(new StaticProperty(StaticProperties.BLUE_CHANCE));
		properties.add(StaticProperties.BLUE_CHANCE);
		myProperties.add(new MyColorPropertyPanel(ColorProperties.INDIANA_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.INDIANA_AVENUE));
		properties.add(ColorProperties.INDIANA_AVENUE);
		myProperties.add(new MyColorPropertyPanel(ColorProperties.ILLINOIS_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.ILLINOIS_AVENUE));
		properties.add(ColorProperties.ILLINOIS_AVENUE);
		ColorGroup red = new ColorGroup(Orientation.UP, properties, myProperties, aggregateProperties, new ColorBlock(Colors.RED));
		top.add(red, c);
		_colorGroups.add(red);
		
		c.gridx = 2;
		c.weightx = Constants.WIDTH;
		Railroad bAndO = new Railroad(Railroads.B_AND_O_RAILROAD);
		top.add(bAndO, c);
		_railroads.add(bAndO);
		
		c.gridx = 3;
		c.weightx = 4*Constants.WIDTH;
		myProperties = new ArrayList<>();
		aggregateProperties = new ArrayList<>();
		properties = new ArrayList<>();
		myProperties.add(new MyColorPropertyPanel(ColorProperties.ATLANTIC_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.ATLANTIC_AVENUE));
		properties.add(ColorProperties.ATLANTIC_AVENUE);
		myProperties.add(new MyColorPropertyPanel(ColorProperties.VENTNOR_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.VENTNOR_AVENUE));
		properties.add(ColorProperties.VENTNOR_AVENUE);
		myProperties.add(new MyUtilityProperty(Utilities.WATER_WORKS));
		aggregateProperties.add(new AggregateUtilityProperty(Utilities.WATER_WORKS));
		properties.add(Utilities.WATER_WORKS);
		myProperties.add(new MyColorPropertyPanel(ColorProperties.MARVIN_GARDENS));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.MARVIN_GARDENS));
		properties.add(ColorProperties.MARVIN_GARDENS);
		ColorGroup yellow = new ColorGroup(Orientation.UP, properties, myProperties, aggregateProperties, new ColorBlock(Colors.YELLOW));
		top.add(yellow);
		_colorGroups.add(yellow);
		
		c.gridx = 4;
		c.weightx = Constants.HEIGHT;
		top.add(new CornerProperty(StaticProperties.GO_TO_JAIL), c);
		
		this.add(top, BorderLayout.NORTH);
	}
	
	public void initializeBottom () throws IOException {
		JPanel bottom = new JPanel();
		Dimension dimension = new Dimension(Constants.FULL_WIDTH, Constants.HEIGHT);
		bottom.setSize(dimension);
		bottom.setPreferredSize(dimension);
		
		bottom.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridy = 0;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = 0;
		c.weightx = Constants.HEIGHT;
		_jail = new Jail();
		bottom.add(_jail, c);
		
		c.gridx = 1;
		c.weightx = 4*Constants.WIDTH;
		List<PropertyPanel> myProperties = new ArrayList<>();
		List<PropertyPanel> aggregateProperties = new ArrayList<>();
		List<Properties> properties = new ArrayList<>();
		myProperties.add(new MyColorPropertyPanel(ColorProperties.CONNECTICUT_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.CONNECTICUT_AVENUE));
		properties.add(ColorProperties.CONNECTICUT_AVENUE);
		myProperties.add(new MyColorPropertyPanel(ColorProperties.VERMONT_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.VERMONT_AVENUE));
		properties.add(ColorProperties.VERMONT_AVENUE);
		myProperties.add(new StaticProperty(StaticProperties.PINK_CHANCE));
		aggregateProperties.add(new StaticProperty(StaticProperties.PINK_CHANCE));
		properties.add(StaticProperties.PINK_CHANCE);
		myProperties.add(new MyColorPropertyPanel(ColorProperties.ORIENTAL_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.ORIENTAL_AVENUE));
		properties.add(ColorProperties.ORIENTAL_AVENUE);
		ColorGroup blue = new ColorGroup(Orientation.DOWN, properties, myProperties, aggregateProperties, new ColorBlock(Colors.LIGHT_BLUE));
		bottom.add(blue, c);
		_colorGroups.add(blue);
		
		c.gridx = 2;
		c.weightx = Constants.WIDTH;
		Railroad reading = new Railroad(Railroads.READING_RAILROAD);
		bottom.add(reading, c);
		_railroads.add(reading);
		
		c.gridx = 3;
		c.weightx = 4*Constants.WIDTH;
		myProperties = new ArrayList<>();
		aggregateProperties = new ArrayList<>();
		properties = new ArrayList<>();
		myProperties.add(new StaticProperty(StaticProperties.INCOME_TAX));
		aggregateProperties.add(new StaticProperty(StaticProperties.INCOME_TAX));
		properties.add(StaticProperties.INCOME_TAX);
		myProperties.add(new MyColorPropertyPanel(ColorProperties.BALTIC_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.BALTIC_AVENUE));
		properties.add(ColorProperties.BALTIC_AVENUE);
		myProperties.add(new StaticProperty(StaticProperties.COMMUNITY_CHEST_DOWN));
		aggregateProperties.add(new StaticProperty(StaticProperties.COMMUNITY_CHEST_DOWN));
		properties.add(StaticProperties.COMMUNITY_CHEST_DOWN);
		myProperties.add(new MyColorPropertyPanel(ColorProperties.MEDITERRANEAN_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.MEDITERRANEAN_AVENUE));
		properties.add(ColorProperties.MEDITERRANEAN_AVENUE);
		ColorGroup purple = new ColorGroup(Orientation.UP, properties, myProperties, aggregateProperties, new ColorBlock(Colors.PURPLE));
		bottom.add(purple, c);
		_colorGroups.add(purple);
		
		c.gridx = 4;
		c.weightx = Constants.HEIGHT;
		bottom.add(new CornerProperty(StaticProperties.GO), c);
		
		this.add(bottom, BorderLayout.SOUTH);
	}
	
	public void initializeLeft () throws IOException {
		JPanel left = new JPanel();
		Dimension dimension = new Dimension(Constants.HEIGHT, 9*Constants.WIDTH);
		left.setPreferredSize(dimension);
		left.setSize(dimension);
		
		left.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.ipadx = 0;
		c.ipady = 0;
		
		c.gridy = 0;
		c.weighty = 4*Constants.WIDTH;
		List<PropertyPanel> myProperties = new ArrayList<>();
		List<PropertyPanel> aggregateProperties = new ArrayList<>();
		List<Properties> properties = new ArrayList<>();
		myProperties.add(new MyColorPropertyPanel(ColorProperties.NEW_YORK_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.NEW_YORK_AVENUE));
		properties.add(ColorProperties.NEW_YORK_AVENUE);
		myProperties.add(new MyColorPropertyPanel(ColorProperties.TENNESSEE_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.TENNESSEE_AVENUE));
		properties.add(ColorProperties.TENNESSEE_AVENUE);
		myProperties.add(new StaticProperty(StaticProperties.COMMUNITY_CHEST_LEFT));
		aggregateProperties.add(new StaticProperty(StaticProperties.COMMUNITY_CHEST_LEFT));
		properties.add(StaticProperties.COMMUNITY_CHEST_LEFT);
		myProperties.add(new MyColorPropertyPanel(ColorProperties.ST_JAMES_PLACE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.ST_JAMES_PLACE));
		properties.add(ColorProperties.ST_JAMES_PLACE);
		ColorGroup orange = new ColorGroup(Orientation.LEFT, properties, myProperties, aggregateProperties, new ColorBlock(Colors.ORANGE));
		left.add(orange, c);
		_colorGroups.add(orange);
		
		c.gridy = 1;
		c.weighty = Constants.WIDTH;
		Railroad pennsylvania = new Railroad(Railroads.PENNSYLVANIA_RAILROAD);
		left.add(pennsylvania, c);
		_railroads.add(pennsylvania);
		
		c.gridy = 2;
		c.weighty = 4*Constants.WIDTH;
		myProperties = new ArrayList<>();
		aggregateProperties = new ArrayList<>();
		properties = new ArrayList<>();
		myProperties.add(new MyColorPropertyPanel(ColorProperties.VIRGINIA_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.VIRGINIA_AVENUE));
		properties.add(ColorProperties.VIRGINIA_AVENUE);
		myProperties.add(new MyColorPropertyPanel(ColorProperties.STATES_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.STATES_AVENUE));
		properties.add(ColorProperties.STATES_AVENUE);
		myProperties.add(new MyUtilityProperty(Utilities.ELECTRIC_COMPANY));
		aggregateProperties.add(new AggregateUtilityProperty(Utilities.ELECTRIC_COMPANY));
		properties.add(Utilities.ELECTRIC_COMPANY);
		myProperties.add(new MyColorPropertyPanel(ColorProperties.ST_CHARLES_PLACE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.ST_CHARLES_PLACE));
		properties.add(ColorProperties.ST_CHARLES_PLACE);
		ColorGroup pink = new ColorGroup(Orientation.LEFT, properties, myProperties, aggregateProperties, new ColorBlock(Colors.PINK));
		left.add(pink, c);
		_colorGroups.add(pink);
		
		this.add(left, BorderLayout.WEST);
	}
	
	public void initializeRight () throws IOException {
		JPanel right = new JPanel();
		Dimension dimension = new Dimension(Constants.HEIGHT, 9*Constants.WIDTH);
		right.setPreferredSize(dimension);
		right.setSize(dimension);
		
		right.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.ipadx = 0;
		c.ipady = 0;
		
		c.gridy = 0;
		c.weighty = 4*Constants.WIDTH;
		List<PropertyPanel> myProperties = new ArrayList<>();
		List<PropertyPanel> aggregateProperties = new ArrayList<>();
		List<Properties> properties = new ArrayList<>();
		myProperties.add(new MyColorPropertyPanel(ColorProperties.PACIFIC_GARDEN));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.PACIFIC_GARDEN));
		properties.add(ColorProperties.PACIFIC_GARDEN);
		myProperties.add(new MyColorPropertyPanel(ColorProperties.NORTH_CAROLINA_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.NORTH_CAROLINA_AVENUE));
		properties.add(ColorProperties.NORTH_CAROLINA_AVENUE);
		myProperties.add(new StaticProperty(StaticProperties.COMMUNITY_CHEST_RIGHT));
		aggregateProperties.add(new StaticProperty(StaticProperties.COMMUNITY_CHEST_RIGHT));
		properties.add(StaticProperties.COMMUNITY_CHEST_RIGHT);
		myProperties.add(new MyColorPropertyPanel(ColorProperties.PENNSYLVANIA_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.PENNSYLVANIA_AVENUE));
		properties.add(ColorProperties.PENNSYLVANIA_AVENUE);
		ColorGroup green = new ColorGroup(Orientation.RIGHT, properties, myProperties, aggregateProperties, new ColorBlock(Colors.GREEN));
		right.add(green, c);
		_colorGroups.add(green);
		
		c.gridy = 1;
		c.weighty = Constants.WIDTH;
		Railroad shortLine = new Railroad(Railroads.SHORT_LINE);
		right.add(shortLine, c);
		_railroads.add(shortLine);
		
		c.gridy = 2;
		c.weighty = 4*Constants.WIDTH;
		myProperties = new ArrayList<>();
		aggregateProperties = new ArrayList<>();
		properties = new ArrayList<>();
		myProperties.add(new StaticProperty(StaticProperties.RED_CHANCE));
		aggregateProperties.add(new StaticProperty(StaticProperties.RED_CHANCE));
		properties.add(StaticProperties.RED_CHANCE);
		myProperties.add(new MyColorPropertyPanel(ColorProperties.PARK_PLACE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.PARK_PLACE));
		properties.add(ColorProperties.PARK_PLACE);
		myProperties.add(new StaticProperty(StaticProperties.LUXURY_TAX));
		aggregateProperties.add(new StaticProperty(StaticProperties.LUXURY_TAX));
		properties.add(StaticProperties.LUXURY_TAX);
		myProperties.add(new MyColorPropertyPanel(ColorProperties.BOARDWALK));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.BOARDWALK));
		properties.add(ColorProperties.BOARDWALK);
		ColorGroup blue = new ColorGroup(Orientation.RIGHT, properties, myProperties, aggregateProperties, new ColorBlock(Colors.DARK_BLUE));
		right.add(blue, c);
		_colorGroups.add(blue);
		
		this.add(right, BorderLayout.EAST);
	}
	
	public void initializeCenter() {
		_center = new Center(this, _id);
		this.add(_center, BorderLayout.CENTER);
	}
	
	public void newRound(int time) {
		//time in seconds
		_center.reenableSetHeuristics(time);
		_center.enableGraph();
		_player = null;
	}
	
	public Player getHeuristics () {		
		Player player = new Player(_id,_profile._name);
		
		HashMap<String, Integer> propertyValues = new HashMap<>();
		HashMap<String, Double[]> colorValues = new HashMap<>();
		for(ColorGroup c: _colorGroups) {
			HashMap<String, Integer> properties = c.getPropertyValues();
			propertyValues.putAll(properties);
			Double[] colors = c.getColorValues();
			if(c.getName()!=null)
				colorValues.put(c.getName(), colors);
		}
		for(Railroad r: _railroads) {
			if(r.getLowercaseName()!=null)
				propertyValues.put(r.getLowercaseName(), r.getValue());
		}
		
		player.setPropertyValues(propertyValues);
		player.setColorValues(colorValues);
		
		List<Double> sliders = _center.getSliderInfo();
		player.setLiquidity(sliders.get(0));
		player.setTimeChange(sliders.get(1));
		player.setTradingFear(sliders.get(2));
		
		List<Integer> minCash = _center.getMinCash();
		player.setMinBuyCash(minCash.get(0));
		player.setMinBuildCash(minCash.get(1));
		player.setMinUnmortgageCash(minCash.get(2));
		
		List<Integer> waits = _jail.getWait();
		player.setJailWait(waits.get(0));
		player.setJailPoor(waits.get(1));
		player.setJailRich(waits.get(2));
		
		_center.setButtonChoices(player);
		
		return player;
	}
	
	public Player getPlayer () {
		_center.disableGraph();
		if(_player != null) {
			return _player;
		}
		Player player =  getHeuristics();
		return player;
	}
	
	public void setPlayerSpecificPropertyData(Map<String, PropertyDataReport> data) {
		for(ColorGroup colorGroup: _colorGroups) {
			Set<String> names = colorGroup.getNames();
			for(String name: names) {
				if(data.containsKey(name)) {
					colorGroup.setMyData(name, data.get(name));
				}
			}
		}
		
		for(Railroad railroad: _railroads) {
			String name = railroad.getLowercaseName();
			if(data.containsKey(name)) {
				railroad.setMyData(data.get(name));
			}
		}
	}
	
	public void setPropertyData(Map<String, PropertyDataReport> data) {
		for(ColorGroup colorGroup: _colorGroups) {
			Set<String> names = colorGroup.getNames();
			for(String name: names) {
				if(data.containsKey(name)) {
					colorGroup.setAggregateData(name, data.get(name));
				}
			}
		}
		
		for(Railroad railroad: _railroads) {
			String name = railroad.getLowercaseName();
			if(data.containsKey(name)) {
				railroad.setAggregateData(data.get(name));
			}
		}
	}
	
	public void setWinnerData(Map<Integer, Double> data, Map<Integer, String> names){
		_center.setWinnerData(data, names);
	}

	
	public void sendHeuristics() {
		_client.finishRespondToGetPlayer();
	}
	
	public void setWealthData(List<PlayerWealthDataReport> data) {
		_center.setWealthData(data);
		
		this.validate();
		this.updateUI();
		this.repaint();
	}
	
	public void setHeuristics (Player player) {
		HashMap<String, Integer> propertyValues = player.getPropertyValues();
		HashMap<String, Double[]> colorValues = player.getColorValues();
		
		for(ColorGroup colorGroup: _colorGroups) {
			colorGroup.setPropertyValues(propertyValues);
			colorGroup.setColorValue(colorValues);
		}
		
		for(Railroad railroad: _railroads) {
			railroad.setPropertyValues(propertyValues);
		}
		
		_center.setSliderValues(player.getLiquidity(), player.getTimeChange(), player.getTradingFear());
		_center.setMinCashValues(player.getMinBuyCash(), player.getMinBuildCash(), player.getMinUnmortgageCash());
		_center.setButtonValues(player.getBuildingChoice(), player.getBuildingEvenness(), player.getBuildAggression(), player.getSellingChoice(), player.getHouseSelling(), player.getMortgageChoice());
		
		_jail.setWaits(player.getJailWait(), player.getJailPoor(), player.getJailRich());
	}
	
	
/* ----------------------------------------------------------------------------
 * ----------------------------- MENU LISTENERS -------------------------------
 * ----------------------------------------------------------------------------
 */
	

	public void setView(View view) {
		_center.setView(view);
		for(ColorGroup c: _colorGroups) {
			c.setView(view);
		}
		for(Railroad r: _railroads) {
			r.setView(view);
		}
		this.repaint();
	}

}
