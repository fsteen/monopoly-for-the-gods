package edu.brown.cs32.MFTG.gui.gameboard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import edu.brown.cs32.MFTG.gui.Constants.View;
import edu.brown.cs32.MFTG.gui.MonopolyGui;
import edu.brown.cs32.MFTG.gui.center.ViewListener;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.tournament.Profile;

public class BoardMenu extends JMenuBar {
	private Board _board;
	private MonopolyGui _main;
	protected JMenu _players;
	protected List<JMenuItem> _playerItems;
	protected String _currentPlayer;
	protected Profile _profile;
	public BoardMenu(Board board, MonopolyGui main, Profile profile) {
		super();
		_board=board;
		_main=main;
		_profile=profile;

		JMenu boardView = new JMenu("Board View");

		JMenuItem myProperty = new JMenuItem("My Properties");
		myProperty.addActionListener(new ViewListener(View.ME,_board));
		boardView.add(myProperty);

		JMenuItem aggregateProperty = new JMenuItem("Aggregate Properties");
		aggregateProperty.addActionListener(new ViewListener(View.AGGREGATE,_board));
		boardView.add(aggregateProperty);

		JMenuItem colorGroup = new JMenuItem("Color Groups");
		colorGroup.addActionListener(new ViewListener(View.COLOR,_board));
		boardView.add(colorGroup);


		_players = new JMenu("Players");

		_playerItems = new ArrayList<>();
		if(_profile!=null) {
			for(String p: _profile.getPlayerNames()) {
				JMenuItem player = new JMenuItem(p);
				_playerItems.add(player);
				player.addActionListener(new PlayerListener(p));
				_players.add(player);
			}
		}
		JMenuItem newplayer = new JMenuItem("Create New Player");
		_playerItems.add(newplayer);
		newplayer.addActionListener(new NewPlayerListener());
		_players.add(newplayer);
		
		_currentPlayer=_playerItems.get(0).getText();
		_players.setText(_currentPlayer);
		_board.setHeuristics(_profile.getPlayer(_currentPlayer));

		JButton save = new JButton("Save");
		save.addActionListener(new SaveListener());

		add(boardView);
		add(_players);
		add(save);

	}
	
	public void resetPlayerMenu() {
		_players.removeAll();
		_playerItems.clear();
		
		if(_profile!=null) {
			for(String p: _profile.getPlayerNames()) {
				JMenuItem player = new JMenuItem(p);
				_playerItems.add(player);
				player.addActionListener(new PlayerListener(p));
				_players.add(player);
			}
		}
		JMenuItem newplayer = new JMenuItem("Create New Player");
		_playerItems.add(newplayer);
		newplayer.addActionListener(new NewPlayerListener());
		_players.add(newplayer);
		
		_currentPlayer=_playerItems.get(0).getText();
		_players.setText(_currentPlayer);
		_board.setHeuristics(_profile.getPlayer(_currentPlayer));
		
	}

	protected class PlayerListener implements ActionListener{
		private String _player;
		public PlayerListener(String player) {
			_player=player;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			_players.setText(_player);
			_board.setHeuristics(_profile.getPlayer(_player));
			_currentPlayer=_player;
		}

	}

	protected class NewPlayerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			_board.setHeuristics(new Player(-1));
			_players.setText("Create New Player");
			_currentPlayer=null;
		}

	}

	protected class SaveListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Player newPlayer = _board.getHeuristics();
			//if it's a new player
			if(_currentPlayer==null) {
				String newPlayerName=JOptionPane.showInputDialog("New player name: ");
				while(true) {
					if(newPlayerName==null) {
						return;
					}
					if(_profile.addPlayer(newPlayer, newPlayerName)) {
						break;
					}
					newPlayerName=JOptionPane.showInputDialog("Please choose an unused name: ");
				}
				resetPlayerMenu();
				_currentPlayer=newPlayerName;
				_players.setText(_currentPlayer);
				_board.setHeuristics(_profile.getPlayer(_currentPlayer));
				
			}
			else {
				_profile.replacePlayer(newPlayer, _currentPlayer);
				
			}

		}

	}

}