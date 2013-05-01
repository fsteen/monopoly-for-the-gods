package edu.brown.cs32.MFTG.gui.gameboard;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.ColorProperties;
import edu.brown.cs32.MFTG.gui.Constants.Colors;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.Properties;
import edu.brown.cs32.MFTG.gui.Constants.View;
import edu.brown.cs32.MFTG.gui.properties.AggregateColorProperty;
import edu.brown.cs32.MFTG.gui.properties.MyColorPropertyPanel;
import edu.brown.cs32.MFTG.gui.properties.PropertyPanel;
import edu.brown.cs32.MFTG.tournament.data.PropertyDataAccumulator;
import edu.brown.cs32.MFTG.tournament.data.PropertyDataReport;

public class ColorGroup extends JPanel {
	
	private Map<Properties, PropertyPanel> _myPropertyName = new HashMap<>();
	private Map<Properties, PropertyPanel> _aggregatePropertyName = new HashMap<>();
	private List<PropertyPanel> _myProperties;
	private List<PropertyPanel> _aggregateProperties;
	private ColorBlock _colorBlock;
	private View _view = View.ME;
	private Orientation _orientation;
	private String _color;
	private GridBagConstraints _c = new GridBagConstraints();
	
	public ColorGroup (Orientation orientation, List<Properties> properties, List<PropertyPanel> myPanel, List<PropertyPanel> aggregatePanel, ColorBlock colorBlock) {
		super();
		_orientation = orientation;
		_color = colorBlock.getName();
		
		this.setLayout(new GridBagLayout());
		
		/* Set up the size of the panel */
		if(orientation == Orientation.UP || orientation == Orientation.DOWN) {
			Dimension dimension = new Dimension(4*Constants.WIDTH, Constants.HEIGHT);
			this.setPreferredSize(dimension);
			this.setMinimumSize(dimension);
			this.setMaximumSize(dimension);
		}
		else {
			Dimension dimension = new Dimension(Constants.HEIGHT, 4*Constants.WIDTH);
			this.setPreferredSize(dimension);
			this.setMinimumSize(dimension);
			this.setMaximumSize(dimension);
		}
		
		this.addMouseListener(new ButtonMouseListener());
		
		for(int i=0; i<properties.size(); i++) {
			_myPropertyName.put(properties.get(i), myPanel.get(i));
			_aggregatePropertyName.put(properties.get(i), aggregatePanel.get(i));
		}
		_myProperties = myPanel;
		_aggregateProperties = aggregatePanel;
		_colorBlock = colorBlock;
		update();
		// TODO Auto-generated method stub
		
		_view = View.AGGREGATE;
		update();
		
		_view = View.ME;
		update();
	}
	
	public String getName() {
		return _color;
	}
	
	public void update () {
		if(_view == View.ME){
			this.putObjects(_myProperties);
		}
		else if(_view == View.AGGREGATE) {
			this.putObjects(_aggregateProperties);
		}
		else if(_view == View.COLOR){
			this.putObjects(_colorBlock);
		}
	}
	
	public void putObjects (ColorBlock colorBlock) {
		this.removeAll();
		_c.ipadx = 0; _c.ipady = 0;
		_c.gridx = 0;
		_c.gridy = 0;
		this.add(_colorBlock, _c);
		this.updateUI();
		this.repaint();
	}
	
	public void putObjects (List<PropertyPanel> panel) {
		this.removeAll();
		_c.ipadx = 0; _c.ipady = 0;
		if(_orientation == Orientation.UP || _orientation == Orientation.DOWN) {
			
			_c.gridy = 0;
			_c.gridx = 0;
			this.add(panel.get(0), _c);
			_c.gridx = 1;
			this.add(panel.get(1), _c);
			_c.gridx = 2;
			this.add(panel.get(2), _c);
			_c.gridx = 3;
			this.add(panel.get(3), _c);
		}
		else {
			_c.gridx = 0;
			_c.gridy = 0;
			this.add(panel.get(0), _c);
			_c.gridy = 1;
			this.add(panel.get(1), _c);
			_c.gridy = 2;
			this.add(panel.get(2), _c);
			_c.gridy = 3;
			this.add(panel.get(3), _c);
		}
		this.updateUI();
		this.repaint();
	}
	
	public Set<String> getNames() {
		Set<Properties> properties = _myPropertyName.keySet();
		Set<String> names = new HashSet<>();
		for(Properties p: properties) {
			names.add(p.getLowercaseName());
		}
		return names;
	}
	
	public void setAggregateData (String name, PropertyDataReport data) {
		Set<Properties> properties = _aggregatePropertyName.keySet();
		for(Properties p: properties) {
			if(p.getLowercaseName().equals(name)) {
				_aggregatePropertyName.get(p).setData(data);
			}
		}
	}
	
	public void setMyData (String name, PropertyDataReport propertyDataReport) {
		Set<Properties> properties = _myPropertyName.keySet();
		for(Properties p: properties) {
			if(p.getLowercaseName().equals(name)) {
				_myPropertyName.get(p).setData(propertyDataReport);
			}
		}
	}
	
	private class ButtonMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount()==2) {
				if(_view == View.ME) {
					_view = View.AGGREGATE;
				}
				else if(_view == View.AGGREGATE) {
					_view = View.COLOR;
				}
				else if(_view == View.COLOR) {
					_view = View.ME;
				}
				update();
			}
			if(e.getButton() == MouseEvent.BUTTON3) {

			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}
	}
	
/* ----------------------------------------------------------------------------
 * ----------------------------- CHANGE VIEW ----------------------------------
 * ----------------------------------------------------------------------------
 */

	public HashMap<String, Integer> getPropertyValues() {
		HashMap<String, Integer> propertyValues = new HashMap<>();
		for(Properties p: _myPropertyName.keySet()) {
			String name = p.getLowercaseName();
			if(! name.equals("static property")) {
				PropertyPanel property = _myPropertyName.get(p);
				int i = property.getValue();
				propertyValues.put(name, i);
			}
		}
		return propertyValues;
	}

	public Double[] getColorValues() {
		Double[] _colorValues =  _colorBlock.getColorValues();
		return _colorValues;
	}

	public void setView(View view) {
		_view = view;
		update();
	}
}
