package edu.brown.cs32.MFTG.tournament;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.brown.cs32.MFTG.monopoly.GameData;

public class DataProcessor {

	//TODO make this class a bit more useful than it currently is
	
	/**
	 * Computes the composite value
	 * @param data
	 * @return
	 */
	public GameData aggregate(List<List<GameData>> data){
		return null;
	}
	
	/**
	 * Checks to make sure that the games at confirmationIndices are identical
	 * (they should be because they were fed the same seed)
	 * @param data the game data to check
	 * @param confirmationIndices the indices of games that should be identical
	 * @return whether the game data is corrupted
	 */
	public static boolean isCorrupted(List<List<GameData>> data, List<Integer> confirmationIndices){
		boolean corrupted = false;
		List<GameData> setData;
		
		for(int i = 0; i < confirmationIndices.size(); i++){
			setData = data.get(confirmationIndices.get(i));
			for(int j = 1; j < setData.size(); j++){
				if(!setData.get(j-1).equals(setData.get(j))){
					corrupted = true;
				}
			}
		}
		
		return corrupted;
	}
	
	/**
	 * Generates a list of seeds for each of the games for each of the players
	 * Seeds are the same for the game indices stored in confirmationIndices
	 * ... these games will later be checked for consistency
	 * @param numGames the number of games for each module to play
	 * @param numPlayers the number of modules
	 * @param confirmationIndices the indices where seeds should be the same
	 * @return a list of seeds for each module
	 */
	public static List<List<Long>> generateSeeds(int numGames, int numPlayers, List<Integer> confirmationIndices){
		Random rand = new Random();
		int confirmationListIndex = 0;
		int confirmationListSize = confirmationIndices.size();
		long seed;
		List<Long> seedValues;
		List<List<Long>> allSeedValues = new ArrayList<>();
		
		for(int i = 0; i < numGames; i++){
			seedValues = new ArrayList<>();
			
			if(confirmationListIndex < confirmationListSize && i == confirmationIndices.get(confirmationListIndex)){
				//all games at this index should have the same seed
				confirmationListIndex++;
				seed = rand.nextLong();
				for(int j = 0; j < numPlayers; j++){
					seedValues.add(seed);
				}
				
			} else {
				//all games at this index should have different seeds
				for(int j = 0; j < numPlayers; j++){
					seed = rand.nextLong();
					seedValues.add(seed);
				}
			}

			allSeedValues.add(seedValues);
		}
		
		return allSeedValues;
	}
	
	/**
	 * Generates the indices for games to check
	 * @param numGames number of games that each module will play
	 * @param confirmationPercentage value between 0 and 1, % of each module's games to check
	 * @return an in order list of game indices to check
	 */
	public static List<Integer> generateConfirmationIndices(int numGames, double confirmationPercentage){
		int numConfirmationGames = (int)(Math.ceil(numGames * confirmationPercentage)); //round up
		List<Integer> confirmationIndices = new ArrayList<>();
		Random rand = new Random();
		int i = 0;
		int index;
		while(i < numConfirmationGames){
			index = rand.nextInt(numGames);
			if(!confirmationIndices.contains(index)){
				confirmationIndices.add(index);
				i++;
			}
		}
		Collections.sort(confirmationIndices);
		return confirmationIndices;
	}
}