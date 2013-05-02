package edu.brown.cs32.MFTG.gui.center;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.Aggression;
import edu.brown.cs32.MFTG.gui.Constants.Balance;
import edu.brown.cs32.MFTG.gui.Constants.Price;
import edu.brown.cs32.MFTG.gui.Constants.Quantity;
import edu.brown.cs32.MFTG.gui.Constants.Toggle;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.monopoly.Player.Amount;
import edu.brown.cs32.MFTG.monopoly.Player.Expense;

public class ButtonPanel extends JPanel {
	
	private ToggleButton _buildingExpense = new ToggleButton(Price.CHEAP);
	private ToggleButton _buildingEvenness = new ToggleButton(Balance.EVEN);
	private ToggleButton _buildingAggressiveness = new ToggleButton(Aggression.PASSIVE);
	private ToggleButton _sellingExpense = new ToggleButton(Price.CHEAP);
	private ToggleButton _sellingAmount = new ToggleButton(Quantity.FEWER);
	private ToggleButton _mortgageExpense = new ToggleButton(Price.CHEAP);
	
	public ButtonPanel() {
		super();
		this.setSize(9*Constants.WIDTH, 2*Constants.WIDTH);
		this.setLocation(0, 0);
		this.setBackground(Constants.BACKGROUND_COLOR);
		
		this.setLayout(null);
		
		setup();
	}
	
	/*public void setup() {
		JButton _buildingChoice = new JButton("", Price.CHEAP.getIcon(), false);
		_buildingChoice.setSelectedIcon(Price.EXPENSIVE.getIcon());
		_buildingChoice.setRolloverSelectedIcon(Price.EXPENSIVE.getIcon());
		_buildingChoice.setRolloverIcon(Price.CHEAP.getIcon());
		_buildingChoice.setText("hello");
		this.add(_buildingChoice);
	}*/
	
	public void setup() {
		Dimension dim = new Dimension(Constants.BUTTON_DIMENSION, Constants.BUTTON_DIMENSION);
		_buildingExpense.setSize(dim);
		_buildingEvenness.setSize(dim);
		_buildingAggressiveness.setSize(dim);
		_sellingExpense.setSize(dim);
		_sellingAmount.setSize(dim);
		_mortgageExpense.setSize(dim);
		
		_buildingExpense.setToolTipText("<html>Do you prefer to build houses on:<br/>CHEAP: more cheap properties<br/>EXPENSIVE: fewer expensive properties</html>");
		_buildingEvenness.setToolTipText("<html>Do you prefer to build houses:<br/>EVEN: evenly across different monopolies<br/>UNEVEN: focus on one monopoly</html>");
		_buildingAggressiveness.setToolTipText("<html>Do you prefer to build houses:<br/>PASSIVE: slowly and only when you have extra money<br/>AGGRESSIVE: whenever possible</html>");
		
		_sellingExpense.setToolTipText("<html>When you sell houses do you prefer to sell:<br/>CHEAP: more cheap houses<br/>EXPENSIVE: fewer expensive houses</html>");
		_sellingAmount.setToolTipText("<html>When you sell houses do you prefer to sell from:<br/>FEWER: properties that don't have many houses<br/>MORE: properties that have many houses</html>");
		
		_mortgageExpense.setToolTipText("<html>WHen you mortgage do you prefer to mortgage:<br/>CHEAP: more cheaper properties<br/>EXPENSIVE: fewer more expensive properties</html>");
		
		double x = ((9*Constants.WIDTH/7));
		int y = (int) (2*Constants.WIDTH/2 - dim.getHeight()/2) + 10;
		
		_buildingExpense.setLocation((int) (2*x - 3*_buildingExpense.getWidth()/2), y);
		_buildingEvenness.setLocation((int) (2*x - _buildingExpense.getWidth()/2), y);
		_buildingAggressiveness.setLocation((int) (2*x + _buildingExpense.getWidth()/2), y);
		_sellingExpense.setLocation((int) (4*x - _buildingExpense.getWidth()/2), y);
		_sellingAmount.setLocation((int) (4*x + _buildingExpense.getWidth()/2), y);
		_mortgageExpense.setLocation((int) (6*x - _buildingExpense.getWidth()/2), y);

		this.add(_buildingExpense);
		this.add(_buildingEvenness);
		this.add(_buildingAggressiveness);
		this.add(_sellingExpense);
		this.add(_sellingAmount);
		this.add(_mortgageExpense);
		
		JLabel building = new JLabel("Building", JLabel.CENTER);
		building.setSize(100, 40);
		building.setLocation((int) (2*x - building.getWidth()/2), y-30);
		JLabel selling = new JLabel("Selling", JLabel.CENTER);
		selling.setSize(100, 40);
		selling.setLocation((int) (4*x + _buildingExpense.getWidth()/2 - selling.getWidth()/2), y-30);
		JLabel mortgaging = new JLabel("Mortgaging", JLabel.CENTER);
		mortgaging.setSize(100, 40);
		mortgaging.setLocation((int) (6*x - mortgaging.getWidth()/2), y-30);
		
		this.add(building);
		this.add(selling);
		this.add(mortgaging);
	}

