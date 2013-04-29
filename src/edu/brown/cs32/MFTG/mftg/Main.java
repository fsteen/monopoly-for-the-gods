package edu.brown.cs32.MFTG.mftg;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import edu.brown.cs32.MFTG.gui.MonopolyGui;

public class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
			new MonopolyGui();
		} catch (UnsupportedLookAndFeelException|ClassNotFoundException|InstantiationException|IllegalAccessException e) {}


	}

}
