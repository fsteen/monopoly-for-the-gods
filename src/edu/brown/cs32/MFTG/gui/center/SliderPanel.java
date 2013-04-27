package edu.brown.cs32.MFTG.gui.center;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSlider;

import edu.brown.cs32.MFTG.gui.Constants;

public class SliderPanel extends JPanel {

	public SliderPanel() {
		super();
		this.setSize(9*Constants.WIDTH, 2*Constants.WIDTH);
		this.setLayout(new GridLayout(3,1));
		this.add(new JSlider(JSlider.HORIZONTAL, 0, 10, 5));
		this.add(new JSlider(JSlider.HORIZONTAL, 0, 10, 5));
		this.add(new JSlider(JSlider.HORIZONTAL, 0, 3, 2));
	}

}
