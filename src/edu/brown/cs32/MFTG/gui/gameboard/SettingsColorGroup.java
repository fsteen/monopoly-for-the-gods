package edu.brown.cs32.MFTG.gui.gameboard;

import java.util.List;

import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.Properties;
import edu.brown.cs32.MFTG.gui.Constants.View;
import edu.brown.cs32.MFTG.gui.properties.PropertyPanel;

public class SettingsColorGroup extends ColorGroup {

	
	public SettingsColorGroup(Orientation orientation, List<Properties> properties, List<PropertyPanel> myPanel, List<PropertyPanel> aggregatePanel, ColorBlock colorBlock) {
		super(orientation, properties, myPanel, aggregatePanel, colorBlock);
	}
	
	public void nextView() {
		if(_view == View.ME) {
			_view = View.COLOR;
		}
		else if(_view == View.COLOR) {
			_view = View.ME;
		}
		else if(_view == View.AGGREGATE) {
			_view = View.ME;
		}
	}

}
