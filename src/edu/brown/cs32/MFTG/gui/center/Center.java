package edu.brown.cs32.MFTG.gui.center;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.gameboard.Board;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.monopoly.PlayerWealthData;
import edu.brown.cs32.MFTG.tournament.data.PlayerWealthDataReport;

public class Center extends JPanel {

	public ProfitGraph _profitGraph;
	public SliderPanel _sliderPanel;
	public ButtonPanel _buttonPanel;
	private Board _board;
	
	public Center(Board board) {
		super();
		_board = board;
		this.setSize(9*Constants.WIDTH, 9*Constants.WIDTH);
		this.setLayout(new BorderLayout());
		
		_buttonPanel = new ButtonPanel();
		this.add(_buttonPanel, BorderLayout.NORTH);
		_profitGraph = new ProfitGraph();
		this.add(_profitGraph, BorderLayout.CENTER);
		_sliderPanel = new SliderPanel();
		this.add(_sliderPanel, BorderLayout.SOUTH);
		
		JButton setHeuristics = new JButton();
		setHeuristics.addActionListener(new SetHeuristicsListener());
		this.add(setHeuristics, BorderLayout.WEST);
	}
	
	
	public void setWealthData(List<PlayerWealthDataReport> data) {
		_profitGraph.setWealthData(data);
	}
	
	public List<Double> getSliderInfo () {
		return _sliderPanel.getSliderInfo();
	}


	public List<Integer> getMinCash() {
		return _profitGraph.getMinCash();
	}


	public void setButtonChoices(Player player) {
		_buttonPanel.setButtonChoices(player);
	}
	
	private class SetHeuristicsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(_board.setHeuristics());
		}
		
	}
	
}
