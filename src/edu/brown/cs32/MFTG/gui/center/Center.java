package edu.brown.cs32.MFTG.gui.center;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.gameboard.Board;
import edu.brown.cs32.MFTG.monopoly.Player;
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
		this.setBackground(Constants.BACKGROUND_COLOR);
		
		this.setLayout(null);
		_buttonPanel = new ButtonPanel();
		this.add(_buttonPanel);
		_profitGraph = new ProfitGraph();
		this.add(_profitGraph);
		_sliderPanel = new SliderPanel();
		this.add(_sliderPanel);
		
		ViewPanel viewPanel = new ViewPanel(board);
		this.add(viewPanel);
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
	

	
}
