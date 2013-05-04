package edu.brown.cs32.MFTG.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.brown.cs32.MFTG.tournament.Record;

public class RecordsSidePanel extends JPanel{
	private JLabel _numMatches, _numMatchesWon,  _numSets, _numSetsWon,  _numGames, _numGamesWon, _numTurnsTaken;
	private JLabel _matchWinningPercentage, _setWinningPercentage, _gameWinningPercentage;
	private JLabel _secondsPlayed;
	private JLabel _numMatchesRecord, _numMatchesWonRecord,  _numSetsRecord, _numSetsWonRecord,  _numGamesRecord, _numGamesWonRecord, _numTurnsTakenRecord;
	private JLabel _matchWinningPercentageRecord, _setWinningPercentageRecord, _gameWinningPercentageRecord;
	private JLabel _secondsPlayedRecord;
	public RecordsSidePanel() {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		this.setBorder(new EmptyBorder(3,3,3,3));
		
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.WHITE);
		JLabel header= new JLabel("Records for this Profile");
		topPanel.add(header);

		_numMatches = new JLabel("Number of Matches: ");
		_numMatchesRecord = new JLabel("");
		_numMatchesWon = new JLabel("Number of Matches Won: ");
		_numMatchesWonRecord = new JLabel("");
		_matchWinningPercentage = new JLabel("Percentage of Matches Won: ");
		_matchWinningPercentageRecord = new JLabel("");
		JLabel break1 = new JLabel("");
		JLabel break2 = new JLabel("");
		_numSets = new JLabel("Number of Sets: ");
		_numSetsRecord = new JLabel("");
		_numSetsWon = new JLabel("Number of Sets Won: ");
		_numSetsWonRecord = new JLabel("");
		_setWinningPercentage = new JLabel("Percentage of Sets Won: ");
		_setWinningPercentageRecord = new JLabel("");
		JLabel break3 = new JLabel("");
		JLabel break4 = new JLabel("");
		_numGames = new JLabel("Number of Games: ");
		_numGamesRecord = new JLabel("");
		_numGamesWon = new JLabel("Number of Games Won: ");
		_numGamesWonRecord = new JLabel("");
		_gameWinningPercentage = new JLabel("Percentage of Gamets Won: ");
		_gameWinningPercentageRecord = new JLabel("");
		JLabel break5 = new JLabel("");
		JLabel break6 = new JLabel("");
		_secondsPlayed = new JLabel("Seconds Played: ");
		_secondsPlayedRecord = new JLabel("");
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(0,1));
		leftPanel.setBackground(Color.WHITE);
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(0,1));
		rightPanel.setBackground(Color.WHITE);
		
		leftPanel.add(_numMatches);
		rightPanel.add(_numMatchesRecord);
		leftPanel.add(_numMatchesWon);
		rightPanel.add(_numMatchesWonRecord);
		leftPanel.add(_matchWinningPercentage);
		rightPanel.add(_matchWinningPercentageRecord);
		leftPanel.add(break1);
		rightPanel.add(break2);
		leftPanel.add(_numSets);
		rightPanel.add(_numSetsRecord);
		leftPanel.add(_numSetsWon);
		rightPanel.add(_numSetsWonRecord);
		leftPanel.add(_setWinningPercentage);
		rightPanel.add(_setWinningPercentageRecord);
		leftPanel.add(break3);
		rightPanel.add(break4);
		leftPanel.add(_numGames);
		rightPanel.add(_numGamesRecord);
		leftPanel.add(_numGamesWon);
		rightPanel.add(_numGamesWonRecord);
		leftPanel.add(_gameWinningPercentage);
		rightPanel.add(_gameWinningPercentageRecord);
		leftPanel.add(break5);
		rightPanel.add(break6);
		leftPanel.add(_secondsPlayed);
		rightPanel.add(_secondsPlayedRecord);

		this.add(topPanel, BorderLayout.NORTH);
		this.add(leftPanel,BorderLayout.WEST);
		this.add(rightPanel,BorderLayout.EAST);

	}
	
	/**
	 * sets text based on a record
	 * @param r
	 */
	public void setRecords(Record r) {
		if(r==null) {
			return;
		}
		_numMatchesRecord.setText(String.valueOf(r.get_numMatches()));
		_numMatchesWonRecord.setText(String.valueOf(r.get_numMatchesWon()));
		_matchWinningPercentageRecord.setText(String.valueOf(r.get_matchWinningPercentage()));

		_numSetsRecord.setText(String.valueOf(r.get_numSets()));
		_numSetsWonRecord.setText(String.valueOf(r.get_numSets()));
		_setWinningPercentageRecord.setText(String.valueOf(r.get_setWinningPercentage()));

		_numGamesRecord.setText(String.valueOf(r.get_numGames()));
		_numGamesWonRecord.setText(String.valueOf(r.get_numGamesWon()));
		_gameWinningPercentageRecord.setText(String.valueOf(r.get_gameWinningPercentage()));

		_secondsPlayedRecord.setText(String.valueOf(r.get_secondsPlayed()));
	}

}
