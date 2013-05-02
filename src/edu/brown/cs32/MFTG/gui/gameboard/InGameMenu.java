package edu.brown.cs32.MFTG.gui.gameboard;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.MonopolyGui;

public class InGameMenu extends BoardMenu {

	public InGameMenu(Board board, MonopolyGui main, String profile) {
		super(board, main, profile);
		
		JButton quit = new JButton("Quit Game");
		quit.addActionListener(new QuitListener());
		quit.setHorizontalAlignment(SwingConstants.RIGHT);
		
		Dimension dimension = new Dimension(Constants.FULL_WIDTH, Constants.MENU_HEIGHT);
		setSize(dimension);
		setPreferredSize(dimension);
		
		add(quit);
	}
	
	protected class QuitListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
				//TODO
		}
			
	}

}
