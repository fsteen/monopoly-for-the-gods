package edu.brown.cs32.MFTG.monopoly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import edu.brown.cs32.MFTG.monopoly.GamePlayer.HouseSellingComparator;
import edu.brown.cs32.MFTG.monopoly.GamePlayer.MortgageComparator;

/**
 * this class models a game and is a runnable so it can run in its own thread
 * @author jschvime
 *
 */
public class Game implements Runnable{
	private ArrayList<GamePlayer> _players;
	private GamePlayer _currentPlayer;
	private int _numPlayers, _currentTurn,_freeParkingMoney, _defaultFP, _numHousesLeft, _numHotelsLeft, _maxNumTurns;
	private Board _board;
	private Dice _dice;
	private int _turnsPerTimeStamp;
	private boolean _playing, _doubleOnGo, _auctions;
	private int _doublesInRow;
	private CommunityChestDeck _comChest;
	private ChanceDeck _chance;
	private GameData _gameData;
	private long _seed;
	private GamePlayer _banker;

	/**
	 * Constructs a new game
	 * @param freeParking, if negative means nothing happens when landed on
	 * @param auctions
	 * @param players
	 */
	public Game(long seed, int maxNumTurns, int freeParking,boolean doubleOnGo, boolean auctions, Player...players) {
		_maxNumTurns=maxNumTurns;
		 Random rand= new Random(seed);
		_players=new ArrayList<>(players.length);
		_turnsPerTimeStamp=Math.max(2, _players.size());
		for(Player p:players){
			_numPlayers++;
			_players.add(new GamePlayer(this,p));
		}
		Collections.shuffle(_players, rand);
		_currentPlayer = _players.get(0);
		
		_banker = new GamePlayer(this, null);
		
		_seed=seed;
		_currentTurn=0;
		_defaultFP=freeParking;
		_freeParkingMoney = _defaultFP;
		_doubleOnGo=doubleOnGo;

		_auctions=auctions;
		_numHousesLeft=32;
		_numHotelsLeft=12;

		_board = new Board(this);
		_dice = new Dice(rand);

		_playing = true;


		_doublesInRow=0;

		_comChest=new CommunityChestDeck(rand);
		_chance= new ChanceDeck(rand);

		_gameData=new GameData(_numPlayers);
		_gameData.addNewTime();
		for(GamePlayer player: _players){
			_gameData.setWealthAtTime(player.getPlayer().ID, player.getCash(), player.getTotalWealth());
		}



	}

	@Override
	public void run() {
		Thread.currentThread().setName("Monopoly Game: "+_seed);
		try {
			while(_playing){
				System.out.println("	Turn: "+_currentTurn+", "+_currentPlayer+" cash: "+_currentPlayer.getCash()+ " wealth: "+_currentPlayer.getTotalWealth());
				if(_currentPlayer.isInJail()){
					_currentPlayer.incrementTurnsInJail();
					System.out.println(_currentPlayer.getTurnsInJail()+" Turn In Jail");
				}
				int roll = _dice.rollDice();
				boolean wasDoubles=_dice.wasDoubles();
				if(wasDoubles){
					System.out.println("wasDoubles");
					_doublesInRow++;
					if(_currentPlayer.isInJail()){
						_currentPlayer.getOutOfJail();
						wasDoubles=false;
					}
				}
				if(_doublesInRow==3){
					System.out.println("out of jail");
					sendPlayerToJail(_currentPlayer);
					endTurn();
				}
				else{
					if(_currentPlayer.isInJail()){
						System.out.println("In Jail");
						//System.out.println(_currentPlayer+" is in jail");
						if(_currentPlayer.getTurnsInJail()==3){
							System.out.println("Out of jail after 3 turns");
							_currentPlayer.getOutOfJail();

							transferMoney(_currentPlayer, null, 50);
						}
						else{
							System.out.println("Trying to get out of jail");
							tryGettingOutOfJail(_currentPlayer);
						}
					}
					if(_currentPlayer.isInJail()==false){
						Space s=movePlayer(_currentPlayer, roll);
						System.out.println("Landed on "+s);
						s.react(this, _currentPlayer);
					}
					tryUnmortgaging(_currentPlayer);
					tryTrading(_currentPlayer);
					tryBuilding(_currentPlayer);

					if(!wasDoubles){
						System.out.println("turn over");
						endTurn();
					}
				}

			}					
		} catch (Exception e) {
			System.err.println("ERROR: "+e.getMessage());
			e.printStackTrace();
			return;
		}

	}
	
