package edu.brown.cs32.MFTG.gui;

import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class EmptyMenuBar extends JMenuBar{

	public EmptyMenuBar() {
		super();
		add(Box.createHorizontalGlue());
		this.add(new JMenu(" "));
	}

}
