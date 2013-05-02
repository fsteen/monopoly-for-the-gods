package edu.brown.cs32.MFTG.gui.center;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicSliderUI;

import edu.brown.cs32.MFTG.gui.Constants;

public class SliderPanel extends JPanel {

	private JSlider _liquidity;
	private JSlider _timeChange;
	private JSlider _tradingFear;

	public SliderPanel() {
		super();
		
		Dimension dim = new Dimension((int) (9*Constants.WIDTH/2.), 2*Constants.HEIGHT);
		this.setSize(dim);
		this.setPreferredSize(dim);
		this.setMaximumSize(dim);
		this.setMinimumSize(dim);
		
		this.setBackground(Constants.BACKGROUND_COLOR);
		
		this.setLocation(0, 7*Constants.WIDTH);

		this.setLayout(null);
		
		_liquidity = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		_timeChange = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		_tradingFear = new JSlider(JSlider.HORIZONTAL, 0, 90, 15);	
		
		_liquidity.setBackground(Constants.BACKGROUND_COLOR);
		_timeChange.setBackground(Constants.BACKGROUND_COLOR);
		_tradingFear.setBackground(Constants.BACKGROUND_COLOR);
		
		
		_liquidity.setSize((int) (4*Constants.WIDTH), 20);
		_timeChange.setSize((int) (4*Constants.WIDTH), 20);
		_tradingFear.setSize((int) (4*Constants.WIDTH), 20);
		
		_liquidity.setLocation((int) (.25*Constants.WIDTH), (int) (2*Constants.WIDTH/4 - _liquidity.getHeight()/2 - 10));
		_timeChange.setLocation((int) (.25*Constants.WIDTH), (int) (4*Constants.WIDTH/4 - _timeChange.getHeight()/2 - 10));
		_tradingFear.setLocation((int) (.25*Constants.WIDTH), (int) (6*Constants.WIDTH/4 - _tradingFear.getHeight()/2 - 10));
		
		this.add(_liquidity);
		this.add(_timeChange);
		this.add(_tradingFear);
		
		JLabel liquidity = new JLabel("Liquidity");
		JLabel timeChange = new JLabel("Time Change");
		JLabel tradingFear = new JLabel("Trading Fear");
		
		liquidity.setSize(100, 40);
		timeChange.setSize(100, 40);
		tradingFear.setSize(100, 40);
		
		liquidity.setLocation((int) (Constants.WIDTH/4.), (int) (2*Constants.WIDTH/4 - liquidity.getHeight()/2 - 17 - 10));
		timeChange.setLocation((int) (Constants.WIDTH/4.), (int) (4*Constants.WIDTH/4 - timeChange.getHeight()/2 - 17 - 10));
		tradingFear.setLocation((int) (Constants.WIDTH/4.), (int) (6*Constants.WIDTH/4 - tradingFear.getHeight()/2 - 17 - 10));
		
		this.add(liquidity);
		this.add(timeChange);
		this.add(tradingFear);
		
		/*UIDefaults defaults = UIManager.getDefaults();
		BufferedImage icon;
		try {
			icon = ImageIO.read(new File("Deed_cards/railroad.jpg"));
			defaults.put("Slider.horizontalThumbIcon", icon);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
	public class mySliderUI extends BasicSliderUI {
		Image icon;
		public mySliderUI (JSlider slider, String file) {
			super(slider);
			try {
				icon = ImageIO.read(new File(file));
			}
			catch (IOException e) {
				
			}
		}
		public void paintThumb(Graphics g) {
			g.drawImage(icon, thumbRect.x, thumbRect.y, 8, 8, null);
		}
	}
	
	public List<Double> getSliderInfo() {
		List<Double> sliderInfo = new ArrayList<>();
		sliderInfo.add(new Double(_liquidity.getValue()/10.));
		sliderInfo.add(new Double(_timeChange.getValue()/10.));
		sliderInfo.add(new Double(_tradingFear.getValue()/20.));
		return sliderInfo;
	}

	//TODO: don't force these to be integers
	public void setValues(double liquidity, double timeChange,
			double tradingFear) {
		_liquidity.setValue((int) (10*liquidity));
		_timeChange.setValue((int) (10*timeChange));
		_tradingFear.setValue((int) (30*tradingFear));
	}

}
