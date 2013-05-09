package edu.brown.cs32.MFTG.gui.center;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.text.NumberFormat;
import java.util.Map;

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
import edu.brown.cs32.MFTG.gui.Constants.Colors;

public class WinnerPanelVertical extends JPanel {

	private DefaultCategoryDataset _dataset;
	private JFreeChart _chart;
	private Integer _id;
	
	public WinnerPanelVertical(int id) {
		_id = new Integer(id);
		
		Dimension dimension = new Dimension(3*Constants.WIDTH-10, 4*Constants.WIDTH-10);
		setSize(dimension);
		setPreferredSize(dimension);
		
		this.setToolTipText("Displays who is winning based on your win condition -- hopefully you :)");
		
		setLocation(10, 2*Constants.WIDTH+10);
		setBackground(Constants.BACKGROUND_COLOR);
		
		_dataset = new DefaultCategoryDataset();
		createChart();
		
		ChartPanel panel = new ChartPanel(_chart);
		panel.setSize(dimension);
		panel.setBackground(Constants.BACKGROUND_COLOR);
		panel.setPreferredSize(dimension);
		add(panel);
	}
	
	public void update (Map<Integer, Double> data, Map<Integer, String> names) {
		_chart.setTitle("Who's Winning?");
		Integer negativeOne = new Integer(-1);
		for(Integer i: data.keySet()) {
			System.out.println("\ni'm looking at: " + i);
			if(i.equals(negativeOne)) {
				System.out.println("its a tie");
				_dataset.addValue(data.get(i), "Tie", "Player");
			}
			else if (i.equals(_id)) {
				_dataset.addValue(data.get(i), names.get(i) + " (ME!)", "Player");
				_dataset.getColumnCount();
			}
			else {
				if(names.containsKey(i))
				{
					System.out.println("player name: " + names.get(i));
					_dataset.addValue(data.get(i), names.get(i) + " (" + i + ")", "Player");
				}
				else {
					System.out.println("no name");
					_dataset.addValue(data.get(i), "Player " + i, "Player");					
				}
			}
		}
	}
	
	public void createChart() {
		_chart = ChartFactory.createStackedBarChart("", "", "", _dataset, PlotOrientation.VERTICAL, false, false, false);

		_chart.setBackgroundPaint(Constants.BACKGROUND_COLOR);
		
		CategoryPlot categoryPlot = (CategoryPlot) _chart.getPlot();
		categoryPlot.setBackgroundPaint(Constants.BACKGROUND_COLOR);
		categoryPlot.setRangeGridlinePaint(Color.LIGHT_GRAY);

		categoryPlot.setOutlinePaint(null);
		categoryPlot.setRangeGridlinesVisible(false);
		
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
		
		renderer.setSeriesPaint(1, Colors.LIGHT_BLUE.getColor());
		renderer.setSeriesPaint(2, Colors.PINK.getColor());
		renderer.setSeriesPaint(3, Colors.ORANGE.getColor());
		renderer.setSeriesPaint(4, Colors.PURPLE.getColor());
		renderer.setSeriesPaint(0, Colors.YELLOW.getColor());
		renderer.setRenderAsPercentages(true);

		_chart.getCategoryPlot().setRenderer(renderer);
	}
}
