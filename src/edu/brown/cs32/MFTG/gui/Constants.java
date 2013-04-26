package edu.brown.cs32.MFTG.gui;

import java.awt.Color;
import java.awt.Font;

public class Constants {
	public final static int WIDTH = (int) 135/2;
	public final static int HEIGHT = (int) 225/2;
	public final static int HEADER_HEIGHT = (int) 50/2;
	public final static int BORDER = (int) 6/2;
	
	public static int ACTUAL_WIDTH = (int) WIDTH;
	public static int ACTUAL_HEIGHT = (int) HEIGHT;
	//public static Color BACKGROUND_COLOR = Color.WHITE;
	public static Color BACKGROUND_COLOR = new Color(211, 211, 211);
	
	public static final int FONT_SIZE = 12;
	public static final Font FONT = new Font("sansserif", Font.BOLD, FONT_SIZE);

	public final static int FULL_WIDTH=9*Constants.WIDTH + 2*Constants.HEIGHT;
	public final static int FULL_HEIGHT=9*Constants.WIDTH + 2*Constants.HEIGHT;
	
	public static String t = "_two.jpg";
	public static String m = "_monopoly.jpg";
	public static String n = "_no_monopoly.jpg";
	public static String f = "Deed_Cards/";

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
	
	public enum PropertyColor {
		PURPLE(75, 61, 120), LIGHT_BLUE(150, 210, 244), PINK (214, 41, 130), ORANGE(235, 154, 13), RED(219, 8, 40), YELLOW(242, 244, 54), 
		GREEN(4, 173, 90), DARK_BLUE(63, 102, 171);
		
		private Color color;
		PropertyColor (int r, int g, int b) {
			this.color = new Color(r,g,b);
		}
		public Color getColor() { return color; }
	}
	
	public enum ColorInfo {
		PURPLE(PropertyColor.PURPLE, Orientation.DOWN, f + "purple" + t, f + "purple" + m, f + "purple" + n),
		LIGHT_BLUE(PropertyColor.LIGHT_BLUE, Orientation.DOWN,  f + "light_blue" + t, f + "light_blue" + m, f + "light_blue" + n),
		PINK(PropertyColor.PINK, Orientation.LEFT,  f + "pink" + t, f + "pink" + m, f + "pink" + n),
		ORANGE(PropertyColor.ORANGE, Orientation.LEFT,  f + "orange" + t, f + "orange" + m, f + "orange" + n), 
		RED(PropertyColor.RED, Orientation.UP,  f + "red" + t, f + "red" + m, f + "red" + n),
		YELLOW(PropertyColor.YELLOW, Orientation.UP,  f + "yellow" + t, f + "yellow" + m, f + "yellow" + n), 
		GREEN(PropertyColor.GREEN, Orientation.RIGHT, f + "green" + t, f + "green" + m, f + "green" + n),
		DARK_BLUE(PropertyColor.DARK_BLUE, Orientation.RIGHT,  f + "dark_blue" + t, f + "dark_blue" + m, f + "dark_blue" + n);
		
		private PropertyColor color;
		private Orientation orientation;
		private String two;
		private String monopoly;
		private String noMonopoly;
		ColorInfo (PropertyColor color, Orientation orientation, String two, String monopoly, String noMonopoly) {
			this.color = color;
			this.orientation = orientation;
			this.two = two;
			this.monopoly = monopoly;
			this.noMonopoly = noMonopoly;
		}
		public Color getColor() { return color.getColor(); }
		public Orientation getOrientation() { return orientation; }
		public String getTwo() { return two; }
		public String getMonopoly() { return monopoly; }
		public String getNoMonopoly() { return noMonopoly; }
	}
	
	public enum RailRoadInfo {
		READING_RAILROAD(Orientation.DOWN, "Reading Railroad", "Deed_Cards/reading_railroad.jpg"),
		PENNSYLVANIA_RAILROAD(Orientation.LEFT, "Pennsylvania Railroad", "Deed_Cards/pennsylvania_railroad.jpg"),
		B_AND_O_RAILROAD(Orientation.UP, "B. and O. Railroad", "Deed_Cards/b_and_o_railroad.jpg"),
		SHORT_LINE(Orientation.RIGHT, "Short Line", "Deed_Cards/short_line.jpg"),
		ELECTRIC_COMPANY(Orientation.LEFT, "Electric Company", "Deed_Cards/electric_company.jpg"),
		WATER_WORKS(Orientation.UP, "Water Works", "Deed_Cards/water_works.jpg");
		
