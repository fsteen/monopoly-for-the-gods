package edu.brown.cs32.MFTG.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class EmptyMenuBar extends JMenuBar{

	public EmptyMenuBar() {
		super();
		this.add(new JMenu(" "));
	}

}
