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
	private DynamicChartPanel _chartPanel;
	
	private List<PlayerWealthDataReport> _currData = null;
	
	private int _minBuyCash = 0;
	private int _minBuildCash = 0;
	private int _minMortgageCash = 0;
	
	private int _blank = 3000;
	
	private XYLineAndShapeRenderer _renderer;
	
	public ProfitGraph() {
		createChart();
		update();
		this.setLayout(null);
		
		_chartPanel = new DynamicChartPanel(_chart, _renderer, this);
		
		Dimension dim = new Dimension(6*Constants.WIDTH, 5*Constants.WIDTH);
		this.setMaximumSize(dim);
		this.setMinimumSize(dim);
		this.setPreferredSize(dim);
		this.setSize(dim);

		this.setLocation(3*Constants.WIDTH, 2*Constants.WIDTH);
	
		this.add(_chartPanel);
	}

	public void setMinCash(int index, double value) {
		if(index==0){
			_minBuyCash = (int) value;
		}
		else if (index ==1) {
			_minBuildCash = (int) value;
		}
		else if(index == 2) {
			_minMortgageCash = (int) value;
		}
	}
	
	public void setWealthData(List<PlayerWealthDataReport> data) {
		_currData = data;
		update();
	}
	
	public void update() {
		_dataset.removeAllSeries();
		XYSeries minBuy = new XYSeries("Minimum Buy Cash");
		XYSeries minBuild = new XYSeries("Minimum Build Cash");
		XYSeries minMortgage = new XYSeries("Minimum Mortgage Cash");
		XYSeries blank = new XYSeries("Blank");
		
		for(double x=0; x<100; x+=.1) {
			minBuy.add(x, _minBuyCash);
			minBuild.add(x, _minBuildCash);
			minMortgage.add(x, _minMortgageCash);
		}
		_dataset.addSeries(minBuy);
		_dataset.addSeries(minBuild);
		_dataset.addSeries(minMortgage);
		
		blank.add(0, _blank);
		_dataset.addSeries(blank);
		
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
		
		_chart = ChartFactory.createXYLineChart("      ", "Time", "Money", _dataset, PlotOrientation.VERTICAL, false, true, false);
		
		_chart.setBackgroundPaint(Constants.BACKGROUND_COLOR);
		
		_plot = _chart.getXYPlot();
		_plot.setBackgroundPaint(Constants.BACKGROUND_COLOR);
		_plot.setDomainGridlinesVisible(false);
		_plot.setRangeGridlinesVisible(false);
		
		_renderer = new XYLineAndShapeRenderer();
		_renderer.setSeriesStroke(0, new BasicStroke(5));
		_renderer.setSeriesPaint(0, Color.BLUE);
		_renderer.setSeriesShapesVisible(0, false);
		_renderer.setSeriesVisible(0, true);
		_renderer.setSeriesStroke(1, new BasicStroke(5));
		_renderer.setSeriesPaint(1, Color.GREEN);
		_renderer.setSeriesShapesVisible(1, false);
		_renderer.setSeriesVisible(1, true);
		_renderer.setSeriesStroke(2, new BasicStroke(5));
		_renderer.setSeriesShapesVisible(2, false);
		_renderer.setSeriesPaint(2, Color.RED);
		_renderer.setSeriesVisible(2, true);
		_renderer.setSeriesStroke(3, new BasicStroke(0));
		_renderer.setSeriesShapesVisible(3, false);
		_renderer.setSeriesPaint(3, Constants.BACKGROUND_COLOR);
		_renderer.setSeriesVisible(3, true);
		_renderer.setSeriesStroke(4, new BasicStroke(2));
		_renderer.setSeriesPaint(4, Color.BLACK);
		_renderer.setSeriesShapesVisible(4, false);
		_renderer.setSeriesVisible(5, true);
		_renderer.setSeriesStroke(5, new BasicStroke(2));
		_renderer.setSeriesPaint(5, Color.GRAY);
		_renderer.setSeriesShapesVisible(5, false);
		_renderer.setSeriesVisible(5, true);
		_plot.setRenderer(_renderer);
		
		NumberAxis rangeAxis = (NumberAxis) _plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		
		NumberAxis domainAxis = (NumberAxis) _plot.getDomainAxis();
		domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		domainAxis.setAutoRange(false);
		domainAxis.setRange(0, 100);
	}

	public List<Integer> getMinCash() {
		List<Integer> minCash = new ArrayList<>();
		minCash.add(_minBuyCash);
		minCash.add(_minBuildCash);
		minCash.add(_minMortgageCash);
		return minCash;
	}

	public void setMinCashValues(int minBuyCash, int minBuildCash,
			int minUnmortgageCash) {
		_minBuyCash = minBuyCash;
		_minBuildCash = minBuildCash;
		_minMortgageCash = minUnmortgageCash;
		
		update();
	}

	public void disableGraph() {
		_chartPanel.disableGraph();
	}
	
	public void enableGraph () {
		_chartPanel.enableGraph();
	}

}