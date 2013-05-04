package edu.brown.cs32.MFTG.gui.center;

import java.awt.Color;
import java.awt.Dimension;
import java.text.NumberFormat;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import edu.brown.cs32.MFTG.gui.Constants;

public class WinnerPanelVertical extends JPanel {

	private DefaultCategoryDataset _dataset;
	private JFreeChart _chart;
	private Integer _id;
	
	public WinnerPanelVertical(int id) {
		_id = new Integer(id);
		
		Dimension dimension = new Dimension(3*Constants.WIDTH, 5*Constants.WIDTH);
		setSize(dimension);
		setPreferredSize(dimension);
		
		setLocation(0, 2*Constants.WIDTH);
		setBackground(Constants.BACKGROUND_COLOR);
		
		_dataset = new DefaultCategoryDataset();
		createChart();
		
		ChartPanel panel = new ChartPanel(_chart);
		panel.setSize(dimension);
		panel.setBackground(Constants.BACKGROUND_COLOR);
		panel.setPreferredSize(dimension);
		add(panel);
		
		HashMap<Integer, Double> values = new HashMap<>();
		values.put(new Integer(-1), new Double(0));
		values.put(new Integer(0), new Double(123));
		values.put(new Integer(1), new Double(200));
		values.put(new Integer(3), new Double(124));
		values.put(new Integer(2), new Double(134));
		
		update(values);
	}
	
	public void update (HashMap<Integer, Double> data) {
		Integer negativeOne = new Integer(-1);
		for(Integer i: data.keySet()) {
			if(i.equals(negativeOne)) {
				_dataset.addValue(data.get(i), "Tie", "Player");
			}
			else if (i.equals(_id)) {
				_dataset.addValue(data.get(i), "ME", "Player");
			}
			else {
				_dataset.addValue(data.get(i), "Player " + Integer.toString(i), "Player");
			}
		}
	}
	
	public void createChart() {
		_chart = ChartFactory.createStackedBarChart("Winner", "", "", _dataset, PlotOrientation.VERTICAL, false, false, false);
		_chart.setBackgroundPaint(Constants.BACKGROUND_COLOR);
		
		CategoryPlot categoryPlot = (CategoryPlot) _chart.getPlot();
		categoryPlot.setBackgroundPaint(Constants.BACKGROUND_COLOR);
		categoryPlot.setRangeGridlinePaint(Color.LIGHT_GRAY);
		
		NumberAxis rangeAxis = (NumberAxis) categoryPlot.getRangeAxis();
		rangeAxis.setNumberFormatOverride(NumberFormat.getPercentInstance());
		rangeAxis.setVisible(false);
		
		CategoryAxis domainAxis = (CategoryAxis) categoryPlot.getDomainAxis();
		domainAxis.setVisible(false);
		
		StackedBarRenderer renderer = (StackedBarRenderer) categoryPlot.getRenderer();
		renderer.setBarPainter(new StandardBarPainter());
		
		CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator("{0}: {3}",NumberFormat.getInstance());
		renderer.setBaseItemLabelGenerator(generator);
		renderer.setBaseItemLabelsVisible(true);
		_chart.getCategoryPlot().setRenderer(renderer);
		
		renderer.setRenderAsPercentages(true);
		
	}
	
	public static void main (String[] args) {
		JFrame frame = new JFrame();
		WinnerPanelVertical panel = new WinnerPanelVertical(1);
		frame.add(panel);
		frame.setVisible(true);
		frame.pack();
		
		HashMap<Integer, Double> values = new HashMap<>();
		values.put(new Integer(-1), new Double(0));
		values.put(new Integer(0), new Double(1));
		values.put(new Integer(1), new Double(0));
		values.put(new Integer(3), new Double(0));
		values.put(new Integer(2), new Double(0));
		
		panel.update(values);
	}
}
