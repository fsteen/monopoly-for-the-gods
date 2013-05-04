/*package edu.brown.cs32.MFTG.gui;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.Player;



public class Music {
	private String _filename;
	private Player _player; 
	private MonopolyGui _main;
	private boolean _isInGame, _stopped;

	// constructor that takes the name of an MP3 file
	public Music(String filename, MonopolyGui main, boolean isInGame) {
		_filename = filename;
		_player = null;
		_main=main;
		_isInGame=isInGame;
		_stopped=false;
	}

	public void close() { 
		if (_player != null) {
			_player.close(); 
			_stopped=true;
		}
		
	}

	// play the MP3 file to the sound card
	public void play() {
		try {
			_player = new Player( new BufferedInputStream(new FileInputStream(_filename)));
		}
		catch (Exception e) {
			System.err.println("ERROR: "+e.getMessage());
		}

		// run in new thread to play in background
		new Thread() {
			public void run() {
				try { 
					_player.play(); 
					if(_stopped==false){
						if(_isInGame)_main.playNextInGameSong();
						else _main.playNextOutOfGameSong();
					}

				}
				catch (Exception e) {
					System.err.println("ERROR: "+e.getMessage()); 
				}
			}
		}.start();

	}

}*/