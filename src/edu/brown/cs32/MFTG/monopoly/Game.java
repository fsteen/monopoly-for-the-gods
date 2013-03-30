package edu.brown.cs32.MFTG.monopoly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import edu.brown.cs32.MFTG.monopoly.GamePlayer.HouseSellingComparator;
import edu.brown.cs32.MFTG.monopoly.GamePlayer.MortgageComparator;

public class Game implements Runnable{
	private ArrayList<GamePlayer> _players;
	private GamePlayer _currentPlayer;
	private int _numPlayers, _currentTurn,_freeParkingMoney, _defaultFP, _numHousesLeft, _numHotelsLeft;
	private Board _board;
	private Dice _dice;
	private static final int TURNS_PER_TIMESTAMP=20;
	private boolean _playing, _doubleOnGo, _auctions;
	private int _doublesInRow;
	private CommunityChestDeck _comChest;
	private ChanceDeck _chance;
	private GameData _gameData;

	/**
	 * Constructs a new game
	 * @param freeParking, if negative means nothing happens when landed on
	 * @param auctions
	 * @param players
	 */
	public Game(int freeParking,boolean doubleOnGo, boolean auctions, Player...players) {
		for(Player p:players){
			_numPlayers++;
			_players.add(new GamePlayer(p));
		}
		Collections.shuffle(_players);
		_currentPlayer = _players.get(0);
		_currentTurn=0;
		_defaultFP=freeParking;
		_freeParkingMoney = _defaultFP;
		_doubleOnGo=doubleOnGo;
		
		_auctions=auctions;
		_numHousesLeft=32;
		_numHotelsLeft=12;
		
		_board = new Board();
		_dice = new Dice();
		
		_playing = true;
	
		
		_doublesInRow=0;
		
		_comChest=new CommunityChestDeck();
		_chance= new ChanceDeck();
		
		_gameData=new GameData(_numPlayers);
		
					
	}

	@Override
	public void run() {
		while(_playing){
			if(_currentPlayer.isInJail()){
				_currentPlayer.incrementTurnsInJail();
			}
			int roll = _dice.rollDice();
			boolean wasDoubles=_dice.wasDoubles();
			if(wasDoubles){
				_doublesInRow++;
				if(_currentPlayer.isInJail()){
					_currentPlayer.getOutOfJail();
					wasDoubles=false;
				}
			}
			if(_doublesInRow==3){
				sendPlayerToJail(_currentPlayer);
				endTurn();
			}
			else{
				if(_currentPlayer.isInJail()){
					if(_currentPlayer.getTurnsInJail()==3){
						_currentPlayer.getOutOfJail();
						transferMoney(_currentPlayer, null, 50);
					}
					else{
						tryGettingOutOfJail(_currentPlayer);
					}
				}
				if(_currentPlayer.isInJail()==false){
					Space s=movePlayer(_currentPlayer, roll);
					s.react(this, _currentPlayer);
				}
				tryUnmortgaging(_currentPlayer);
				tryTrading(_currentPlayer);
				tryBuilding(_currentPlayer);
				
				if(!wasDoubles){
					endTurn();
				}
			}

		}
		
	}
	
	public GameData getGameData(){
		return _gameData;
	}
	
	/**
	 * Ends the current turn, saves data if necessary, makes it the next person's turn.
	 */
	void endTurn(){
		if(_currentTurn%TURNS_PER_TIMESTAMP==0){
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
	 */
	boolean transferMoney(GamePlayer payer, GamePlayer receiver, int amountOwed){
		int actuallyPaid=payer.payMoney(amountOwed);
		if(receiver!=null){
			receiver.addMoney(actuallyPaid);
		} else {
			addFreeParkingMoney(actuallyPaid);
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
	 */
	void bankruptPlayer(GamePlayer bankruptPlayer, GamePlayer creditor){
		if(creditor!=null){
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
	
	private void tryTrading(GamePlayer player){
		//TODO
	}
	
	private void tryBuilding(GamePlayer player){
		//TODO
	}
	
	private void tryUnmortgaging(GamePlayer player){
		player.tryUnmortgaging();
	}
	
	private void tryGettingOutOfJail(GamePlayer player){
		//TODO
	}
	
	void auction(Property property){
		//TODO
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

}
