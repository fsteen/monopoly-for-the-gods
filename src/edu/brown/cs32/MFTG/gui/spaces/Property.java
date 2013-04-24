package edu.brown.cs32.MFTG.gui.spaces;

import java.awt.Color;

import javax.swing.JPanel;

import edu.brown.cs32.MFTG.gui.Board;



public class Property extends JPanel {

	private PropertyButton _myButton;
	private PropertyButton _generalButton;
	private Board _board;
	
	public Property(Board board, Orientation orientation, Color color) {
		_board = board;
		_myButton = new PropertyButton(this, orientation, color, Viewer.ME, .75, 10000, 500, 2.1);
		_generalButton = new PropertyButton(this, orientation, color, Viewer.ME, 1, 8999, 0, 2.9);
		this.add(_myButton);
	}
	
	public void changeViewer (Viewer viewer) {
		if(viewer == Viewer.ME) {
			_board.put(_generalButton, _myButton);
		}
		else {
			_board.put(_myButton, _generalButton);
		}
	}
	
}
