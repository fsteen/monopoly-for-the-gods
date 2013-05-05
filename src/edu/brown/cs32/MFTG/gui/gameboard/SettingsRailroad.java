package edu.brown.cs32.MFTG.gui.gameboard;

import java.io.IOException;

import edu.brown.cs32.MFTG.gui.Constants.Railroads;
import edu.brown.cs32.MFTG.gui.Constants.View;

public class SettingsRailroad extends Railroad {

	public SettingsRailroad(Railroads railroad) throws IOException {
		super(railroad);
	}

	
	public void nextView() {
		_view = View.ME;
	}
}
