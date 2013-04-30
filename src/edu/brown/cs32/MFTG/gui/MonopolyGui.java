package edu.brown.cs32.MFTG.gui;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import edu.brown.cs32.MFTG.gui.gameboard.Board;
import edu.brown.cs32.MFTG.networking.ProfileManager;
import edu.brown.cs32.MFTG.tournament.Client;
import edu.brown.cs32.MFTG.tournament.Profile;

public class MonopolyGui extends JFrame{
	private JPanel _currentPanel;
	private HashMap<String, JPanel> _panels;
	private EndGamePanel _end;
	private ChooseProfilePanel _choose;
	private Profile _currentProfile;
	private ProfileManager _profileManager;
	private Client _module;
	
	public MonopolyGui(Client module) {
		super("Monopoly for the GODS");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		_panels = new HashMap<>(7);
		_profileManager = new ProfileManager();
		
		_module = module;

		GreetingPanel greet = new GreetingPanel(this);
		_panels.put("greet", greet);
		SettingsPanel settings = new SettingsPanel(this);
		_panels.put("settings", settings);
		GameLobbyPanel lobby = new GameLobbyPanel(this);
		_panels.put("lobby", lobby);
		_choose = new ChooseProfilePanel(this);
		_panels.put("choose", _choose);
		CreateGamePanel create = new CreateGamePanel(this);
		_panels.put("create", create);
		JoinGamePanel join = new JoinGamePanel(this);
		_panels.put("join", join);
		_end = new EndGamePanel(this);
		_panels.put("end", _end);

		
		this.setSize(9*Constants.WIDTH + 2*Constants.HEIGHT, 9*Constants.WIDTH + 2*Constants.HEIGHT);
		this.setResizable(false);
		
		_currentPanel= lobby;
		this.add(_currentPanel);
		
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * sets winner of game
	 * @param didWin
	 * @param names
	 */
	public void setWinner(boolean didWin, String...names) {
		_end.setWinner(didWin, names);
	}
	/**
	 * This method switches between panels
	 * @param panel
	 */
	public void switchPanels(String panel) {
		remove(_currentPanel);
		_currentPanel=_panels.get(panel);
		add(_currentPanel);
		if(_currentPanel==_choose) {
			_choose.giveFocusToList();
		}
		_currentPanel.repaint();
	}
	
	/**
	 * 
	 * @return profile manager
	 */
	public Set<String> getProfileNames() {
		return _profileManager.getProfileNames();
	}
	
	/**
	 * sets current profile
	 * @param profileName
	 */
	public void setCurrentProfile(String profileName) {
		_currentProfile = _profileManager.getProfile(profileName);
	}
	
	/**
	 * adds a profile
	 * @param name
	 * @return name
	 */
	public String addProfile(String name) {
		boolean done=_profileManager.addProfile(name, new Profile(name));
		if(done==false) {
			return null;
		}
		return name;
	}
	
	public Board getBoard(){
		return (Board) _panels.get("board"); //TODO get rid of casting
	}
	
	public void createBoard(int id){
		try {
		Board board = new Board(id);
		_panels.put("board",  board);
		switchPanels("board");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Client getModule(){
		return _module;
	}
}