	/**
	 * 
	 * @return game's dice
	 */
	Dice getDice() {
		return _dice;
	}
	
	/**
	 * 
	 * @param stacked dice, used for testing
	 */
	void setDice(Dice dice) {
		_dice=dice;
	}

	/**
	 * 
	 * @return banker
	 */
	GamePlayer getBanker() {
		return _banker;
	}
	
	/** 
	 * 
	 * @return game data
	 */
	public GameData getGameData(){
		return _gameData;
	}

	/**
	 * Ends the current turn, saves data if necessary, makes it the next person's turn.
	 */
	void endTurn(){
		if(_currentTurn==_maxNumTurns-1) {
			tieGame();
		}
		if(_currentTurn%_turnsPerTimeStamp==0){
			_gameData.addNewTime();
			for(GamePlayer player: _players){
				_gameData.setWealthAtTime(player.getPlayer().ID, player.getCash(), player.getTotalWealth());
				for(Property prop: player.getProperties()){
					_gameData.setPropertyAtTime(prop.Name, player.getPlayer().ID, prop.getNumHouses(), prop.getPersonalRevenueWith(),prop.getPersonalRevenueWithout(),prop.getTotalRevenueWithHouses(),prop.getTotalRevenueWithoutHouses(), prop.getMortgagedState());
				}
			}

		}
		_currentTurn++;
		_currentPlayer = _players.get(_currentTurn%_numPlayers);
		_doublesInRow=0;
	}


	/**
	 * triggers the end of the game and cleans it up
	 */
	private void endGame(){
		_gameData.setWinner(_players.get(0).getPlayer().ID);
		_playing=false;
		_gameData.addNewTime();
		for(GamePlayer player: _players){
			_gameData.setWealthAtTime(player.getPlayer().ID, player.getCash(), player.getTotalWealth());
			for(Property prop: player.getProperties()){
				_gameData.setPropertyAtTime(prop.Name, player.getPlayer().ID, prop.getNumHouses(), prop.getPersonalRevenueWith(),prop.getPersonalRevenueWithout(),prop.getTotalRevenueWithHouses(),prop.getTotalRevenueWithoutHouses(), prop.getMortgagedState());
			}
		}
	}
	
	/**
	 * ends game with a tie
	 */
	private void tieGame() {
		_gameData.setWinner(-1);
		_playing=false;
		_gameData.addNewTime();
		for(GamePlayer player: _players){
			_gameData.setWealthAtTime(player.getPlayer().ID, player.getCash(), player.getTotalWealth());
			for(Property prop: player.getProperties()){
				_gameData.setPropertyAtTime(prop.Name, player.getPlayer().ID, prop.getNumHouses(), prop.getPersonalRevenueWith(),prop.getPersonalRevenueWithout(),prop.getTotalRevenueWithHouses(),prop.getTotalRevenueWithoutHouses(), prop.getMortgagedState());
			}
		}
	}

	/**
	 * Moves player a given number of spaces
	 * @param player
	 * @param numSpaces
	 * @return space player lands on
	 */
	Space movePlayer (GamePlayer player, int numSpaces){
		int curr=player.getPosition();
		int next =(curr+numSpaces)%40;
		if(curr>next){
			player.addMoney(200);
			System.out.println(player+" collects $200 passing Go");
		}
		player.setPosition(next);
		return _board.get(player.getPosition());
	}

	/**
	 * moves player to a given space
	 * @param player
	 * @param spaceName
	 * @return space player lands on
	 */
	Space movePlayer (GamePlayer player, String spaceName){
		int curr=player.getPosition();
		int next =_board.get(spaceName).getPosition();
		//if we have to go around the board
		if(spaceName.equals("jail")==false&&curr>next){
			player.addMoney(200);
			System.out.println(player+" collects $200 passing Go");
		}
		player.setPosition(next);
		return _board.get(spaceName);
	}

	/**
	 * sends player to jail
	 * @param player
	 */
	void sendPlayerToJail(GamePlayer player){
		movePlayer(player, "jail");
		player.goToJail();

	}

	/**
	 * Transfers from a player to another
	 * @param payer
	 * @param receiver (bank if null)
	 * @param amountOwed
	 * @return if the player was bankrupted
	 * @throws Exception 
	 */
	boolean transferMoney(GamePlayer payer, GamePlayer receiver, int amountOwed) throws Exception{
		int actuallyPaid=payer.payMoney(amountOwed);
		if(receiver!=null){
			receiver.addMoney(actuallyPaid);
		} 
		else if(receiver!=_banker) {
			addFreeParkingMoney(actuallyPaid);
			System.out.println(actuallyPaid +" was added to free parking");
		}

		if(actuallyPaid<amountOwed){
			bankruptPlayer(payer,receiver);
			return true;
		}
		return false;
	}

