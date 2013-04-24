package edu.brown.cs32.MFTG.gui.spaces;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JPanel;

import edu.brown.cs32.MFTG.gui.Board;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.PropertyInfo;
import edu.brown.cs32.MFTG.gui.Constants.Viewer;



public class Property  {

	private PropertyButton _myButton;
	private PropertyButton _generalButton;
	private Board _board;
	private PropertyInfo _propertyInfo;
	
	public Property(Board board, PropertyInfo propertyInfo) {
		_board = board;
		_propertyInfo = propertyInfo;
		_myButton = new PropertyButton(this, propertyInfo.getOrientation(), propertyInfo.getColor(), Viewer.ME, .75, 10000, 500, 2.1);
		_generalButton = new PropertyButton(this, propertyInfo.getOrientation(), propertyInfo.getColor(), Viewer.ALL, 1, 8999, 0, 2.9);
	}
	
	public void changeViewer (Viewer viewer) {
		if(viewer == Viewer.ME) {
			_board.put(_generalButton, _myButton);
		}
		else {
			_board.put(_myButton, _generalButton);
		}
	}
	
	public PropertyButton getMe () {
		return _myButton;
	}
	
	public void popup() {
		try {
			new PropertyPopUp(_propertyInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
