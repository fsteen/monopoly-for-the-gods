package edu.brown.cs32.MFTG.gui.properties;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.metal.MetalComboBoxButton;

import edu.brown.cs32.MFTG.gui.Constants.StaticProperties;

public class Jail extends CornerProperty{

	private JComboBox _jailWait;
	private JComboBox _poorWait;
	private JComboBox _richWait;
	
	private Integer _wait;
	private Integer _poor;
	private Integer _rich;
	
	public Jail() throws IOException {
		super(StaticProperties.JAIL);
		
		this.setLayout(null);
		
		this.setToolTipText("<html>How many turns do you want to wait in jail?<br/>Turns: the default number of turn to wait<br/>Poor: if i'm poor<br/>Rich: if i'm rich<html/>");
		
		String [] wait = {"Turns", "1", "2", "3"};
		_jailWait = new JComboBox<String>(wait);
		_jailWait.setSelectedIndex(0);
		_jailWait.setSize(68, 23);
		_jailWait.setLocation(30, 75);
		_jailWait.setToolTipText("Default number of turns to wait");
		
		String[] poor = {"Poor", "1", "2", "3"};
		_poorWait = new JComboBox<String>(poor);
		_poorWait.setSelectedIndex(0);
		_poorWait.setSize(62, 23);
		_poorWait.setLocation(-2, 97);
		_poorWait.setToolTipText("Number of turns to wait if i'm poor");
		
		String [] rich = {"Rich", "1", "2", "3"};
		_richWait = new JComboBox<String>(rich);
		_richWait.setSelectedIndex(0);
		_richWait.setSize(59, 23);
		_richWait.setLocation(60, 97);
		_richWait.setToolTipText("Number of turns to wait if i'm rich");
		
		_jailWait.addActionListener(new JailListener(_wait));
		_poorWait.addActionListener(new JailListener(_poor));
		_richWait.addActionListener(new JailListener(_rich));
		
		this.add(_jailWait);
		_jailWait.setVisible(true);
		this.add(_poorWait);
		this.add(_richWait);
		this.updateUI();
	}
	
	public List<Integer> getWait() {
		List<Integer> waits = new ArrayList<>();
		if(_wait!=null) waits.add(_wait);
		else waits.add(3);
		if(_poor!=null) waits.add(_poor);
		else waits.add(3);
		if(_rich!=null) waits.add(_rich);
		else waits.add(3);
		return waits;
	}

	public void setWaits(int jailWait, int jailPoor, int jailRich) {
		_jailWait.setSelectedIndex(jailWait);
		_jailWait.setSelectedIndex(jailPoor);
		_jailWait.setSelectedIndex(jailRich);
	}
	
	public class JailListener implements ActionListener {
		
		private Integer _value;
		private int lastSelected = 1;
		
		public JailListener(Integer value) {
			_value = value;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox<String> combobox = (JComboBox<String>) e.getSource();
			int selected = combobox.getSelectedIndex();
			if(selected == 0) {
				combobox.setSelectedIndex(lastSelected);
				_value = lastSelected;
			}
			else {
				_value = selected;
				lastSelected = selected;
			}	
		}
	}
}