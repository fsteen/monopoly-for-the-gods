package edu.brown.cs32.MFTG.gui;

import java.awt.Color;
import java.awt.Font;

public class Constants {

	public final static int WIDTH = 135;
	public final static int HEIGHT = 225;
	public final static int HEADER_HEIGHT = 50;
	public final static int BORDER = 6;
	
	public static int ACTUAL_WIDTH = (int) WIDTH;
	public static int ACTUAL_HEIGHT = (int) HEIGHT;
	public static Color BACKGROUND_COLOR = new Color(211, 211, 211);
	
	public static final Font FONT = new Font("sansserif", Font.BOLD, 30);

	public enum Viewer {
		ME(Color.BLACK), ALL(Color.WHITE);
		private final Color color;
		Viewer (Color color) { this.color = color; }
		public Color getColor() {return color; }
	}
	
	public enum Orientation {
		DOWN(0, WIDTH, HEIGHT), RIGHT(.5*Math.PI, HEIGHT, WIDTH), LEFT(1.5*Math.PI, HEIGHT, WIDTH), UP(Math.PI, WIDTH, HEIGHT),
		CENTER(0, HEIGHT, HEIGHT);

		private final double rotation;
		private final int width;
		private final int height;
		Orientation(double rotation, int width, int height) {
			this.rotation = rotation; 
			this.width = width;
			this.height = height;
		}
		public double getRotation() { return rotation; }
		public int getWidth() { return width; }
		public int getHeight() { return height; }
	}
}