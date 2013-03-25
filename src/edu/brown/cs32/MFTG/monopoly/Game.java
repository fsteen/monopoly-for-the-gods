package edu.brown.cs32.MFTG.monopoly;

import java.util.ArrayList;

public class Game {
	private ArrayList<GamePlayer> _players;
	private int _numPlayers;
	private GamePlayer _currentPlayer;
	private int _currentTurn,_freeParkingMoney, _defaultFP, _numHousesLeft, _numHotelsLeft;
	private boolean _auctions;
	private Space[] _board;

	public Game(int freeParking,boolean auctions, Player...players) {
		for(Player p:players){
			_numPlayers++;
			_players.add(new GamePlayer(p));
		}
		_currentPlayer = _players.get((int) (Math.random()*_numPlayers));
		_currentTurn=0;
		_defaultFP=freeParking;
		_freeParkingMoney = _defaultFP;
		
		_auctions=auctions;
		_numHousesLeft=32;
		_numHotelsLeft=12;
		
		_board = new Space[36];
		
		
		
	}

}
