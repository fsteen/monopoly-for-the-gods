package edu.brown.cs32.MFTG.gui.center;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.View;
import edu.brown.cs32.MFTG.gui.gameboard.Board;

public class ViewPanel extends JPanel{

	private Board _board;
	private JToggleButton _myProperty;
	private JToggleButton _aggregateProperty;
	private JToggleButton _colorGroup;
	private JToggleButton _setHeuristics;
	
	public ViewPanel(Board board) {
		_board = board;
		this.setBackground(Constants.BACKGROUND_COLOR);
		this.setLayout(null);
		this.setLocation((int) (9*Constants.WIDTH/2), 7*Constants.WIDTH);
		this.setSize((int) (9*Constants.WIDTH/2), 2*Constants.WIDTH);
		
		UIManager.put("Button.focus", new Color(0,0,0,0));
		
		_myProperty = new JToggleButton("My Properties");
		_myProperty.setSelected(true);
		_aggregateProperty = new JToggleButton("Aggregate Property");
		_colorGroup = new JToggleButton("Color Group");
		
		_myProperty.addActionListener(new ViewListener(View.ME, _board));
		_aggregateProperty.addActionListener(new ViewListener(View.AGGREGATE, _board));
		_colorGroup.addActionListener(new ViewListener(View.COLOR, _board));
		
		ButtonGroup viewGroup = new ButtonGroup();
		viewGroup.add(_myProperty);
		viewGroup.add(_aggregateProperty);
		viewGroup.add(_colorGroup);
		
		_myProperty.setToolTipText("My invidivual property information");
		_aggregateProperty.setToolTipText("All player property informatoin");
		_colorGroup.setToolTipText("My color information");

		_setHeuristics = new JToggleButton("<html><center>Set<br/>Heuristics</center></html>");
		_setHeuristics.addActionListener(new SetHeuristicsListener());

		Dimension size = new Dimension((int) (2.5*Constants.WIDTH), 30);
		_myProperty.setSize(size);
		_aggregateProperty.setSize(size);
		_colorGroup.setSize(size);
		
		
		double x = (9*Constants.WIDTH/2) - .25*Constants.WIDTH - _myProperty.getWidth();
		double y = 2*Constants.WIDTH/4;
		_myProperty.setLocation((int) (x), (int) (y-_myProperty.getHeight()/2) - 10);
		_aggregateProperty.setLocation((int) (x), (int) (2*y-_myProperty.getHeight()/2) - 10);
		_colorGroup.setLocation((int) (x), (int) (3*y-_myProperty.getHeight()/2) - 10);
		
		_setHeuristics.setSize(100, 100);
		_setHeuristics.setLocation(0, Constants.WIDTH - _setHeuristics.getHeight()/2 - 10);
		
		_setHeuristics.setToolTipText("<html>Commit to these heuristics<br/>Make sure to set property heuristics (My Properties), color heuristics (Color Group)<br/>and general heuristics (Center panel - buttons, graph, sliders)</html>");
		
		this.add(_myProperty);
		this.add(_aggregateProperty);
		this.add(_colorGroup);
		this.add(_setHeuristics);
	}
	
	private class SetHeuristicsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			_setHeuristics.setEnabled(false);
			_board.getHeuristics();
		}
		
	}


}
