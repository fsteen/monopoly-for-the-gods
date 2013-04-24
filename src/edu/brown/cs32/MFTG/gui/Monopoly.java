package edu.brown.cs32.MFTG.gui;

import java.awt.Color;

import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.Viewer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import spaces.PropertyButton;


public class Monopoly extends JFrame {
	public Monopoly () {
		super("Monopoly");
		
		// you want (look at the docs)
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Create top-level class and initialize App
		//PropertyFeedbackBoard board = new PropertyFeedbackBoard();
		//this.add(board);
		//PropertyHeuristicsBoard boar = new PropertyHeuristicsBoard();
		//this.add(boar);
		Board board = new Board();
		this.add(board);
		this.pack();
		this.setVisible(true);
	}
	
	public static void main (String[] args) {
		new Monopoly();
	}
	
}