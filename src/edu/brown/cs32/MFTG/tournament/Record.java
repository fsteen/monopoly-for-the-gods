package edu.brown.cs32.MFTG.tournament;

/**
 * models a game record
 * @author jschvime
 *
 */
public class Record {
	private int _numMatches, _numMatchesWon,  _numSets, _numSetsWon,  _numGames, _numGamesWon, _numTurnsTaken;
	private double _matchWinningPercentage, _setWinningPercentage, _gameWinningPercentage;
	private int _secondsPlayed;
	public Record() {
		_numMatches=0;
		_numMatchesWon=0;
		_matchWinningPercentage=0;
		_numSets=0;
		_numSetsWon=0;
		_setWinningPercentage=0;
		_numGames=0;
		_numGamesWon=0;
		_gameWinningPercentage=0;
		
		_numTurnsTaken=0;
		_secondsPlayed=0;
	}
	
	/**
	 * adds a game to the record
	 * @param isWin
	 * @param numTurnsTaken
	 */
	public void addGame(boolean isWin, int numTurnsTaken) {
		_numGames++;
		if(isWin)_numGamesWon++;
		_gameWinningPercentage=((double)_numGamesWon)/_numGames;	
		_numTurnsTaken+=numTurnsTaken;
	}
	
	/**
	 * add set
	 * @param isWin
	 */
	public void addSet(boolean isWin) {
		_numSets++;
		if(isWin)_numSetsWon++;
		_setWinningPercentage=((double)_numSetsWon)/_numSets;	
	}
	
	/**
	 * set
	 * @param isWin
	 */
	public void addMatch(boolean isWin, int lengthOfGameInSeconds) {
		_numMatches++;
		if(isWin)_numMatchesWon++;
		_matchWinningPercentage=((double)_numMatchesWon)/_numMatches;	
		_secondsPlayed+=lengthOfGameInSeconds;
	}

	/**
	 * @return the _numMatches
	 */
	public int getNumMatches() {
		return _numMatches;
	}

	/**
	 * @param _numMatches the _numMatches to set
	 */
	public void setNumMatches(int _numMatches) {
		this._numMatches = _numMatches;
	}

	/**
	 * @return the _numMatchesWon
	 */
	public int getNumMatchesWon() {
		return _numMatchesWon;
	}

	/**
	 * @param _numMatchesWon the _numMatchesWon to set
	 */
	public void setNumMatchesWon(int _numMatchesWon) {
		this._numMatchesWon = _numMatchesWon;
	}

	/**
	 * @return the _numSets
	 */
	public int getNumSets() {
		return _numSets;
	}

	/**
	 * @param _numSets the _numSets to set
	 */
	public void setNumSets(int _numSets) {
		this._numSets = _numSets;
	}

	/**
	 * @return the _numSetsWon
	 */
	public int getNumSetsWon() {
		return _numSetsWon;
	}

	/**
	 * @param _numSetsWon the _numSetsWon to set
	 */
	public void setNumSetsWon(int _numSetsWon) {
		this._numSetsWon = _numSetsWon;
	}

	/**
	 * @return the _numGames
	 */
	public int getNumGames() {
		return _numGames;
	}

	/**
	 * @param _numGames the _numGames to set
	 */
	public void setNumGames(int _numGames) {
		this._numGames = _numGames;
	}

	/**
	 * @return the _numGamesWon
	 */
	public int getNumGamesWon() {
		return _numGamesWon;
	}

	/**
	 * @param _numGamesWon the _numGamesWon to set
	 */
	public void setNumGamesWon(int _numGamesWon) {
		this._numGamesWon = _numGamesWon;
	}

	/**
	 * @return the _numTurnsTaken
	 */
	public int getNumTurnsTaken() {
		return _numTurnsTaken;
	}

	/**
	 * @param _numTurnsTaken the _numTurnsTaken to set
	 */
	public void setNumTurnsTaken(int _numTurnsTaken) {
		this._numTurnsTaken = _numTurnsTaken;
	}

	/**
	 * @return the _matchWinningPercentage
	 */
	public double getMatchWinningPercentage() {
		return _matchWinningPercentage;
	}

	/**
	 * @param _matchWinningPercentage the _matchWinningPercentage to set
	 */
	public void setMatchWinningPercentage(double _matchWinningPercentage) {
		this._matchWinningPercentage = _matchWinningPercentage;
	}

	/**
	 * @return the _setWinningPercentage
	 */
	public double getSetWinningPercentage() {
		return _setWinningPercentage;
	}

	/**
	 * @param _setWinningPercentage the _setWinningPercentage to set
	 */
	public void setSetWinningPercentage(double _setWinningPercentage) {
		this._setWinningPercentage = _setWinningPercentage;
	}

	/**
	 * @return the _gameWinningPercentage
	 */
	public double getGameWinningPercentage() {
		return _gameWinningPercentage;
	}

	/**
	 * @param _gameWinningPercentage the _gameWinningPercentage to set
	 */
	public void setGameWinningPercentage(double _gameWinningPercentage) {
		this._gameWinningPercentage = _gameWinningPercentage;
	}

	/**
	 * @return the _secondsPlayed
	 */
	public int getSecondsPlayed() {
		return _secondsPlayed;
	}

	/**
	 * @param _secondsPlayed the _secondsPlayed to set
	 */
	public void setSecondsPlayed(int _secondsPlayed) {
		this._secondsPlayed = _secondsPlayed;
	}
	
	@Override
	public boolean equals(Object o){
		if (o == null || !(o instanceof Record))
			return false;
		
		Record that = (Record) o;
		
		return _numMatches == that.getNumMatches()
			   && _numMatchesWon == that.getNumMatchesWon()
			   && _numSets == that.getNumSets()
			   && _numSetsWon == that.getNumSetsWon()
			   && _numGames == that.getNumGames()
			   && _numGamesWon == that.getNumGamesWon()
			   && _numTurnsTaken == that.getNumTurnsTaken()
			   && _matchWinningPercentage == that.getMatchWinningPercentage()
			   && _setWinningPercentage == that.getSetWinningPercentage()
			   && _gameWinningPercentage == that.getGameWinningPercentage()
			   && _secondsPlayed == that.getSecondsPlayed();
			
	}
}
