package edu.brown.cs32.MFTG.gui.gameboard;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.ToolTipManager;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.Properties;
import edu.brown.cs32.MFTG.gui.Constants.View;
import edu.brown.cs32.MFTG.gui.properties.PropertyPanel;
import edu.brown.cs32.MFTG.tournament.data.PropertyDataReport;

public class ColorGroup extends JPanel {
	
	private Map<String, PropertyPanel> _myPropertyName = new HashMap<>();
	private Map<String, PropertyPanel> _aggregatePropertyName = new HashMap<>();
	private List<BufferedImage> _deeds = new ArrayList<>();
	private List<PropertyPanel> _myProperties;
	private List<PropertyPanel> _aggregateProperties;
	private ColorBlock _colorBlock;
	private View _view = View.ME;
	private Orientation _orientation;
	private String _color;
	private GridBagConstraints _c = new GridBagConstraints();
	
	private JToolTip _myPropertyToolTip;
	private JToolTip _aggregatePropertyToolTip;
	private JToolTip _colorBlockToolTip;
	
	public ColorGroup (Orientation orientation, List<Properties> properties, List<PropertyPanel> myPanel, List<PropertyPanel> aggregatePanel, ColorBlock colorBlock) {
		super();
		_orientation = orientation;
		_color = colorBlock.getName();
		
		try {
			_myPropertyToolTip = new IconToolTip("Deed_Cards/property_instructions.jpg");
			_myPropertyToolTip.setComponent(this);
			
			_aggregatePropertyToolTip = new IconToolTip("Deed_Cards/aggregate_property_instructions.jpg");
			_aggregatePropertyToolTip.setComponent(this);
			
			_colorBlockToolTip = new IconToolTip("Deed_Cards/color_block_instructions.jpg");
			_colorBlockToolTip.setComponent(this);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		setToolTipText("");
		
		for(Properties p: properties) {
			BufferedImage im;
			try {
				String deed = p.getDeed();
				if(deed!=null) {
					im = ImageIO.read(new File(p.getDeed()));
					//im = Helper.resize(im, Constants.DEED_WIDTH, Constants.DEED_HEIGHT);
					_deeds.add(im);
				}
			} catch (IOException e) {
				System.out.println(p.getDeed());
				e.printStackTrace();
			}
		}
		
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
			_myPropertyName.put(properties.get(i).getLowercaseName(), myPanel.get(i));
			_aggregatePropertyName.put(properties.get(i).getLowercaseName(), aggregatePanel.get(i));
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
	

	public JToolTip createToolTip () {
		if(_view == View.ME)
			return _myPropertyToolTip;
		else if(_view == View.AGGREGATE)
			return _aggregatePropertyToolTip;
		return _colorBlockToolTip;
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
		Set<String> properties = _myPropertyName.keySet();
		Set<String> names = new HashSet<>();
		for(String p: properties) {
			names.add(p);
		}
		return names;
	}
	
	public void setAggregateData (String name, PropertyDataReport data) {
		Set<String> properties = _aggregatePropertyName.keySet();
		for(String p: properties) {
			if(p.equals(name)) {
				_aggregatePropertyName.get(p).setData(data);
			}
		}
	}
	
	public void setMyData (String name, PropertyDataReport propertyDataReport) {
		Set<String> properties = _myPropertyName.keySet();
		for(String p: properties) {
			if(p.equals(name)) {
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
				JFrame frame = new JFrame("Deed Cards");
				
				Dimension dimension;
				if(_deeds.size() < 4)
					dimension = new Dimension(Constants.DEED_WIDTH + 10,_deeds.size()*Constants.DEED_HEIGHT + 25);
				else 
					dimension = new Dimension(2*Constants.DEED_WIDTH + 10,2*Constants.DEED_HEIGHT + 25);
				
				frame.setSize(dimension);
				frame.setPreferredSize(dimension);
				frame.setMaximumSize(dimension);
				frame.setMinimumSize(dimension);
				frame.setResizable(false);
				
				frame.add(new DeedPopup(_deeds));
				frame.pack();
				frame.setVisible(true);
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
		// TODO Auto-generated method stub
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
		for(String name: _myPropertyName.keySet()) {
			if(name != null && !name.equals("static property")) {
				PropertyPanel property = _myPropertyName.get(name);
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

	public void setPropertyValues(HashMap<String, Integer> propertyValues) {
		for(String property: propertyValues.keySet()) {
			if(_myPropertyName.containsKey(property)) {
				_myPropertyName.get(property).setValue(propertyValues.get(property));
			}
		}
	}

	public void setColorValue(HashMap<String, Double[]> colorValues) {
		for(String color: colorValues.keySet()) {
			if(_color.equals(color)) {
				_colorBlock.setValues(colorValues.get(color));
			}
		}
	}
}
