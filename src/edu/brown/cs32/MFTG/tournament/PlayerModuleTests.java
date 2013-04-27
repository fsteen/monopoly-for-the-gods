package edu.brown.cs32.MFTG.tournament;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.brown.cs32.MFTG.monopoly.Player;

public class PlayerModuleTests {

	@Test
	public void threadingTest(){
		//NOTE : have GameRunnerFactory construct DummyGameRunners for this test
		
		List<Long> seeds = new ArrayList<>();
		seeds.add((long) 1);
		seeds.add((long) 1);
		seeds.add((long) 1);
		seeds.add((long) 1);
		seeds.add((long) 1);

		PlayerModule p = new PlayerModule("fakehost", -1);
		p.playGames(new ArrayList<Player>(), seeds);
		
	}
	
}
