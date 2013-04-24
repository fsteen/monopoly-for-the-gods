package edu.brown.cs32.MFTG.mftg;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import edu.brown.cs32.MFTG.gui.Board;
import edu.brown.cs32.MFTG.gui.GreetingPanel;

public class Main extends JFrame{

	public Main() {
		super("Monopoly");

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		//Board board = new Board();
		//this.add(board);
		GreetingPanel greet = new GreetingPanel();
		this.add(greet);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new Main();
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (UnsupportedLookAndFeelException|ClassNotFoundException|InstantiationException|IllegalAccessException e) {}


	}

}
