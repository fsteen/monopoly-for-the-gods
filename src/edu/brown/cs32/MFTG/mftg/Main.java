package edu.brown.cs32.MFTG.mftg;

import edu.brown.cs32.MFTG.tournament.Client;
import edu.brown.cs32.MFTG.tournament.HumanClient;

@SuppressWarnings("unused")
public class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean music=false;
		if(args.length>0&&args[0].equals("-music")){
			music=true;
		}
		new HumanClient(music);

	}

}
