package edu.brown.cs32.MFTG.mftg;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import edu.brown.cs32.MFTG.gui.Board;
import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.CreateGamePanel;
import edu.brown.cs32.MFTG.gui.EndGamePanel;
import edu.brown.cs32.MFTG.gui.GameLobbyPanel;
import edu.brown.cs32.MFTG.gui.GreetingPanel;
import edu.brown.cs32.MFTG.gui.JoinGamePanel;
import edu.brown.cs32.MFTG.gui.Music;
import edu.brown.cs32.MFTG.networking.ProfileManager;
import edu.brown.cs32.MFTG.tournament.Profile;

public class Main extends JFrame{
	private JPanel _currentPanel;
	private HashMap<String, JPanel> _panels;
	private EndGamePanel _end;
	private Profile _currentProfile;
	private ProfileManager _profileManager;
	public Main() {
		super("Monopoly for the GODS");

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		_panels = new HashMap<>(7);
		_profileManager = new ProfileManager();

		GreetingPanel greet = new GreetingPanel(this);
		_panels.put("greet", greet);
		GameLobbyPanel lobby = new GameLobbyPanel(this);
		_panels.put("lobby", lobby);
		CreateGamePanel create = new CreateGamePanel(this);
		_panels.put("create", create);
		JoinGamePanel join = new JoinGamePanel(this);
		_panels.put("join", join);
		_end = new EndGamePanel(this);
		_panels.put("end", _end);
		/* DO NOT USE THE BOARD RIGHT NOW! */
		//Board board = new Board();
		//_panels.put("board", board);
		
		this.setSize(9*Constants.WIDTH + 2*Constants.HEIGHT, 9*Constants.WIDTH + 2*Constants.HEIGHT);
		this.setResizable(false);
		
		//ProfitGraph graph = new ProfitGraph();
		//this.add(graph);
		
		//ColorBoard board = new ColorBoard();
		//this.add(board);
		
		_currentPanel=_end;
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
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
			new Main();
		} catch (UnsupportedLookAndFeelException|ClassNotFoundException|InstantiationException|IllegalAccessException e) {}


	}

}
