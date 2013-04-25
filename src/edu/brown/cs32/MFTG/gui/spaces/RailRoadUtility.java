package edu.brown.cs32.MFTG.gui.spaces;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JPanel;

import edu.brown.cs32.MFTG.gui.Board;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.PropertyInfo;
import edu.brown.cs32.MFTG.gui.Constants.RailRoadInfo;
import edu.brown.cs32.MFTG.gui.Constants.Viewer;

public class RailRoadUtility  {

	private RailRoadUtilityButton _myButton;
	private RailRoadUtilityButton _generalButton;
	private Board _board;
	private RailRoadInfo _railRoadInfo;
	
	public RailRoadUtility (Board board, RailRoadInfo railRoadInfo) throws IOException {
		_board = board;
		_railRoadInfo = railRoadInfo;
		_myButton = new RailRoadUtilityButton(this, railRoadInfo, Viewer.ME, Math.random(), (int) (Math.random()*10000), (int) (Math.random() * 100000));
		_generalButton = new RailRoadUtilityButton(this, railRoadInfo, Viewer.ALL, 1, (int) (Math.random() * 10000), (int) (Math.random() * 10000));
	}
	
	public void changeViewer (Viewer viewer) {
		if(viewer == Viewer.ME) {
			_board.put(_railRoadInfo, _generalButton, _myButton);
		}
		else {
			_board.put(_railRoadInfo, _myButton, _generalButton);
		}
	}
	
	public RailRoadUtilityButton getMe () {
		return _myButton;
	}
	
	public void popup() {
		try {
			new RailRoadUtilityPopup(_railRoadInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
