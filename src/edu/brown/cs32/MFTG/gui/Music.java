//package edu.brown.cs32.MFTG.gui;
//
//import java.io.BufferedInputStream;
//import java.io.FileInputStream;
//
//import javazoom.jl.player.FactoryRegistry;
//import javazoom.jl.player.Player;
//
//
//public class Music {
//	private String _filename;
//	private Player _player; 
//
//	// constructor that takes the name of an MP3 file
//	public Music(String filename) {
//		_filename = filename;
//		_player = null;
//	}
//
//	public void close() { 
//		if (_player != null) 
//			_player.close(); 
//	}
//
//	// play the MP3 file to the sound card
//	public void play() {
//		try {
//			FileInputStream fis     = new FileInputStream(_filename);
//			BufferedInputStream bis = new BufferedInputStream(fis);
//			System.out.println("hi");
//			FactoryRegistry.systemRegistry().createAudioDevice();
//			_player = new Player(bis);
//		}
//		catch (Exception e) {
//			System.out.println("Problem playing file " + _filename);
//			System.out.println(e);
//		}
//
//		// run in new thread to play in background
//		new Thread() {
//			public void run() {
//				try { 
//					_player.play(); 
//				}
//				catch (Exception e) {
//					System.out.println(e); 
//				}
//			}
//		}.start();
//
//	}
//
//
//	// test client
//	public static void main(String[] args) {
//		String filename = args[0];
//		Music mp3 = new Music(filename);
//		mp3.play();
//
//		// when the computation is done, stop playing it
//		mp3.close();
//
//	}
//
//}
