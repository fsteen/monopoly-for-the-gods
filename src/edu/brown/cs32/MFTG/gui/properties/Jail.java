package edu.brown.cs32.MFTG.gui.properties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboBoxUI;

import edu.brown.cs32.MFTG.gui.Constants.StaticProperties;

public class Jail extends CornerProperty{

	private JComboBox _jailWait;
	private JComboBox _poorWait;
	private JComboBox _richWait;
	Integer [] _wait = {1,2,3};
	
	public Jail() throws IOException {
		super(StaticProperties.JAIL);
		
		this.setLayout(null);
		
		_jailWait = new JComboBox<Integer>(_wait);
		_jailWait.setSelectedIndex(2);
		_jailWait.setSize(35, 30);
		_jailWait.setLocation(0, 10);
		_poorWait = new JComboBox<Integer>(_wait);
		_poorWait.setSelectedIndex(2);
		_poorWait.setSize(35, 30);
		_poorWait.setLocation(0, 75);
		_richWait = new JComboBox<Integer>(_wait);
		_richWait.setSelectedIndex(2);
		_richWait.setSize(35, 30);
		_richWait.setLocation(70, 75);
		
		
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

	public void setWaits(int jailWait, int jailPoor, int jailRich) {
		_jailWait.setSelectedIndex(jailWait);
		_jailWait.setSelectedIndex(jailPoor);
		_jailWait.setSelectedIndex(jailRich);
	}

}
