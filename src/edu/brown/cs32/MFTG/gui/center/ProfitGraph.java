package edu.brown.cs32.MFTG.gui.center;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import edu.brown.cs32.MFTG.gui.Constants;

public class ProfitGraph extends JPanel {

	private XYSeriesCollection _dataset = new XYSeriesCollection();;
	private JFreeChart _chart;
	private XYPlot _plot;
	private ChartPanel _chartPanel;
	
	private int _minBuyCash = 1000;
	private int _minBuildCash = 800;
	private int _minMortgageCash = 1500;
	
	public ProfitGraph() {
		createDataset();
		createChart();
		
		_chartPanel = new DynamicChartPanel(_chart);
		_chartPanel.setPreferredSize(new Dimension(500, 270));
		this.add(_chartPanel);
	}
	
	public void update () {
		createDataset();
		_chart.fireChartChanged();
	}
	
	public void createDataset() {
		XYSeries netWorth = new XYSeries("Net Worth");
		XYSeries cash = new XYSeries("Cash");
		for(int x=0; x<100; x++) {
			double y1 = Math.random()*10000;
			double y2 = Math.random()*1000;
			netWorth.add(x, y1);
			cash.add(x, y2);
		}
		XYSeries minBuild = new XYSeries("Minimum Build Cash");
		XYSeries minBuy = new XYSeries("Minimum Buy Cash");
		XYSeries minMortgage = new XYSeries("Minimum Mortgage Cash");
		
		for(double x=0; x<100; x+=.1) {
			minBuild.add(x, _minBuildCash);
			minBuy.add(x, _minBuyCash);
			minMortgage.add(x, _minMortgageCash);
		}
		
		_dataset.removeAllSeries();
		_dataset.addSeries(netWorth);
		_dataset.addSeries(cash);
		_dataset.addSeries(minBuild);
		_dataset.addSeries(minBuy);
		_dataset.addSeries(minMortgage);
	}
	
	public void createChart () {
		
		_chart = ChartFactory.createXYLineChart("Profit and Cash", "Time", "Money", _dataset, PlotOrientation.VERTICAL, true, true, false);
		
		_chart.setBackgroundPaint(Constants.BACKGROUND_COLOR);
		
		_plot = _chart.getXYPlot();
		_plot.setBackgroundPaint(Constants.BACKGROUND_COLOR);
		_plot.setDomainGridlinesVisible(false);
		_plot.setRangeGridlinesVisible(false);
		
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesStroke(0, new BasicStroke(1));
		renderer.setSeriesShapesVisible(0, false);
		renderer.setSeriesVisible(0, true);
		renderer.setSeriesStroke(1, new BasicStroke(1));
		renderer.setSeriesShapesVisible(1, false);
		renderer.setSeriesVisible(1, true);
		renderer.setSeriesStroke(2, new BasicStroke(5));
		renderer.setSeriesShapesVisible(2, false);
		renderer.setSeriesVisible(2, true);
		renderer.setSeriesStroke(3, new BasicStroke(5));
		renderer.setSeriesShapesVisible(3, false);
		renderer.setSeriesVisible(3, true);
		renderer.setSeriesStroke(4, new BasicStroke(5));
		renderer.setSeriesShapesVisible(4, false);
		renderer.setSeriesVisible(4, true);
		_plot.setRenderer(renderer);
		
		NumberAxis rangeAxis = (NumberAxis) _plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		
		NumberAxis domainAxis = (NumberAxis) _plot.getDomainAxis();
		domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		domainAxis.setRange(0, 100);
		domainAxis.setUpperBound(100);
	}
	
	public static void main (String [] args) {
		JFrame frame = new JFrame("Testing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ProfitGraph graph = new ProfitGraph();
		frame.add(graph);
		frame.pack();
		frame.setVisible(true);
		
		//while (true) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			graph.update();
		//}
		
	}

}