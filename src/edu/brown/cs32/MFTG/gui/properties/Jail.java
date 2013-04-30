package edu.brown.cs32.MFTG.gui.properties;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import edu.brown.cs32.MFTG.gui.Constants.StaticProperties;

public class Jail extends CornerProperty{

	private JComboBox _jailWait;
	private JComboBox _poorWait;
	private JComboBox _richWait;
	Integer [] _wait = {1,2,3};
	
	public Jail() throws IOException {
		super(StaticProperties.JAIL);
		
		_jailWait = new JComboBox<Integer>(_wait);
		_jailWait.setSelectedIndex(2);
		_poorWait = new JComboBox<Integer>(_wait);
		_poorWait.setSelectedIndex(2);
		_richWait = new JComboBox<Integer>(_wait);
		_richWait.setSelectedIndex(2);
		this.add(_jailWait);
		_jailWait.setVisible(true);
		this.add(_poorWait);
		this.add(_richWait);
		this.updateUI();
	}
	
	public List<Integer> getWait() {
		List<Integer> waits = new ArrayList<>();
		waits.add(_wait[_jailWait.getSelectedIndex()]);
		waits.add(_wait[_poorWait.getSelectedIndex()]);
		waits.add(_wait[_richWait.getSelectedIndex()]);
		return waits;
	}

}
