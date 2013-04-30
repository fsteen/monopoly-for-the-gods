package edu.brown.cs32.MFTG.gui.center;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.UIManager;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.View;
import edu.brown.cs32.MFTG.gui.gameboard.Board;

public class ViewPanel extends JPanel{

	private Board _board;
	private JToggleButton _setHeuristics;
	
	public ViewPanel(Board board) {
		_board = board;
		this.setBackground(Constants.BACKGROUND_COLOR);
		this.setLayout(null);
		this.setLocation((int) (9*Constants.WIDTH/2), 7*Constants.WIDTH);
		this.setSize((int) (9*Constants.WIDTH/2), 2*Constants.WIDTH);
		
		UIManager.put("Button.focus", new Color(0,0,0,0));
		
		JToggleButton myProperty = new JToggleButton("My Properties");
		myProperty.setSelected(true);
		JToggleButton aggregateProperty = new JToggleButton("Aggregate Property");
		JToggleButton colorGroup = new JToggleButton("Color Group");
		
		myProperty.addActionListener(new ViewListener(View.ME));
		aggregateProperty.addActionListener(new ViewListener(View.AGGREGATE));
		colorGroup.addActionListener(new ViewListener(View.COLOR));
		
		ButtonGroup viewGroup = new ButtonGroup();
		viewGroup.add(myProperty);
		viewGroup.add(aggregateProperty);
		viewGroup.add(colorGroup);

		_setHeuristics = new JToggleButton("<html><center>Set<br/>Heuristics</center></html>");
		_setHeuristics.addActionListener(new SetHeuristicsListener());

		Dimension size = new Dimension((int) (2.5*Constants.WIDTH), 30);
		myProperty.setSize(size);
		aggregateProperty.setSize(size);
		colorGroup.setSize(size);
		
		
		double x = (9*Constants.WIDTH/2) - .25*Constants.WIDTH - myProperty.getWidth();
		double y = 2*Constants.WIDTH/4;
		myProperty.setLocation((int) (x), (int) (y-myProperty.getHeight()/2));
		aggregateProperty.setLocation((int) (x), (int) (2*y-myProperty.getHeight()/2));
		colorGroup.setLocation((int) (x), (int) (3*y-myProperty.getHeight()/2));
		
		_setHeuristics.setSize(100, 100);
		_setHeuristics.setLocation(0, Constants.WIDTH - _setHeuristics.getHeight()/2);
		
		this.add(myProperty);
		this.add(aggregateProperty);
		this.add(colorGroup);
		this.add(_setHeuristics);
	}
	
	private class SetHeuristicsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			_setHeuristics.setEnabled(false);
			System.out.println(_board.setHeuristics());
		}
		
	}
	
	public class ViewListener implements ActionListener {

		private View _view;
		public ViewListener (View view) {
			_view = view;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			_board.setView(_view);
		}
		
	}

}
