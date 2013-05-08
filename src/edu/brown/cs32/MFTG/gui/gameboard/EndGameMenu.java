package edu.brown.cs32.MFTG.gui.gameboard;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.MonopolyGui;
import edu.brown.cs32.MFTG.tournament.Profile;

public class EndGameMenu extends BoardMenu {
	private MonopolyGui _main;
	public EndGameMenu(Board board, MonopolyGui main, Profile profile) {
		super(board, main, profile);
		_main=main;
		removeDeleteButton();
		
		
		JButton back = new JButton("Back");
		back.addActionListener(new BackListener());
		
		Dimension dimension = new Dimension(Constants.FULL_WIDTH, Constants.MENU_HEIGHT);
		setSize(dimension);
		setPreferredSize(dimension);
		
		
		add(back);
	}
	
	protected class BackListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			_main.switchPanels("end");
			
		}
			
	}

}
