package edu.brown.cs32.MFTG.gui.center;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.brown.cs32.MFTG.gui.Constants.View;
import edu.brown.cs32.MFTG.gui.gameboard.Board;

public class ViewListener implements ActionListener {

	private View _view;
	private Board _board;
	public ViewListener (View view, Board board) {
		_view = view;
		_board=board;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		_board.setView(_view);
	}
	
}