	/**
	 * called if players runs out of assets and loses
	 * removes player and gives assets either to bank or to 
	 * @param bankruptPlayer
	 * @param creditor
	 * @throws Exception 
	 */
	void bankruptPlayer(GamePlayer bankruptPlayer, GamePlayer creditor) throws Exception{
		System.out.println("BANKRUPT: "+bankruptPlayer +" by "+creditor);
		if(creditor!=null && creditor!=_banker){
			for(Property p: bankruptPlayer.getProperties()){
				creditor.gainProperty(p);
			}
		}
		else{
			for(Property p: bankruptPlayer.getProperties()){
				p.setOwner(null);
				p.setMortgagedState(false);
				p.setMonopolyState(false);
				auction(p);
			}
		}
		_players.remove(bankruptPlayer);
		_numPlayers--;
		if(_numPlayers==1){
			endGame();
		}
	}

	/**
	 * 
	 * @param position
	 * @return space at that position
	 */
	Space getSpace(int position){
		return _board.get(position);
	}

	private void tryTrading(GamePlayer player) throws Exception{
		player.tryTrading();
	}

	private void tryBuilding(GamePlayer player) throws Exception{
		player.tryBuilding();
	}

	private void tryUnmortgaging(GamePlayer player) throws Exception{
		player.tryUnmortgaging();
	}

	private void tryGettingOutOfJail(GamePlayer player) throws Exception{
		player.tryGettingOutofJail();
	}

	void auction(Property property) throws Exception{
		if(_auctions==false) {
			return;
		}
		GamePlayer maxPlayer=null;
		double maxBid=0;
		double secondBid=0;
		for(GamePlayer p: _players) {
			double bid = p.getPropertyValue(property);
			System.out.println(p +" bidded "+bid);
			if(bid>maxBid) {
				secondBid=maxBid+1;
				maxBid=bid;
				maxPlayer=p;
			}
			else if(bid>=secondBid) {
				secondBid=bid+1;
			}
			//if they have same bid, have the player with more wealth bid 1 more dollar
			else if(bid==maxBid&&p.getTotalWealth()>maxPlayer.getTotalWealth()) {
				secondBid=maxBid+1;
				maxBid=bid+1;
				maxPlayer=p;
			}
		}
		System.out.println(maxPlayer + " paid "+secondBid);
		maxPlayer.payMoney((int)secondBid);
		maxPlayer.gainProperty(property);
	}

	/**
	 * 
	 * @return community chest deck
	 */
	CommunityChestDeck getCommunityChestDeck(){
		return _comChest;
	}

	/**
	 * 
	 * @return chance deck
	 */
	ChanceDeck getChanceDeck(){
		return _chance;
	}

	/**
	 * adds money to the freeparking pot
	 * @param extraMoney
	 */
	void addFreeParkingMoney(int extraMoney){
		_freeParkingMoney+=extraMoney;
	}

	/**
	 * resets the free parking money
	 * @return amount of money in free parking before it's resetted
	 */
	void giveFreeParkingMoney(GamePlayer player){
		if(_defaultFP>=0) {
			System.out.println(player + " won jackpot of $"+_freeParkingMoney);
			player.addMoney(_freeParkingMoney);
			_freeParkingMoney=_defaultFP;
		}
	}
	
	/**
	 * 
	 * @return if you're playing with double on Go
	 */
	boolean playWithDoubleOnGo(){
		return _doubleOnGo;
	}

	/**
	 * 
	 * @param player
	 * @return list of other players
	 */
	List<GamePlayer> getOtherPlayers(GamePlayer player){
		List<GamePlayer> result = new ArrayList<>();
		for(GamePlayer p: _players){
			if (p!=player){
				result.add(p);
			}
		}
		return result;
	}
	
	double getNumberofUnownedProperties() {
		double num=28;
		for(GamePlayer player: _players){
			num-=player.getProperties().size();
		}
		return num;
	}
	
	//TODO added by Frances ... take out if you think it's bad, but I will need something like this for my code
	public Game copy(){
		return new Game(_seed, _maxNumTurns,_defaultFP,_doubleOnGo,_auctions,(Player[])_players.toArray());
	}

}
