package edu.brown.cs32.MFTG.gui;

import javax.swing.JFrame;


public class Monopoly extends JFrame {
	public Monopoly () {
		super("Monopoly");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Board board = new Board();
		this.add(board);
		this.pack();
		this.setVisible(true);
	}
	
	public static void main (String[] args) {
		new Monopoly();
	}
	
}