package edu.brown.cs32.MFTG.monopoly;

import java.util.HashMap;


/**
 * wrapper around the board for the array
 * @author JudahSchvimer
 *
 */
public class Board {
	private Space[] _board;
	private HashMap<String, Space> _spaces;
	private Game _game;
	/**
	 * Constructs a new board
	 */
	public Board(Game game) {
		_game=game;
		_board = new Space[40];
		_spaces = new HashMap<>(40);
		
		_board[0]=new GoSpace(0);
		_spaces.put("go", _board[0]);

		_board[1]=new PropertySpace(1,new Property("mediterranean avenue","purple",60,30,2,10,30,90,160,250,50));
		_spaces.put("mediterranean avenue", _board[1]);

		_board[2]=new CommunityChestSpace(2);
		_spaces.put("community chest 1", _board[2]);
		
		_board[3]=new PropertySpace(3,new Property("baltic avenue","purple",60,30,4,20,60,180,320,450,50));
		_spaces.put("baltic avenue", _board[3]);
		
		_board[1].getProperty().setSiblings(_board[3].getProperty(), null);
		_board[3].getProperty().setSiblings(_board[1].getProperty(), null);
		
		
		
		_board[4]=new IncomeTaxSpace(4);
		_spaces.put("income tax", _board[4]);
		
		_board[5]=new PropertySpace(5,new RailroadProperty("reading railroad"));
		_spaces.put("reading railroad", _board[5]);
		
		_board[6]=new PropertySpace(6,new Property("oriental avenue","light blue",100,50,6,30,90,270,400,550,50));
		_spaces.put("oriental avenue", _board[6]);
		
		_board[7]=new ChanceSpace(7);
		_spaces.put("chance 1", _board[7]);
		
		_board[8]=new PropertySpace(8,new Property("vermont avenue","light blue",100,50,6,30,90,270,400,550,50));
		_spaces.put("vermont avenue", _board[8]);
		
		_board[9]=new PropertySpace(9,new Property("connecticut avenue","light blue",120,60,8,40,100,300,450,600,50));
		_spaces.put("connecticut avenue", _board[9]);
		
		_board[6].getProperty().setSiblings(_board[8].getProperty(), _board[9].getProperty());
		_board[8].getProperty().setSiblings(_board[6].getProperty(), _board[9].getProperty());
		_board[9].getProperty().setSiblings(_board[6].getProperty(), _board[8].getProperty());
		
		
		
		_board[10]=new JailSpace(10);
		_spaces.put("jail", _board[10]);
		
		_board[11]=new PropertySpace(11,new Property("st. charles place","pink",140,70,10,50,150,450,625,750,100));
		_spaces.put("st. charles place", _board[11]);
		
		_board[12]=new PropertySpace(12,new UtilityProperty("electric company", _game));
		_spaces.put("electric company", _board[12]);
		
		_board[13]=new PropertySpace(13,new Property("states avenue","pink",140,70,10,50,150,450,625,750,100));
		_spaces.put("states avenue", _board[13]);
		
		_board[14]=new PropertySpace(14,new Property("virginia avenue","pink",160,80,12,60,180,500,700,900,100));
		_spaces.put("virginia avenue", _board[14]);
		
		_board[11].getProperty().setSiblings(_board[13].getProperty(), _board[14].getProperty());
		_board[13].getProperty().setSiblings(_board[11].getProperty(), _board[14].getProperty());
		_board[14].getProperty().setSiblings(_board[11].getProperty(), _board[13].getProperty());
		
		
		
		_board[15]=new PropertySpace(15,new RailroadProperty("pennsylvania railroad"));
		_spaces.put("pennsylvania railroad", _board[15]);
		
		_board[16]=new PropertySpace(16,new Property("st. james place","orange",180,90,14,70,200,550,750,950,100));
		_spaces.put("st. james place", _board[16]);
		
		_board[17]=new CommunityChestSpace(17);
		_spaces.put("community chest 2", _board[17]);
		
		_board[18]=new PropertySpace(18,new Property("tennessee avenue","orange",180,90,14,70,200,550,750,950,100));
		_spaces.put("tennessee avenue", _board[18]);
		
		_board[19]=new PropertySpace(19,new Property("new york avenue","orange",200,100,16,80,220,600,800,1000,100));
		_spaces.put("new york avenue", _board[19]);
		
		_board[16].getProperty().setSiblings(_board[18].getProperty(), _board[19].getProperty());
		_board[18].getProperty().setSiblings(_board[16].getProperty(), _board[19].getProperty());
		_board[19].getProperty().setSiblings(_board[16].getProperty(), _board[18].getProperty());
		
		
		
		_board[20]=new FreeParkingSpace(20);
		_spaces.put("free parking", _board[20]);
		
		_board[21]=new PropertySpace(21,new Property("kentucky avenue","red",220,110,18,90,250,700,875,1050,150));
		_spaces.put("kentucky avenue", _board[21]);
		
		_board[22]=new ChanceSpace(22);
		_spaces.put("chance 2", _board[22]);
		
		_board[23]=new PropertySpace(23,new Property("indiana avenue","red",220,110,18,90,250,700,875,1050,150));
		_spaces.put("indiana avenue", _board[23]);
		
		_board[24]=new PropertySpace(24,new Property("illinois avenue","red",240,120,20,100,300,750,925,1100,150));
		_spaces.put("illinois avenue", _board[24]);
		
		_board[21].getProperty().setSiblings(_board[23].getProperty(), _board[24].getProperty());
		_board[23].getProperty().setSiblings(_board[21].getProperty(), _board[24].getProperty());
		_board[24].getProperty().setSiblings(_board[21].getProperty(), _board[23].getProperty());
		
		
		
		_board[25]=new PropertySpace(25,new RailroadProperty("b and o railroad"));
		_spaces.put("b and o railroad", _board[25]);
		
		_board[26]=new PropertySpace(26,new Property("atlantic avenue","yellow",260,130,22,110,330,800,975,1150,150));
		_spaces.put("atlantic avenue", _board[26]);
		
		_board[27]=new PropertySpace(27,new Property("ventnor avenue","yellow",260,130,22,110,330,800,975,1150,150));
		_spaces.put("ventnor avenue", _board[27]);
		
		_board[28]=new PropertySpace(28,new UtilityProperty("water works", _game));
		_spaces.put("water works", _board[28]);
		
		_board[29]=new PropertySpace(29,new Property("marvin gardens","yellow",280,140,24,120,360,850,1025,1200,150));
		_spaces.put("marvin gardens", _board[27]);
		
		_board[26].getProperty().setSiblings(_board[27].getProperty(), _board[29].getProperty());
		_board[27].getProperty().setSiblings(_board[26].getProperty(), _board[29].getProperty());
		_board[29].getProperty().setSiblings(_board[26].getProperty(), _board[27].getProperty());
		
		
		
		_board[30]=new GoToJailSpace(30);
		_spaces.put("go to jail", _board[30]);
		
		_board[31]=new PropertySpace(31,new Property("pacific avenue","green",300,150,26,130,390,900,1100,1275,200));
		_spaces.put("pacific avenue", _board[31]);
		
		_board[32]=new PropertySpace(32,new Property("north carolina avenue","green",300,150,26,130,390,900,1100,1275,200));
		_spaces.put("north carolina avenue", _board[32]);
		
		_board[33]=new CommunityChestSpace(33);
		_spaces.put("community chest 3", _board[33]);
		
		_board[34]=new PropertySpace(34,new Property("pennsylvania avenue","green",320,160,28,150,450,1000,1200,1400,200));
		_spaces.put("pennsylvania avenue", _board[34]);
		
		_board[31].getProperty().setSiblings(_board[32].getProperty(), _board[34].getProperty());
		_board[32].getProperty().setSiblings(_board[31].getProperty(), _board[34].getProperty());
		_board[34].getProperty().setSiblings(_board[31].getProperty(), _board[32].getProperty());
		
		
		
		_board[35]=new PropertySpace(35,new RailroadProperty("short line"));
		_spaces.put("short line", _board[35]);
		
		_board[36]=new ChanceSpace(36);
		_spaces.put("chance 3", _board[36]);
		
		_board[37]=new PropertySpace(37,new Property("park place","dark blue",350,175,35,175,500,1100,1300,1500,200));
		_spaces.put("park place", _board[37]);
		
		_board[38]=new LuxuryTaxSpace(38);
		_spaces.put("chance 3", _board[38]);
		
		_board[39]=new PropertySpace(39,new Property("boardwalk","dark blue",400,200,50,200,600,1400,1700,2000,200));
		_spaces.put("boardwalk", _board[39]);
		
		_board[37].getProperty().setSiblings(_board[39].getProperty(), null);
		_board[39].getProperty().setSiblings(_board[37].getProperty(), null);
		
	}

	/**
	 * gets a space
	 * @param position
	 * @return
	 */
	public Space get(int position){
		return _board[position];
	}
	
	/**
	 * Get a space by name
	 * @param spaceName
	 * @return space
	 */
	public Space get(String spaceName){
		return _spaces.get(spaceName);
	}
}
