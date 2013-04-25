package edu.brown.cs32.MFTG.mftg;

import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import edu.brown.cs32.MFTG.gui.Board;
import edu.brown.cs32.MFTG.gui.CreateGamePanel;
import edu.brown.cs32.MFTG.gui.GameLobbyPanel;
import edu.brown.cs32.MFTG.gui.GreetingPanel;

public class Main extends JFrame{
	private JPanel _currentPanel;
	private HashMap<String, JPanel> _panels;
	public Main() {
		super("Monopoly");

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		_panels = new HashMap<>(5);
		//Board board = new Board();
		//this.add(board);
		GreetingPanel greet = new GreetingPanel(this);
		_panels.put("greet", greet);
		GameLobbyPanel lobby = new GameLobbyPanel(this);
		_panels.put("lobby", lobby);
		CreateGamePanel create = new CreateGamePanel();
		_panels.put("create", create);
		
		_currentPanel=greet;
		this.add(_currentPanel);
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * This method switches between panels
	 * @param panel
	 */
	public void switchPanels(String panel) {
		System.out.println("Switch");
		remove(_currentPanel);
		_currentPanel=_panels.get(panel);
		add(_currentPanel);
		_currentPanel.repaint();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new Main();
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (UnsupportedLookAndFeelException|ClassNotFoundException|InstantiationException|IllegalAccessException e) {}


	}

}
