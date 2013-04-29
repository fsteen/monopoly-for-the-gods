package edu.brown.cs32.MFTG.gui.center;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
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
		this.setSize(9*Constants.WIDTH, 3*Constants.WIDTH);
		this.setLayout(new GridBagLayout());
		
		setup();
	}
	
	public void setup() {
		GridBagConstraints c = new GridBagConstraints();
		
		JPanel panel = new JPanel(new GridLayout(1,1));
		panel.add(_buildingExpense);
		c.weightx = 1;
		c.weighty = 2;
		c.gridx = 0;
		c.gridy = 0;
		this.add(panel, c);
		
		panel = new JPanel(new GridLayout(1,1));
		panel.add(_buildingEvenness);
		c.weightx = 1;
		c.weighty = 2;
		c.gridx = 1;
		c.gridy = 0;
		this.add(panel, c);
		
		panel = new JPanel(new GridLayout(1,1));
		panel.add(_buildingAggressiveness);
		c.weightx = 1;
		c.weighty = 2;
		c.gridx = 2;
		c.gridy = 0;
		this.add(panel, c);
		
		panel = new JPanel(new GridLayout(1,1));
		panel.add(_sellingExpense);
		c.weightx = 1;
		c.weighty = 2;
		c.gridx = 3;
		c.gridy = 0;
		this.add(panel, c);
		
		panel = new JPanel(new GridLayout(1,1));
		panel.add(_sellingAmount);
		c.weightx = 1;
		c.weighty = 2;
		c.gridx = 4;
		c.gridy = 0;
		this.add(panel, c);
		
		panel = new JPanel(new GridLayout(1,1));
		panel.add(_mortgageExpense);
		c.weightx = 1;
		c.weighty = 2;
		c.gridx = 5;
		c.gridy = 0;
		this.add(panel, c);
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

		if(toggle == Price.CHEAP)
			player.setMortgageChoice(Expense.CHEAP);
		else if (toggle == Price.EXPENSIVE)
			player.setMortgageChoice(Expense.EXPENSIVE);
	}
}
