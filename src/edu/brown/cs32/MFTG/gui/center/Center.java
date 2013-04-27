package edu.brown.cs32.MFTG.gui.center;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.brown.cs32.MFTG.gui.Constants;

public class Center extends JPanel {

	public Center() {
		super();
		this.setSize(9*Constants.WIDTH, 9*Constants.WIDTH);
		this.setLayout(new BorderLayout());
		
		this.add(new ButtonPanel(), BorderLayout.NORTH);
		this.add(new ProfitGraph(), BorderLayout.CENTER);
		this.add(new SliderPanel(), BorderLayout.SOUTH);
	}
	
	public static void main (String[] args) {
		JFrame frame = new JFrame("Testing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Center());
		frame.pack();
		frame.setVisible(true);
	}

}
