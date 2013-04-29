package edu.brown.cs32.MFTG.gui.center;

import java.awt.event.MouseEvent;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class DynamicChartPanel extends ChartPanel {

	private int _location = -1;
	private double _currHeight = 0;
	private int _moving = -1;
	private boolean _mousePressed;
	private JFreeChart _chart;
	
	public DynamicChartPanel(JFreeChart chart) {
		super(chart);
		this.setMouseWheelEnabled(true);
		_chart = chart;
		this.addChartMouseListener(new MinCashListener());
	}
	
	public void mousePressed (MouseEvent e) {
		super.mousePressed(e);
		System.out.println(_location);
		_moving = _location;
		_currHeight = e.getLocationOnScreen().getX();
		_mousePressed = true;
	}
	
	public void update (double change) {
		XYPlot plot = (XYPlot) _chart.getPlot();
		XYSeriesCollection seriesCollection = (XYSeriesCollection) plot.getDataset();
		XYSeries series = seriesCollection.getSeries(_moving);

		
		
		double currValue = series.getMaxY();
		currValue += (50*(-1)*change);
		
		for(double x=0; x<100; x+=.1) {
			series.update(x, currValue);
		}
		
		_chart.fireChartChanged();
	}
	
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		_location = -1;
		_moving = -1;
		_mousePressed = false;
	}
	
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		System.out.println("ckicked");
	}
	
	public void mouseDragged(MouseEvent e) {
		if(_moving == -1) super.mouseDragged(e);
		else {
			double newHeight = e.getY();
			if(Math.abs(newHeight - _currHeight) < 10) {
				update(newHeight - _currHeight);
			}
			_currHeight = newHeight;
		}
	}
	
	
	private class MinCashListener implements ChartMouseListener {
		@Override
		public void chartMouseClicked(ChartMouseEvent e) {
			ChartEntity entity = e.getEntity();
			if(!(entity instanceof XYItemEntity)) {
				return;
			}
			XYItemEntity xyItem = (XYItemEntity) entity;
			
			_location = xyItem.getSeriesIndex();
			System.out.println("clicked in box");
		}

		@Override
		public void chartMouseMoved(ChartMouseEvent e) {
			ChartEntity entity = e.getEntity();
			if(!(entity instanceof XYItemEntity)) {
				_location = -1;
				return;
			}
			XYItemEntity xyItem = (XYItemEntity) entity;
			
			_location = xyItem.getSeriesIndex();
			if(_location == 3 || _location == 4 ) _location = -1;
		}
		
	}

}
