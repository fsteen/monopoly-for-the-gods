package edu.brown.cs32.MFTG.gui.center;

import java.util.List;

import javax.swing.JPanel;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.gameboard.Board;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.monopoly.Player.Aggression;
import edu.brown.cs32.MFTG.monopoly.Player.Amount;
import edu.brown.cs32.MFTG.monopoly.Player.Balance;
import edu.brown.cs32.MFTG.monopoly.Player.Expense;
import edu.brown.cs32.MFTG.tournament.data.PlayerWealthDataReport;

public class Center extends JPanel {

	public ProfitGraph _profitGraph;
	public SliderPanel _sliderPanel;
	public ButtonPanel _buttonPanel;
	public ViewPanel _viewPanel;
	private Board _board;
	private WinnerPanelVertical _winnerPanel;
	
	public Center(Board board, int id) {
		super();
		_board = board;
		this.setSize(9*Constants.WIDTH, 9*Constants.WIDTH);
		this.setBackground(Constants.BACKGROUND_COLOR);
		
		this.setLayout(null);
		_buttonPanel = new ButtonPanel();
		this.add(_buttonPanel);
		_profitGraph = new ProfitGraph();
		_profitGraph.setToolTipText("<html>Set the minimum amount of cash you need in order to perform different transactions<br/>Click on a colored button above and then drag the highlighted line in the chart<html/>");
		_winnerPanel = new WinnerPanelVertical(id);
		this.add(_winnerPanel);
		this.add(_profitGraph);
		_sliderPanel = new SliderPanel();
		this.add(_sliderPanel);
		
		_viewPanel = new ViewPanel(board);
		this.add(_viewPanel);
	}
	
	public void reenableSetHeuristics(int time) {
		_viewPanel.reenableSetHeuristics(time);
	}
	
	public void setWealthData(List<PlayerWealthDataReport> data) {
		_profitGraph.setWealthData(data);
	}
	
	public List<Double> getSliderInfo () {
		return _sliderPanel.getSliderInfo();
	} 
	
	public void removeSetHeuristicsButton() {
		_viewPanel.removeSetHeuristicsButton();
	}


	public List<Integer> getMinCash() {
		return _profitGraph.getMinCash();
	}


	public void setButtonChoices(Player player) {
		_buttonPanel.setButtonChoices(player);
	}


	public void setSliderValues(double liquidity, double timeChange,
			double tradingFear) {
		_sliderPanel.setValues(liquidity, timeChange, tradingFear);
	}


	public void setMinCashValues(int minBuyCash, int minBuildCash,
			int minUnmortgageCash) {
		_profitGraph.setMinCashValues(minBuyCash, minBuildCash, minUnmortgageCash);
	}


	public void setButtonValues(Expense buildingChoice,
			Balance buildingEvenness, Aggression buildAggression,
			Expense sellingChoice, Amount houseSelling, Expense mortgageChoice) {
		_buttonPanel.setValues(buildingChoice, buildingEvenness, buildAggression, sellingChoice, houseSelling, mortgageChoice);
	}
	

	
}
