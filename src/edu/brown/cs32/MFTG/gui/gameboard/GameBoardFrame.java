package edu.brown.cs32.MFTG.gui.gameboard;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

import edu.brown.cs32.MFTG.gui.Constants;

public class GameBoardFrame extends JFrame {

	public Board _board;
	
	public GameBoardFrame() {
		super();
		this.setPreferredSize(new Dimension(Constants.FULL_WIDTH, Constants.FULL_HEIGHT));
		
		try {
			_board = new Board(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.add(_board);
		
		this.pack();
		this.setVisible(true);
	}

}
