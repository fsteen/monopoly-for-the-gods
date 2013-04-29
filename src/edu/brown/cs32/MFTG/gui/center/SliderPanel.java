package edu.brown.cs32.MFTG.gui.center;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JSlider;

import edu.brown.cs32.MFTG.gui.Constants;

public class SliderPanel extends JPanel {

	private JSlider _liquidity;
	private JSlider _timeChange;
	private JSlider _tradingFear;

	public SliderPanel() {
		super();
		this.setSize(9*Constants.WIDTH, 2*Constants.WIDTH);
		this.setLayout(new GridLayout(3,1));
		
		_liquidity = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
		_timeChange = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
		_tradingFear = new JSlider(JSlider.HORIZONTAL, 0, 3, 2);		
		this.add(_liquidity);
		this.add(_timeChange);
		this.add(_tradingFear);
	}
	
	public List<Double> getSliderInfo() {
		List<Double> sliderInfo = new ArrayList<>();
		sliderInfo.add(new Double(_liquidity.getValue()));
		sliderInfo.add(new Double(_timeChange.getValue()));
		sliderInfo.add(new Double(_tradingFear.getValue()));
		return sliderInfo;
	}

}
