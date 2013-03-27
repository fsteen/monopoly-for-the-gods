package edu.brown.cs32.MFTG.monopoly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Game implements Runnable{
	private ArrayList<GamePlayer> _players;
	private int _numPlayers;
	private GamePlayer _currentPlayer;
	private int _currentTurn,_freeParkingMoney, _defaultFP, _numHousesLeft, _numHotelsLeft;
	private boolean _auctions;
	private Board _board;
	private Dice _dice;
	private static final int TURNS_PER_TIMESTAMP=5;
	private boolean _playing;
	private int _doublesInRow;
	private CommunityChestDeck _comChest;
	private ChanceDeck _chance;

	/**
	 * Constructs a new game
	 * @param freeParking, if negative means nothing happens when landed on
	 * @param auctions
	 * @param players
	 */
	public Game(int freeParking,boolean auctions, Player...players) {
		for(Player p:players){
			_numPlayers++;
			_players.add(new GamePlayer(p));
		}
		Collections.shuffle(_players);
		_currentPlayer = _players.get(0);
		_currentTurn=0;
		_defaultFP=freeParking;
		_freeParkingMoney = _defaultFP;
		
		_auctions=auctions;
		_numHousesLeft=32;
		_numHotelsLeft=12;
		
		_board = new Board();
		_dice = new Dice();
		
		_playing = true;
	
		
		_doublesInRow=0;
		
		_comChest=new CommunityChestDeck();
		_chance= new ChanceDeck();
		
					
	}

	@Override
	public void run() {
		while(_playing){
			int roll = _dice.rollDice();
			boolean wasDoubles=_dice.wasDoubles();
			if(wasDoubles){
				_doublesInRow++;
			}
			if(_doublesInRow==3){
				sendPlayerToJail(_currentPlayer);
				endTurn();
			}
			else{
				if(_currentPlayer.isInJail()){
					//decide whether to get out
				}
				else{
					Space s=movePlayer(_currentPlayer, roll);
					s.react(this, _currentPlayer);
				}

				tryTrading(_currentPlayer);
				tryBuilding(_currentPlayer);
				
				if(!wasDoubles){
					endTurn();
				}
			}

		}
		
	}
	
	/**
	 * Ends the current turn, saves data if necessary, makes it the next person's turn.
	 */
	public void endTurn(){
		if(_currentTurn%TURNS_PER_TIMESTAMP==0){
			//TODO: add data
		}
		_currentTurn++;
		_currentPlayer = _players.get(_currentTurn%_numPlayers);
		_doublesInRow=0;
	}
	
	
	/**
	 * triggers the end of the game and cleans it up
	 */
	private void endGame(){
		_playing=false;
	}
	
	/**
	 * Moves player a given number of spaces
	 * @param player
	 * @param numSpaces
	 * @return space player lands on
	 */
	Space movePlayer (GamePlayer player, int numSpaces){
		player.movePlayer(numSpaces);
		return _board.get(player.getPosition());
	}
	
	/**
	 * moves player to a given space
	 * @param player
	 * @param spaceName
	 * @return space player lands on
	 */
	Space movePlayer (GamePlayer player, String spaceName){
		player.setPosition(_board.get(spaceName).getPosition());
		return _board.get(spaceName);
	}
	
	/**
	 * sends player to jail
	 * @param player
	 */
	void sendPlayerToJail(GamePlayer player){
		player.goToJail();
		
	}
	
	private void tryTrading(GamePlayer player){
		
	}
	
	private void tryBuilding(GamePlayer player){
		
	}
	
	void auction(Property property){
		
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
	 * 
	 * @return money in freeparking
	 */
	int getFreeParkingMoney(){
		return _freeParkingMoney;
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
	int resetFreeParkingMoney(){
		int temp = _freeParkingMoney;
		_freeParkingMoney=_defaultFP;
		return temp;
	}
	
	/**
	 * 
	 * @return if you're playing with free parking
	 */
	boolean playWithFreeParking(){
		return (_defaultFP>=0);
	}

}
