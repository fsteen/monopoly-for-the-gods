package edu.brown.cs32.MFTG.gui.center;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

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
import edu.brown.cs32.MFTG.monopoly.PlayerWealthData;
import edu.brown.cs32.MFTG.tournament.data.PlayerWealthDataReport;

public class ProfitGraph extends JPanel {

	private XYSeriesCollection _dataset = new XYSeriesCollection();;
	private JFreeChart _chart;
	private XYPlot _plot;
	private ChartPanel _chartPanel;
	
	private List<PlayerWealthDataReport> _currData = null;
	
	private int _minBuyCash = 0;
	private int _minBuildCash = 0;
	private int _minMortgageCash = 0;
	
	public ProfitGraph() {
		createChart();
		update();
		this.setLayout(null);
		
		_chartPanel = new DynamicChartPanel(_chart);
		
		Dimension dim = new Dimension(9*Constants.WIDTH, 5*Constants.WIDTH);
		this.setMaximumSize(dim);
		this.setMinimumSize(dim);
		this.setPreferredSize(dim);
		this.setSize(dim);
		
		this.setLocation(0, 2*Constants.WIDTH);
	
		this.add(_chartPanel);
	}

/*	
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
	*/
	
	public void setWealthData(List<PlayerWealthDataReport> data) {
		System.out.println("setting wealth data");
		_currData = data;
		update();
		System.out.println("finished setting wealth data");
	}
	
	public void update() {
		_dataset.removeAllSeries();
		XYSeries minBuild = new XYSeries("Minimum Build Cash");
		XYSeries minBuy = new XYSeries("Minimum Buy Cash");
		XYSeries minMortgage = new XYSeries("Minimum Mortgage Cash");
		
		for(double x=0; x<100; x+=.1) {
			minBuild.add(x, _minBuildCash);
			minBuy.add(x, _minBuyCash);
			minMortgage.add(x, _minMortgageCash);
		}
		_dataset.addSeries(minBuild);
		_dataset.addSeries(minBuy);
		_dataset.addSeries(minMortgage);
		
		if (_currData != null) {
			XYSeries cash = new XYSeries("Cash");
			XYSeries totalWealth = new XYSeries("Total Wealth");
			for(int x=0; x<_currData.size(); x++) {
				PlayerWealthDataReport d = _currData.get(x);
				if(d != null) {
					cash.add(x, d.accCash);
					totalWealth.add(x, d.accTotalWealth);
				}				
			}
			_dataset.addSeries(cash);
			_dataset.addSeries(totalWealth);
			
		}
		_chart.fireChartChanged();
	}
	
	public void createChart () {
		
		_chart = ChartFactory.createXYLineChart("Profit and Cash", "Time", "Money", _dataset, PlotOrientation.VERTICAL, false, true, false);
		
		_chart.setBackgroundPaint(Constants.BACKGROUND_COLOR);
		
		_plot = _chart.getXYPlot();
		_plot.setBackgroundPaint(Constants.BACKGROUND_COLOR);
		_plot.setDomainGridlinesVisible(false);
		_plot.setRangeGridlinesVisible(false);
		
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesStroke(0, new BasicStroke(5));
		renderer.setSeriesPaint(0, Color.BLUE);
		renderer.setSeriesShapesVisible(0, false);
		renderer.setSeriesVisible(0, true);
		renderer.setSeriesStroke(1, new BasicStroke(5));
		renderer.setSeriesPaint(1, Color.GREEN);
		renderer.setSeriesShapesVisible(1, false);
		renderer.setSeriesVisible(1, true);
		renderer.setSeriesStroke(2, new BasicStroke(5));
		renderer.setSeriesShapesVisible(2, false);
		renderer.setSeriesPaint(2, Color.RED);
		renderer.setSeriesVisible(2, true);
		renderer.setSeriesStroke(3, new BasicStroke(2));
		renderer.setSeriesPaint(3, Color.BLACK);
		renderer.setSeriesShapesVisible(3, false);
		renderer.setSeriesVisible(3, true);
		renderer.setSeriesStroke(4, new BasicStroke(2));
		renderer.setSeriesPaint(4, Color.GRAY);
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

	public List<Integer> getMinCash() {
		List<Integer> minCash = new ArrayList<>();
		minCash.add(_minBuyCash);
		minCash.add(_minBuildCash);
		minCash.add(_minMortgageCash);
		return minCash;
	}

}