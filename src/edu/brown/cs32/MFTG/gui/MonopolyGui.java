package edu.brown.cs32.MFTG.gui;

import java.awt.Dimension;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import edu.brown.cs32.MFTG.gui.gameboard.Board;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.networking.ProfileManager;
import edu.brown.cs32.MFTG.tournament.Client;
import edu.brown.cs32.MFTG.tournament.Profile;
import edu.brown.cs32.MFTG.tournament.Record;

public class MonopolyGui extends JFrame{
	private JPanel _currentPanel;
	private HashMap<String, JPanel> _panels;
	private EndGamePanel _end;
	private ChooseProfilePanel _choose;
	private Profile _currentProfile;
	private ProfileManager _profileManager;
	private SettingsPanel _settings;
	private RecordsPanel _records;
	private Client _client;
	private EmptyMenuBar _empty;
	private List<String> _outSongNames, _inSongNames;
	private int _currentOutSongNumber, _currentInSongNumber;
	private Music _currentSong;

	public MonopolyGui(Client client) {
		super("Monopoly for the GODS");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					try {
						UIManager.setLookAndFeel(info.getClassName());
					} catch (ClassNotFoundException | InstantiationException
							| IllegalAccessException
							| UnsupportedLookAndFeelException e) {
					}
					break;
				}
			}
		
		Dimension dimension = new Dimension(Constants.FULL_WIDTH, Constants.FULL_FRAME_HEIGHT);
		this.setSize(dimension);		
		this.setPreferredSize(dimension);
		this.setMaximumSize(dimension);
		this.setMinimumSize(dimension);
		
		this.setResizable(false);
		
		_panels = new HashMap<>();
		_profileManager = new ProfileManager();

		_client = client;

		GreetingPanel greet = new GreetingPanel(this);
		_panels.put("greet", greet);
		_settings = new SettingsPanel(this);
		_panels.put("settings", _settings);
		_records = new RecordsPanel(this);
		_panels.put("records", _records);
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
		
		_empty = new EmptyMenuBar();
		
		/* DO NOT ACTUALLY USE THE BOARD PLEASE */
		Board board;
		try {
			board = new Board(1, this, null);
			
			_currentPanel=greet;
			this.add(_currentPanel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setJMenuBar(_empty);
		
		this.switchPanels("settings");
		
		this.pack();
		this.setVisible(true);
		
		_outSongNames = new ArrayList<>(2);
		_outSongNames.add("music/NoChurchInTheWild.mp3");
		_outSongNames.add("music/SwaggerLikeUs.mp3");
		
		_inSongNames = new ArrayList<>(2);
		_inSongNames.add("music/Asutobots.mp3");		
		_currentOutSongNumber=(int) Math.floor(Math.random()*_outSongNames.size());
		_currentInSongNumber=(int) Math.floor(Math.random()*_inSongNames.size());

		_currentSong=new Music(_outSongNames.get(_currentOutSongNumber), this, false);
		_currentSong.play();
	}
	
	/**
	 * stops music
	 */
	public void stopMusic(){
		_currentSong.close();
	}
	
	/**
	 * plays the next song out of game
	 */
	public void playNextOutOfGameSong(){
		_currentSong.close();
		_currentOutSongNumber = (_currentOutSongNumber+1)%_outSongNames.size();
		String newSongName = _outSongNames.get(_currentOutSongNumber);
		_currentSong = new Music(newSongName, this, false);
		_currentSong.play();
	}
	
	/**
	 * play song in game
	 */
	public void playNextInGameSong(){
		_currentSong.close();
		_currentInSongNumber = (_currentInSongNumber+1)%_inSongNames.size();
		String newSongName = _inSongNames.get(_currentInSongNumber);
		_currentSong = new Music(newSongName, this, true);
		_currentSong.play();
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
			_choose.resetProfileList();
			_choose.giveFocusToList();
		}
		else if(_currentPanel==_settings) {
			_settings.resetProfileList();
			_settings.giveFocusToList();
		}
		else if(_currentPanel==_records) {
			_records.resetProfileList();
			_records.giveFocusToList();
		}
		if(panel.equals("board")==false&&panel.equals("settingsboard")==false) {
			this.setJMenuBar(_empty);
		}
		revalidate();
		repaint();
	}

	/**
	 * 
	 * @return set of profiles names
	 */
	public Set<String> getProfileNames() {
		return _profileManager.getProfileNames();
	}
	
	/**
	 * gets a profile
	 * @param name
	 * @return profile
	 */
	public Profile getProfile(String name) {
		return _profileManager.getProfile(name);
	}
	
	/**
	 * 
	 * @param profileName
	 * @return record for profile
	 */
	public Record getRecord(String profileName) {
		return (_profileManager.getProfile(profileName)==null)? null:_profileManager.getProfile(profileName).getRecord();
	}
	
	/**
	 * removes a profile
	 * @param profileName
	 */
	public void removeProfile(String profileName) {
		_profileManager.deleteProfile(profileName);
	}
	
	/**
	 * 
	 * @return set of player names
	 */
	public Set<String> getPlayerNames(String profile) {
		return _profileManager.getProfile(profile).getPlayerNames();
	}
	
	/**
	 * 
	 * @return set of settings names
	 */
	public Set<String> getSettingsNames(String profile) {
		return _profileManager.getProfile(profile).getSettingsNames();
	}
	
	/**
	 * 
	 * @param profile
	 * @param playerName
	 * @return player
	 */
	public Player getPlayer(String profile, String playerName) {
		return _profileManager.getProfile(profile).getPlayer(playerName);
	}

	/**
	 * sets current profile
	 * @param profileName
	 */
	public void setCurrentProfile(String profileName) {
		_currentProfile = _profileManager.getProfile(profileName);
	}
	
	/**
	 * 
	 * @return currentProfile
	 */
	public Profile getCurrentProfile() {
		return _currentProfile;
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

	public void createBoard(int id, Profile profile) {
		Board board;
		try {
			board = new Board(id, this, profile);
			_panels.put("board", board);
			switchPanels("board");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createSettingsBoard(Profile profile) {
		SettingsBoard board;
		try {
			board = new SettingsBoard(this, profile);
			_panels.put("settingsboard", board);
			switchPanels("settingsboard");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Client getClient(){
		return _client;
	}
}