		private Orientation orientation;
		private String name;
		private String file;
		RailRoadInfo(Orientation orientation, String name, String file) {
			this.orientation = orientation;
			this.name = name;
			this.file = file;
		}
		public Orientation getOrientation() { return orientation; }
		public String getName() { return name; }
		public String getFile() { return file; }
	}
	
	public enum PropertyInfo {
		MEDITERRANEAN_AVENUE(Orientation.DOWN, PropertyColor.PURPLE, "Mediterranean Avenue", "Deed_Cards/mediterranean_avenue.jpg"),
		BALTIC_AVENUE(Orientation.DOWN, PropertyColor.PURPLE, "Baltic Avenue", "Deed_Cards/baltic_avenue.jpg"),
		ORIENTAL_AVENUE(Orientation.DOWN, PropertyColor.LIGHT_BLUE, "Oriental Avenue", "Deed_Cards/oriental_avenue.jpg"),
		VERMONT_AVENUE(Orientation.DOWN, PropertyColor.LIGHT_BLUE, "Vermont Avenue", "Deed_Cards/vermont_avenue.jpg"),
		CONNECTICUT_AVENUE(Orientation.DOWN, PropertyColor.LIGHT_BLUE, "Connecticut Avenue", "Deed_Cards/connecticut_avenue.jpg"),
		ST_CHARLES_PLACE(Orientation.LEFT, PropertyColor.PINK, "St. Charles Place", "Deed_Cards/st_charles_places.jpg"),
		STATES_AVENUE(Orientation.LEFT, PropertyColor.PINK, "States Avenue", "Deed_Cards/states_avenue.jpg"),
		VIRGINIA_AVENUE(Orientation.LEFT, PropertyColor.PINK, "Virginia Avenue", "Deed_Cards/virginia_avenue.jpg"),
		ST_JAMES_PLACE(Orientation.LEFT, PropertyColor.ORANGE, "St. James Place", "Deed_Cards/st_james_place.jpg"),
		TENNESSEE_AVENUE(Orientation.LEFT, PropertyColor.ORANGE, "Tennessee Avenue", "Deed_Cards/tennessee_avenue.jpg"),
		NEW_YORK_AVENUE(Orientation.LEFT, PropertyColor.ORANGE, "New York Avenue", "Deed_Cards/new_york_avenue.jpg"),
		KENTUCKY_AVENUE(Orientation.UP, PropertyColor.RED, "Kentucky Avenue", "Deed_Cards/kentucky_avenue.jpg"),
		INDIANA_AVENUE(Orientation.UP, PropertyColor.RED, "Indiana Avenue", "Deed_Cards/indiana_avenue.jpg"),
		ILLINOIS_AVENUE(Orientation.UP, PropertyColor.RED, "Illinois Avenue", "Deed_Cards/illinois_avenue.jpg"),
		ATLANTIC_AVENUE(Orientation.UP, PropertyColor.YELLOW, "Atlantic Avenue", "Deed_Cards/atlantic_avenue.jpg"),
		VENTNOR_AVENUE(Orientation.UP, PropertyColor.YELLOW, "Ventnor Avenue", "Deed_Cards/ventnor_avenue.jpg"),
		MARVIN_GARDENS(Orientation.UP, PropertyColor.YELLOW, "Marvin Gardens", "Deed_Cards/marvin_gardens.jpg"),
		PACIFIC_GARDEN(Orientation.RIGHT, PropertyColor.GREEN, "Pacific Avenue", "Deed_Cards/pacific_avenue.jpg"),
		NORTH_CAROLINA_AVENUE(Orientation.RIGHT, PropertyColor.GREEN, "North Carolina Avenue", "Deed_Cards/north_carolina_avenue.jpg"),
		PENNSYLVANIA_AVENUE(Orientation.RIGHT, PropertyColor.GREEN, "Pennsylvania Avenue", "Deed_Cards/pennsylvania_avenue.jpg"),
		PARK_PLACE(Orientation.RIGHT, PropertyColor.DARK_BLUE, "Park Place", "Deed_Cards/park_place.jpg"),
		BOARDWALK(Orientation.RIGHT, PropertyColor.DARK_BLUE, "Boardwalk", "Deed_Cards/boardwalk.jpg");
		
		private Orientation orientation;
		private PropertyColor color;
		private String name;
		private String file;
		PropertyInfo(Orientation orientation, PropertyColor color, String name, String file) {
			this.orientation = orientation;
			this.color = color;
			this.name = name;
			this.file = file;
		}
		public Orientation getOrientation() { return orientation; }
		public Color getColor() { return color.getColor(); }
		public String getName() { return name; }
		public String getFile() { return file; }
	}
}
