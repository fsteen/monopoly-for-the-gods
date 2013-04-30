package edu.brown.cs32.MFTG.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Constants {
	public final static int WIDTH = 70;
	public final static int HEIGHT = 120;
	public final static int HEADER_HEIGHT = (int) 50/2;
	public final static int BORDER = (int) 2;
	
	public static int ACTUAL_WIDTH = (int) WIDTH;
	public static int ACTUAL_HEIGHT = (int) HEIGHT;
	//public static Color BACKGROUND_COLOR = Color.WHITE;
	public static Color BACKGROUND_COLOR = new Color(245, 245, 245); //new Color(211, 211, 211);
	
	public static final int FONT_SIZE = 12;
	public static final Font FONT = new Font("sansserif", Font.BOLD, FONT_SIZE);

	public final static int FULL_WIDTH= 9*Constants.WIDTH + 2*Constants.HEIGHT + 11;
	public final static int FULL_HEIGHT= 9*Constants.WIDTH + 2*Constants.HEIGHT + 11;
	
	public final static int BACK_Y=740;
	public final static int BACK_X=20;
	
	public static String t = "_two.jpg";
	public static String m = "_monopoly.jpg";
	public static String n = "_no_monopoly.jpg";
	public static String f = "Deed_Cards/";
	public static final Color CLEAR = new Color(0,0,0,0);

	public static int BUTTON_DIMENSION = Constants.WIDTH;
	
	public interface Toggle {
		public ImageIcon getIcon();
		public Toggle next();
	}
	
	public enum Price implements Toggle {
		CHEAP("Deed_Cards/cheap.jpg"), EXPENSIVE("Deed_Cards/expensive.jpg");
		
		private ImageIcon _icon;
		private Price (String path) {
			try {
				BufferedImage i = ImageIO.read(new File(path));
				i = Helper.resize(i, BUTTON_DIMENSION, BUTTON_DIMENSION);
				_icon = new ImageIcon(i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public ImageIcon getIcon() { return _icon; }
		public Toggle next() {
			if(this == CHEAP) return EXPENSIVE;
			return CHEAP;
		}
	}
	
	public enum Quantity implements Toggle {
		FEWER("Deed_Cards/fewer.jpg"), MORE("Deed_Cards/more.jpg");
		
		private ImageIcon _icon;
		private Quantity (String path) {
			try {
				BufferedImage i = ImageIO.read(new File(path));
				i = Helper.resize(i, BUTTON_DIMENSION, BUTTON_DIMENSION);
				_icon = new ImageIcon(i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public ImageIcon getIcon() { return _icon; }
		public Toggle next() {
			if(this == FEWER) return MORE;
			return FEWER;
		}
	}
	
	public enum Aggression implements Toggle {
		PASSIVE("Deed_Cards/passive.jpg"), AGGRESSIVE("Deed_Cards/aggressive.jpg");
		
		private ImageIcon _icon;
		private Aggression (String path) {
			try {
				BufferedImage i = ImageIO.read(new File(path));
				i = Helper.resize(i, BUTTON_DIMENSION, BUTTON_DIMENSION);
				_icon = new ImageIcon(i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public ImageIcon getIcon() { return _icon; }
		public Toggle next() {
			if(this == PASSIVE) return AGGRESSIVE;
			return PASSIVE;
		}
	}
	
	public enum Balance implements Toggle {
		EVEN("Deed_Cards/balanced.jpg"), UNEVEN("Deed_Cards/uneven.jpg");
		
		private ImageIcon _icon;
		private Balance (String path) {
			try {
				BufferedImage i = ImageIO.read(new File(path));
				i = Helper.resize(i, BUTTON_DIMENSION, BUTTON_DIMENSION);
				_icon = new ImageIcon(i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public ImageIcon getIcon() { return _icon; }
		public Toggle next() {
			if(this == EVEN) return UNEVEN;
			return EVEN;
		}
	}
	
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
	
	public interface Properties {
		public Orientation getOrientation();
		public String getLowercaseName();
	}
	
	public enum Colors {
		PURPLE(PropertyColor.PURPLE, Orientation.DOWN, f + "purple" + t, f + "purple" + m, f + "purple" + n, "purple"),
		LIGHT_BLUE(PropertyColor.LIGHT_BLUE, Orientation.DOWN,  f + "light_blue" + t, f + "light_blue" + m, f + "light_blue" + n, "light blue"),
		PINK(PropertyColor.PINK, Orientation.LEFT,  f + "pink" + t, f + "pink" + m, f + "pink" + n, "pink"),
		ORANGE(PropertyColor.ORANGE, Orientation.LEFT,  f + "orange" + t, f + "orange" + m, f + "orange" + n, "orange"), 
		RED(PropertyColor.RED, Orientation.UP,  f + "red" + t, f + "red" + m, f + "red" + n, "red"),
		YELLOW(PropertyColor.YELLOW, Orientation.UP,  f + "yellow" + t, f + "yellow" + m, f + "yellow" + n, "yellow"), 
		GREEN(PropertyColor.GREEN, Orientation.RIGHT, f + "green" + t, f + "green" + m, f + "green" + n, "green"),
		DARK_BLUE(PropertyColor.DARK_BLUE, Orientation.RIGHT,  f + "dark_blue" + t, f + "dark_blue" + m, f + "dark_blue" + n, "dark blue");
		
		private PropertyColor color;
		private Orientation orientation;
		private String two;
		private String monopoly;
		private String noMonopoly;
		private String name;
		Colors (PropertyColor color, Orientation orientation, String two, String monopoly, String noMonopoly, String name) {
			this.color = color;
			this.orientation = orientation;
			this.two = two;
			this.name = name;
			this.monopoly = monopoly;
			this.noMonopoly = noMonopoly;
		}
		public Color getColor() { return color.getColor(); }
		public Orientation getOrientation() { return orientation; }
		public String getTwo() { return two; }
		public String getMonopoly() { return monopoly; }
		public String getNoMonopoly() { return noMonopoly; }
		public String getName() { return name; }
	}
	
	public enum Corners {
		GO_TO_JAIL (Orientation.RIGHT, "Deed_Cards/go_to_jail.jpg"),
		FREE_PARKING (Orientation.UP, "Deed_Cards/free_parking.jpg"),
		GO (Orientation.DOWN, "Deed_Cards/go.jpg"),
		JAIL (Orientation.LEFT, "Deed_Cards/jail.jpg");
		
		private Orientation orientation;
		private String file;
		Corners (Orientation orientation, String file) {
			this.orientation = orientation;
			this.file = file;
		}
		public Orientation getOrientation() { return orientation; }
		public String getFile() { return file; }
	}
	
	public enum Utilities implements Properties{
		ELECTRIC_COMPANY(Orientation.LEFT, "Electric Company", "Deed_Cards/lightbulb.jpg", "electric company"),
		WATER_WORKS(Orientation.UP, "Water Works", "Deed_Cards/faucet.jpg", "water works");
		
		private Orientation orientation;
		private String name;
		private String file;
		private String lowercaseName;
		Utilities(Orientation orientation, String name, String file, String lowercaseName) {
			this.orientation = orientation;
			this.name = name;
			this.file = file;
			this.lowercaseName = lowercaseName;
		}
		public Orientation getOrientation() { return orientation; }
		public String getName() { return name; }
		public String getFile() { return file; }
		public String getLowercaseName() { return lowercaseName; }
	}
	
	public enum StaticProperties implements Properties {
		COMMUNITY_CHEST_DOWN(Orientation.DOWN, "Deed_Cards/community_chest.jpg"),
		INCOME_TAX(Orientation.DOWN, "Deed_Cards/income_tax.jpg"),
		PINK_CHANCE(Orientation.DOWN, "Deed_Cards/pink_chance.jpg"),
		COMMUNITY_CHEST_LEFT(Orientation.LEFT, "Deed_Cards/community_chest.jpg"),
		BLUE_CHANCE(Orientation.UP, "Deed_Cards/blue_chance.jpg"),
		COMMUNITY_CHEST_RIGHT(Orientation.RIGHT, "Deed_Cards/community_chest.jpg"),
		RED_CHANCE(Orientation.RIGHT, "Deed_Cards/red_chance.jpg"),
		LUXURY_TAX(Orientation.RIGHT, "Deed_Cards/ring.jpg"), 
		GO_TO_JAIL (Orientation.RIGHT, "Deed_Cards/go_to_jail.jpg"),
		FREE_PARKING (Orientation.UP, "Deed_Cards/free_parking.jpg"),
		GO (Orientation.DOWN, "Deed_Cards/go.jpg"),
		JAIL (Orientation.LEFT, "Deed_Cards/jail.jpg");
		
		private Orientation orientation;
		private String file;
		StaticProperties(Orientation orientation, String file) {
			this.orientation = orientation;
			this.file = file;
		}
		public Orientation getOrientation() { return orientation; }
		public String getFile() { return file; }
		public String getLowercaseName() { return "static property"; }
	}
	
	public enum Railroads implements Properties {
		READING_RAILROAD(Orientation.DOWN, "Reading Railroad", "Deed_Cards/reading_railroad.jpg", "reading railroad"),
		PENNSYLVANIA_RAILROAD(Orientation.LEFT, "Pennsylvania Railroad", "Deed_Cards/pennsylvania_railroad.jpg", "pennsylvania railroad"),
		B_AND_O_RAILROAD(Orientation.UP, "B. and O. Railroad", "Deed_Cards/b_and_o_railroad.jpg", "b and o railroad"),
		SHORT_LINE(Orientation.RIGHT, "Short Line", "Deed_Cards/short_line.jpg", "short line");
		
		private Orientation orientation;
		private String name;
		private String file;
		private String lowercaseName;
		Railroads(Orientation orientation, String name, String file, String lowercaseName) {
			this.orientation = orientation;
			this.name = name;
			this.file = file;
			this.lowercaseName = lowercaseName;
		}
		public Orientation getOrientation() { return orientation; }
		public String getName() { return name; }
		public String getFile() { return file; }
		public String getLowercaseName() { return lowercaseName; }
	}
	
	public enum ColorProperties implements Properties {
		MEDITERRANEAN_AVENUE(Orientation.DOWN, PropertyColor.PURPLE, "Mediterranean Avenue", "Deed_Cards/mediterranean_avenue.jpg", "mediterranean avenue"),
		BALTIC_AVENUE(Orientation.DOWN, PropertyColor.PURPLE, "Baltic Avenue", "Deed_Cards/baltic_avenue.jpg", "baltic avenue"),
		ORIENTAL_AVENUE(Orientation.DOWN, PropertyColor.LIGHT_BLUE, "Oriental Avenue", "Deed_Cards/oriental_avenue.jpg", "oriental avenue"),
		VERMONT_AVENUE(Orientation.DOWN, PropertyColor.LIGHT_BLUE, "Vermont Avenue", "Deed_Cards/vermont_avenue.jpg", "vermont avenue"),
		CONNECTICUT_AVENUE(Orientation.DOWN, PropertyColor.LIGHT_BLUE, "Connecticut Avenue", "Deed_Cards/connecticut_avenue.jpg", "connecticut avenue"),
		ST_CHARLES_PLACE(Orientation.LEFT, PropertyColor.PINK, "St. Charles Place", "Deed_Cards/st_charles_places.jpg", "st. charles place"),
		STATES_AVENUE(Orientation.LEFT, PropertyColor.PINK, "States Avenue", "Deed_Cards/states_avenue.jpg", "states avenue"),
		VIRGINIA_AVENUE(Orientation.LEFT, PropertyColor.PINK, "Virginia Avenue", "Deed_Cards/virginia_avenue.jpg", "virginia avenue"),
		ST_JAMES_PLACE(Orientation.LEFT, PropertyColor.ORANGE, "St. James Place", "Deed_Cards/st_james_place.jpg", "st. james place"),
		TENNESSEE_AVENUE(Orientation.LEFT, PropertyColor.ORANGE, "Tennessee Avenue", "Deed_Cards/tennessee_avenue.jpg", "tennessee avenue"),
		NEW_YORK_AVENUE(Orientation.LEFT, PropertyColor.ORANGE, "New York Avenue", "Deed_Cards/new_york_avenue.jpg", "new york avenue"),
		KENTUCKY_AVENUE(Orientation.UP, PropertyColor.RED, "Kentucky Avenue", "Deed_Cards/kentucky_avenue.jpg", "kentucky avenue"),
		INDIANA_AVENUE(Orientation.UP, PropertyColor.RED, "Indiana Avenue", "Deed_Cards/indiana_avenue.jpg", "indiana avenue"),
		ILLINOIS_AVENUE(Orientation.UP, PropertyColor.RED, "Illinois Avenue", "Deed_Cards/illinois_avenue.jpg", "illinois avenue"),
		ATLANTIC_AVENUE(Orientation.UP, PropertyColor.YELLOW, "Atlantic Avenue", "Deed_Cards/atlantic_avenue.jpg", "atlantic avenue"),
		VENTNOR_AVENUE(Orientation.UP, PropertyColor.YELLOW, "Ventnor Avenue", "Deed_Cards/ventnor_avenue.jpg", "ventnor avenue"),
		MARVIN_GARDENS(Orientation.UP, PropertyColor.YELLOW, "Marvin Gardens", "Deed_Cards/marvin_gardens.jpg", "marvin gardens"),
		PACIFIC_GARDEN(Orientation.RIGHT, PropertyColor.GREEN, "Pacific Avenue", "Deed_Cards/pacific_avenue.jpg", "pacific avenue"),
		NORTH_CAROLINA_AVENUE(Orientation.RIGHT, PropertyColor.GREEN, "North Carolina Avenue", "Deed_Cards/north_carolina_avenue.jpg", "north carolina avenue"),
		PENNSYLVANIA_AVENUE(Orientation.RIGHT, PropertyColor.GREEN, "Pennsylvania Avenue", "Deed_Cards/pennsylvania_avenue.jpg", "pennsylvania avenue"),
		PARK_PLACE(Orientation.RIGHT, PropertyColor.DARK_BLUE, "Park Place", "Deed_Cards/park_place.jpg", "park place"),
		BOARDWALK(Orientation.RIGHT, PropertyColor.DARK_BLUE, "Boardwalk", "Deed_Cards/boardwalk.jpg", "boardwalk");
		
		private Orientation orientation;
		private PropertyColor color;
		private String name;
		private String file;
		private String lowercaseName;
		ColorProperties(Orientation orientation, PropertyColor color, String name, String file, String lowercaseName) {
			this.orientation = orientation;
			this.color = color;
			this.name = name;
			this.file = file;
			this.lowercaseName = lowercaseName;
		}
		public Orientation getOrientation() { return orientation; }
		public Color getColor() { return color.getColor(); }
		public String getName() { return name; }
		public String getFile() { return file; }
		public String getLowercaseName() { return lowercaseName; }
	}
	
	public enum View {
		ME, AGGREGATE, COLOR;
	}
}
