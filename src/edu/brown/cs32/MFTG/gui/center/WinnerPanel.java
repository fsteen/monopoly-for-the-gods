package edu.brown.cs32.MFTG.gui.center;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

import edu.brown.cs32.MFTG.gui.Constants;

public class WinnerPanel extends JPanel {

	private JFreeChart _pieChart;
	private DefaultPieDataset _dataset;
	private Integer _id;
	
	public WinnerPanel(int id) {
		_id = id;
		_dataset = new DefaultPieDataset();
		
		Dimension dimension = new Dimension(3*Constants.WIDTH, 3*Constants.WIDTH);
		setSize(dimension);
		setPreferredSize(dimension);
		
		setLocation(0, 2*Constants.WIDTH);
		setBackground(Constants.BACKGROUND_COLOR);
		
		HashMap<Integer, Double> values = new HashMap<>();
		values.put(new Integer(-1), new Double(20));
		values.put(new Integer(0), new Double(10));
		values.put(new Integer(1), new Double(100));
		values.put(new Integer(3), new Double(0));
		values.put(new Integer(2), new Double(15));
		setBackground(Constants.BACKGROUND_COLOR);
		
		update(values);
		createChart();
		
		ChartPanel panel = new ChartPanel(_pieChart);
		panel.setSize(dimension);
		panel.setBackground(Constants.BACKGROUND_COLOR);
		panel.setPreferredSize(dimension);
		add(panel);
	}
	
	private void update(HashMap<Integer, Double> values) {
		
		Integer negativeOne = new Integer(-1);
		for(Integer i: values.keySet()){
			if(i.equals(negativeOne)) {
				_dataset.setValue("Tie", values.get(i));
			}
			else if (i.equals(_id)) {
				_dataset.setValue("ME", values.get(i));
			}
			else {
				_dataset.setValue("Player " + Integer.toString(i), values.get(i));
			}
		}
	}
	
	public void createChart () {
		_pieChart = ChartFactory.createPieChart("", _dataset, false, true, false);
		
		PiePlot plot = (PiePlot) _pieChart.getPlot();
		//plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setCircular(true);
		//plot.setForegroundAlpha(.5f);
		plot.setExplodePercent("ME", .2);
		plot.setLabelGenerator(null);
		plot.setBackgroundPaint(Constants.BACKGROUND_COLOR);
		plot.setOutlinePaint(Constants.BACKGROUND_COLOR);
	}
	
	public static void main (String[] args) {
		JFrame frame = new JFrame();
		WinnerPanel panel = new WinnerPanel(1);
		frame.add(panel);
		frame.setVisible(true);
		frame.pack();
		
		HashMap<Integer, Double> values = new HashMap<>();
		values.put(new Integer(-1), new Double(0));
		values.put(new Integer(0), new Double(123));
		values.put(new Integer(1), new Double(200));
		values.put(new Integer(3), new Double(124));
		values.put(new Integer(2), new Double(134));
		
		panel.update(values);
	}

}
