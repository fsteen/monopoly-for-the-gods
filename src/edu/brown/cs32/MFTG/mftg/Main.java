package edu.brown.cs32.MFTG.mftg;

import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

public class Main {

	public Main() {}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean gui = false;
		for (int i = 0; i < args.length; i++) {
			  if (args[i].equals("--gui"))
				  gui=true;
		}
			
			if (gui) {
				try { // Make the GUI look real nice
				    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				        if ("Nimbus".equals(info.getName())) {
				            UIManager.setLookAndFeel(info.getClassName());
				            break;
				        }
				    }
				} catch (UnsupportedLookAndFeelException|ClassNotFoundException|InstantiationException|IllegalAccessException e) {}
			}

		
	}

}
