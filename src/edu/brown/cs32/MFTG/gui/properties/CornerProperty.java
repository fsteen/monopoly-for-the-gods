package edu.brown.cs32.MFTG.gui.properties;

import java.awt.Dimension;
import java.io.IOException;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.StaticProperties;

public class CornerProperty extends StaticProperty{

	public CornerProperty(StaticProperties properties) throws IOException {
		super(properties);
		
		this.setPreferredSize(new Dimension(Constants.ACTUAL_HEIGHT, Constants.ACTUAL_HEIGHT));
		this.setMaximumSize(new Dimension(Constants.ACTUAL_HEIGHT, Constants.ACTUAL_HEIGHT));
		this.setMinimumSize(new Dimension(Constants.ACTUAL_HEIGHT, Constants.ACTUAL_HEIGHT));
		
		_spaceOutline.setSize(Constants.HEIGHT, Constants.HEIGHT);
	}

}
