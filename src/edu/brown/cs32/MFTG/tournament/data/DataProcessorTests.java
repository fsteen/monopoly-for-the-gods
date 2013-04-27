package edu.brown.cs32.MFTG.tournament.data;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;


public class DataProcessorTests {

	@Test
	public void generateConfirmationIndicesTest(){
		List<Integer> confirmationIndices1 = DataProcessor.generateConfirmationIndices(100, .10);
		assertTrue(confirmationIndices1.size() == 10);
		for(int i = 1; i < confirmationIndices1.size(); i++){
			assertTrue(confirmationIndices1.get(i-1) < confirmationIndices1.get(i));
		}
//		System.out.println(confirmationIndices1);
		
		List<Integer> confirmationIndices2 = DataProcessor.generateConfirmationIndices(10, .09);
		assertTrue(confirmationIndices2.size() == 1);
		for(int i = 1; i < confirmationIndices2.size(); i++){
			assertTrue(confirmationIndices2.get(i-1) < confirmationIndices2.get(i));
		}
//		System.out.println(confirmationIndices2);
	}
	
	@Test
	public void generateSeedsTest(){
		int numGames1 = 100;
		int numPlayers1 = 4;
		List<Integer> confirmationIndices1 = DataProcessor.generateConfirmationIndices(numGames1, .10);
		List<List<Long>> seeds1 = DataProcessor.generateSeeds(numGames1, numPlayers1, confirmationIndices1);
		
		long seed1;
		for(int i = 0; i < confirmationIndices1.size(); i++){
			seed1 = seeds1.get(confirmationIndices1.get(i)).get(0);
			for(int j = 0; j < numPlayers1; j++){
				assertTrue(seeds1.get(confirmationIndices1.get(i)).get(j) == seed1);
			}
		}
//		System.out.println(seeds1);
	}
}
