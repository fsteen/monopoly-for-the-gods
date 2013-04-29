package edu.brown.cs32.MFTG.gui;

import java.io.BufferedInputStream;
import java.io.FileInputStream;


public class Music implements Runnable{
	private boolean _playing;
	public Music() {
		_playing = true;
	}
	
	public stop() {
		_playing=false;
	}
	public void run() {
		while (_playing) {
			try
            {
               FileInputStream fis     = new FileInputStream(filename);
               BufferedInputStream bis = new BufferedInputStream(fis);
               player = new Player(bis);
               
               mp3Thread = new Thread() 
               {
                  public void run() 
                  { try { player.play(); } catch (Exception e) { System.out.println(e); } }
               };
            
               mp3Thread.start();
               mp3Thread.join();
            } catch (Exception e) { System.out.println(e); }
		}
    }

}
