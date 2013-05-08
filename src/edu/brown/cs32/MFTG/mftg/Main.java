package edu.brown.cs32.MFTG.mftg;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import edu.brown.cs32.MFTG.tournament.Client;
import edu.brown.cs32.MFTG.tournament.HumanClient;

@SuppressWarnings("unused")
public class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*try {
			System.setErr(new PrintStream(new FileOutputStream("system_err.txt")));
			System.setOut(new PrintStream(new FileOutputStream("system_out.txt")));
		} catch (FileNotFoundException e) {
			System.exit(1);
		}*/
		boolean music=false;
		if(args.length>0&&args[0].equals("-music")){
			music=true;
		}
		new HumanClient(music);

	}

}
