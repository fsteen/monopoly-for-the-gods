package edu.brown.cs32.MFTG.gui.center;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JToggleButton;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import edu.brown.cs32.MFTG.gui.Constants;

public class DynamicChartPanel extends ChartPanel {

	//private int _location = -1;
	private double _currHeight = 0;
	private int _moving = -1;
	//private boolean _mousePressed;
	private JFreeChart _chart;
	private XYLineAndShapeRenderer _renderer;
	
	private ButtonGroup _buttons;
	private JButton _buyCash;
	private JButton _buildCash;
	private JButton _unmortgageCash;
	private ProfitGraph _graph;
	private boolean _changeEnabled = true;
	
	public DynamicChartPanel(JFreeChart chart, XYLineAndShapeRenderer renderer, ProfitGraph graph) {
		super(chart);
		_graph = graph;
		_renderer = renderer;
		this.setMouseWheelEnabled(true);
		_chart = chart;
		
		this.addChartMouseListener(new MinCashListener());
		
		this.setLayout(null);
		Dimension dim = new Dimension(6*Constants.WIDTH, 5*Constants.WIDTH);
		this.setMaximumSize(dim);
		this.setMinimumSize(dim);
		this.setPreferredSize(dim);
		this.setSize(dim);
		
		setToolTipText("i'm here");
		
		this.setLocation(0, 0);
		initializeButtons();
	}
	
	public void disableGraph() {
		_changeEnabled = false;
		_buyCash.setBackground(Color.LIGHT_GRAY);
		_buildCash.setBackground(Color.LIGHT_GRAY);
		_unmortgageCash.setBackground(Color.LIGHT_GRAY);
		
		_buyCash.setFocusable(false);
		_buildCash.setFocusable(false);
		_unmortgageCash.setFocusable(false);
	}
	
	public void enableGraph() {
		_changeEnabled = true;
		_buyCash.setBackground(Color.BLUE);
		_buildCash.setBackground(Color.GREEN);
		_unmortgageCash.setBackground(Color.RED);
		
		_buyCash.setFocusable(true);
		_buildCash.setFocusable(true);
		_unmortgageCash.setFocusable(true);
	}
	
	public void initializeButtons() {
		_buttons = new ButtonGroup();
		
		_buyCash = new JButton("Buy");
		_buildCash = new JButton("Build");
		_unmortgageCash = new JButton("Unmortgage");
		
		_buyCash.setToolTipText("<html>Set the minimum amount of cash needed in order to buy properties<br/>click this then drag the line in the graph<html/");
		_buildCash.setToolTipText("<html>Set the minimum amount of cash needed in order to build houses<br/>click this then drag the line in the graph<html/");
		_unmortgageCash.setToolTipText("<html>Set the minimum amount of cash needed in order to unmortgage properties<br/>click this then drag the line in the graph<html/");
		
		_buyCash.setLocation(180, 5);
		_buildCash.setLocation(230, 5);
		_unmortgageCash.setLocation(288, 5);
		
		_buyCash.setSize(50, 25);
		_buildCash.setSize(58, 25);
		_unmortgageCash.setSize(109, 25);
		
		_buyCash.addActionListener(new MinCashButton(0));
		//_buyCash.setSelected(true);
		//_moving = 0;
		//_renderer.setSeriesPaint(0, Color.YELLOW);
		
		_buildCash.addActionListener(new MinCashButton(1));
		_unmortgageCash.addActionListener(new MinCashButton(2));
		
		_buttons.add(_buyCash);
		_buttons.add(_buildCash);
		_buttons.add(_unmortgageCash);
		
		_buyCash.addMouseListener(new RolloverListener(_buyCash));
		_buildCash.addMouseListener(new RolloverListener(_buildCash));
		_unmortgageCash.addMouseListener(new RolloverListener(_unmortgageCash));
		
		_buildCash.setForeground(Color.DARK_GRAY);
		_buyCash.setForeground(Color.DARK_GRAY);
		_unmortgageCash.setForeground(Color.DARK_GRAY);
		
		_buildCash.setBackground(Color.GREEN);
		_buyCash.setBackground(Color.BLUE);
		_unmortgageCash.setBackground(Color.RED);
		
		this.add(_buyCash);
		this.add(_buildCash);
		this.add(_unmortgageCash);
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
		
		_graph.setMinCash(_moving, currValue);
		
		_chart.fireChartChanged();
	}
	
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		_moving = -1;
		_renderer.setSeriesPaint(0, Color.BLUE);
		_renderer.setSeriesPaint(1, Color.GREEN);
		_renderer.setSeriesPaint(2, Color.RED);
		_renderer.setSeriesPaint(4, Color.BLACK);
		_renderer.setSeriesPaint(5, Color.GRAY);
		
