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

public class InGameMenu extends BoardMenu {
	private MonopolyGui _main;
	public InGameMenu(Board board, MonopolyGui main, Profile profile) {
		super(board, main, profile);
		_main=main;
		removeDeleteButton();
		
		JCheckBox music = new JCheckBox("Music: ", true);
		music.setHorizontalTextPosition(SwingConstants.LEADING);

		music.addItemListener(new MusicListener());
		
		JButton quit = new JButton("Quit Game");
		quit.addActionListener(new QuitListener());
		quit.setHorizontalAlignment(SwingConstants.RIGHT);
		
		Dimension dimension = new Dimension(Constants.FULL_WIDTH, Constants.MENU_HEIGHT);
		setSize(dimension);
		setPreferredSize(dimension);
		
		add(music);
		add(quit);
	}
	
	protected class QuitListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
				_main.switchPanels("greet");
				
		}
			
	}
	

	private class MusicListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange()==ItemEvent.SELECTED){
				_main.playNextInGameSong();
			}
			else{
				_main.stopMusic();
			}
			
		}
		
	}

}
