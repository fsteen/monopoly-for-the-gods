package edu.brown.cs32.MFTG.tournament.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import edu.brown.cs32.MFTG.monopoly.GameData;
import edu.brown.cs32.MFTG.monopoly.PlayerWealthData;
import edu.brown.cs32.MFTG.monopoly.PropertyData;
import edu.brown.cs32.MFTG.monopoly.TimeStamp;

public class DataProcessor {	
	/**
	 * Aggregates the GameData into a GameDataAccumulator, mostly by averaging fields
	 * @param data the GameData to combine
	 * @param numDataPoints should be less than the number of TimeStamps //TODO fix
	 * @return the GameDataAccumulator 
	 */
	public static GameDataAccumulator aggregate(List<GameData> data, int numDataPoints){
		GameDataAccumulator overall = new GameDataAccumulator(numDataPoints, data.get(0)._numPlayers);
		for(GameData d : data){
			if(d != null){
				combineGameData(overall, d);
				overall.gameFinished();
				overall.addPlayerWin(d.getWinner());
			}
		}
		overall.average();
		return overall;
	}
	
	/**
	 * Go through the time stamps for a specific game
	 * and combine them with the overall time stamps
	 * @param overall the overall time stamps
	 * @param specific the specific game time stamps
	 */
	private static void combineGameData(GameDataAccumulator overall, GameData specific){
		int specificIndex = specific.getData().size() - 1;
		int numDataPoints = overall.timeStamps.size();
		int stepSize;
		
		//we go backwards so that we ensure that we always get the last time stamp
		//we don't care so much about getting the first stamp		
		TimeStampAccumulator overallTimeStamp;
		for(int i = numDataPoints - 1; i >= 0; i--){	
			overallTimeStamp = overall.timeStamps.get(i);
			for(PlayerWealthData w : specific.getData().get(specificIndex).getWealthData()){
				overallTimeStamp.putWealthData(w);
			}
			stepSize = (int)Math.round(((double)specificIndex)/i);
			specificIndex -= stepSize;
		}
		//overall game data
		for(TimeStamp t : specific.getData()){
			for(PropertyData p : t.getPropertyData()){
				overall.putPropertyData(p);
			}
		}
	}

	/**
	 * Combines all of the accumulators by combining them into the first
	 * Note that because some of the combining is not transitive (ie appending) order DOES matter
	 * @param accumulators
	 * @return the combined accumulator
	 * @throws DataAccumulationException 
	 */
	public static GameDataAccumulator combineAccumulators(GameDataAccumulator...accumulators){
		GameDataAccumulator first = accumulators[0];
		for(int i = 1; i < accumulators.length; i++){
			first.combineWith(accumulators[i]);
		}
		return first;
	}
	
	/**
	 * Checks to make sure that the games at confirmationIndices are identical
	 * (they should be because they were fed the same seed)
	 * @param data the game data to check
	 * @param confirmationIndices the indices of games that should be identical
	 * @return whether the game data is corrupted
	 */
	public static boolean isCorrupted(List<GameDataReport> data, List<Integer> confirmationIndices){
		boolean corrupted = false;
		
		GameDataReport current = data.get(0);
		for(Integer i : confirmationIndices){
			for(GameDataReport d : data){
				if((current == null && d != null) || (d == null && current != null)
						|| i >= d._winList.size() || i >= current._winList.size() || 
						d._winList.get(i) != current._winList.get(i)){
					corrupted = true;
				}
				current = d;
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
	public static List<List<Long>> generateSeeds(int numGames, int numPlayers, List<Integer> confirmationIndices, Random rand){
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
	public static List<Integer> generateConfirmationIndices(int numGames, double confirmationPercentage, Random rand){
		int numConfirmationGames = (int)(Math.ceil(numGames * confirmationPercentage)); //round up
		List<Integer> confirmationIndices = new ArrayList<>();
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