	public void setButtonChoices(Player player) {
		Toggle toggle = _buildingExpense.getValue();
		if(toggle == Price.CHEAP)
			player.setBuildingChoice(Player.Expense.CHEAP);
		else if (toggle == Price.EXPENSIVE)
			player.setBuildingChoice(Expense.EXPENSIVE);
		
		toggle = _buildingEvenness.getValue();
		if(toggle == Balance.EVEN)
			player.setBuildingEvenness(Player.Balance.EVEN);
		else if(toggle == Balance.UNEVEN)
			player.setBuildingEvenness(Player.Balance.UNEVEN);
		
		toggle = _buildingAggressiveness.getValue();
		if(toggle == Aggression.PASSIVE)
			player.setBuildAggression(Player.Aggression.PASSIVE);
		else if(toggle == Aggression.AGGRESSIVE)
			player.setBuildAggression(Player.Aggression.AGGRESSIVE);
		
		toggle = _sellingExpense.getValue();
		if(toggle == Price.CHEAP)
			player.setSellingChoice(Player.Expense.CHEAP);
		else if (toggle == Price.EXPENSIVE)
			player.setSellingChoice(Player.Expense.EXPENSIVE);

		toggle = _sellingAmount.getValue();
		if(toggle == Quantity.FEWER)
			player.setHouseSelling(Player.Amount.FEWER);
		else if (toggle == Quantity.MORE)
			player.setHouseSelling(Player.Amount.MORE);

		toggle = _mortgageExpense.getValue();
		if(toggle == Price.CHEAP)
			player.setMortgageChoice(Expense.CHEAP);
		else if (toggle == Price.EXPENSIVE)
			player.setMortgageChoice(Expense.EXPENSIVE);
	}

	public void setValues(Expense buildingChoice,
			edu.brown.cs32.MFTG.monopoly.Player.Balance buildingEvenness,
			edu.brown.cs32.MFTG.monopoly.Player.Aggression buildAggression,
			Expense sellingChoice, Amount houseSelling, Expense mortgageChoice) {
		
		if(buildingChoice == Expense.CHEAP) {
			_buildingExpense.setValue(Price.CHEAP);
		}
		else if(buildingChoice == Expense.EXPENSIVE) {
			_buildingExpense.setValue(Price.EXPENSIVE);
		}
		
		if(buildingEvenness == Player.Balance.EVEN) {
			_buildingEvenness.setValue(Balance.EVEN);
		}
		else if(buildingEvenness == Player.Balance.UNEVEN) {
			_buildingEvenness.setValue(Balance.UNEVEN);
		}
		
		if(buildAggression == Player.Aggression.AGGRESSIVE) {
			_buildingAggressiveness.setValue(Aggression.AGGRESSIVE);
		}
		else if(buildAggression == Player.Aggression.PASSIVE) {
			_buildingAggressiveness.setValue(Aggression.PASSIVE);
		}
		
		if(sellingChoice == Expense.CHEAP) {
			_sellingExpense.setValue(Price.CHEAP);
		}
		else if(sellingChoice == Expense.EXPENSIVE) {
			_sellingExpense.setValue(Price.EXPENSIVE);
		}
		
		if(houseSelling == Amount.FEWER) {
			_sellingAmount.setValue(Quantity.FEWER);
		}
		else if(houseSelling == Amount.MORE) {
			_sellingAmount.setValue(Quantity.MORE);
		}
		
		if(mortgageChoice == Expense.CHEAP) {
			_mortgageExpense.setValue(Price.CHEAP);
		}
		else if(mortgageChoice == Expense.EXPENSIVE) {
			_mortgageExpense.setValue(Price.EXPENSIVE);
		}
	} 
}