		_buyCash.setSelected(false);
		_buildCash.setSelected(false);
		_unmortgageCash.setSelected(false);
	}
	
	public void mouseDragged(MouseEvent e) {
		if(_moving == -1) {
			super.mouseDragged(e);
		}
		else if (_changeEnabled){
			double newHeight = e.getY();
			if(Math.abs(newHeight - _currHeight) < 10) {
				update(newHeight - _currHeight);
			}
			_currHeight = newHeight;
		}
		else if (_changeEnabled == false) {
			_moving = -1;
			_renderer.setSeriesPaint(0, Color.BLUE);
			_renderer.setSeriesPaint(1, Color.GREEN);
			_renderer.setSeriesPaint(2, Color.RED);
			_renderer.setSeriesPaint(4, Color.BLACK);
			_renderer.setSeriesPaint(5, Color.GRAY);
			
			_buyCash.setSelected(false);
			_buildCash.setSelected(false);
			_unmortgageCash.setSelected(false);
		}
	}

	
	private class MinCashListener implements ChartMouseListener {
		@Override
		public void chartMouseClicked(ChartMouseEvent e) {
			_renderer.setSeriesPaint(0, Color.BLUE);
			_renderer.setSeriesPaint(1, Color.GREEN);
			_renderer.setSeriesPaint(2, Color.RED);
			_renderer.setSeriesPaint(4, Color.BLACK);
			_renderer.setSeriesPaint(5, Color.GRAY);
			
			
			
			ChartEntity entity = e.getEntity();
			if(!(entity instanceof XYItemEntity)) {
				_renderer.setSeriesPaint(0, Color.BLUE);
				_renderer.setSeriesPaint(1, Color.GREEN);
				_renderer.setSeriesPaint(2, Color.RED);
				_renderer.setSeriesPaint(4, Color.BLACK);
				_renderer.setSeriesPaint(5, Color.GRAY);
				_moving = -1;
				return;
			}
			XYItemEntity xyItem = (XYItemEntity) entity;
			int loc = xyItem.getSeriesIndex();
			
			if(_changeEnabled) {
				if(loc == 0) {
					_moving = 0;				_buyCash.setSelected(false);

					_renderer.setSeriesPaint(0, Color.YELLOW);
					_buttons.setSelected(_buyCash.getModel(), true);
				}
				else if (loc == 1) {
					_moving = 1;
					_renderer.setSeriesPaint(1, Color.YELLOW);
					_buttons.setSelected(_buildCash.getModel(), true);
				}
				else if (loc == 2) {
					_moving = 2;
					_renderer.setSeriesPaint(2, Color.YELLOW);
					_buttons.setSelected(_unmortgageCash.getModel(), true);
				}
				else {
					_moving = -1;
				}
			}
			
		}

		@Override
		public void chartMouseMoved(ChartMouseEvent e) {
		/*	ChartEntity entity = e.getEntity();
			if(!(entity instanceof XYItemEntity)) {
				_location = -1;
				_renderer.setSeriesPaint(0, Color.BLUE);
				_renderer.setSeriesPaint(1, Color.GREEN);
				_renderer.setSeriesPaint(2, Color.RED);
				_renderer.setSeriesPaint(3JButton, Color.BLACK);
				_renderer.setSeriesPaint(4, Color.GRAY);
				return;JToggleButton
			}
			XYItemEntity xyItem = (XYItemEntity) entity;
			
			_location = xyItem.getSeriesIndex();
			if(_location == 3 || _location == 4 ) _location = -1;
			if(_location != -1) {
				_renderer.setSeriesPaint(_location, Color.YELLOW);
			}
			else {
				_renderer.setSeriesPaint(0, Color.BLUE);
				_renderer.setSeriesPaint(1, Color.GREEN);
				_renderer.setSeriesPaint(2, Color.RED);
				_renderer.setSeriesPaint(3, Color.BLACK);
				_renderer.setSeriesPaint(4, Color.GRAY);
			} */
		}
		
	}
	
	public class RolloverListener implements MouseListener {

		private JButton _button;
		public RolloverListener (JButton _buyCash) {
			_button = _buyCash;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			_button.setForeground(Color.DARK_GRAY);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			_button.setForeground(Color.BLACK);
		}
		
	}
	
	public class MinCashButton implements ActionListener {

		private int line;
		public MinCashButton (int line) {
			this.line = line;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(_changeEnabled) {
				_moving = line;
				_renderer.setSeriesPaint(0, Color.BLUE);
				_renderer.setSeriesPaint(1, Color.GREEN);
				_renderer.setSeriesPaint(2, Color.RED);
				_renderer.setSeriesPaint(4, Color.BLACK);
				_renderer.setSeriesPaint(5, Color.GRAY);
				_renderer.setSeriesPaint(_moving, Color.YELLOW);
			}
			else if (_changeEnabled == false) {
				_buyCash.setSelected(false);
				_buildCash.setSelected(false);
				_unmortgageCash.setSelected(false);
			}
		}
		
	}

}
