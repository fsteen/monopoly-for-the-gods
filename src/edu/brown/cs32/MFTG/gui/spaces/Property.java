package edu.brown.cs32.MFTG.gui.spaces;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JPanel;

import edu.brown.cs32.MFTG.gui.Board;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.ColorProperties;
import edu.brown.cs32.MFTG.gui.Constants.Viewer;



public class Property  {

	private PropertyButton _myButton;
	private PropertyButton _generalButton;
	private Board _board;
	private ColorProperties _propertyInfo;
	
	public Property(Board board, ColorProperties propertyInfo) {
		_board = board;
		_propertyInfo = propertyInfo;
		
		_myButton = new PropertyButton(this, propertyInfo, Viewer.ME, Math.random(), (int) (Math.random()*10000), (int) (Math.random()*1000), Math.random()*5);
		_generalButton = new PropertyButton(this, propertyInfo, Viewer.ALL, 1, (int) (Math.random() * 10000), 0, Math.random() * 5);
	}
	
	public void changeViewer (Viewer viewer) {
		if(viewer == Viewer.ME) {
			_board.put(_propertyInfo, _generalButton, _myButton);
		}
		else {
			_board.put(_propertyInfo, _myButton, _generalButton);
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
