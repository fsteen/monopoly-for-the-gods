package edu.brown.cs32.MFTG.gui.old;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestPanel extends JPanel{

	private Rectangle _house1;
	private Rectangle _house2;
	
	private Point _startLoc;
	private Rectangle _moving;
	
	private static int HEIGHT = 1000;
	
	public TestPanel() {
		this.setPreferredSize(new Dimension(200, HEIGHT));
		this.addMouseListener(new HouseListener());
		this.addMouseMotionListener(new HouseMotionListener());
		_house1 = new Rectangle(30, (int) (HEIGHT - 300), 100, 300);
		_house2 = new Rectangle(30, (int) (HEIGHT - 500), 100, 500);
	}
	
	@Override
	protected void paintComponent (Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, 200, HEIGHT);
		drawHouse(g, _house2, Color.BLUE);
		drawHouse(g, _house1, Color.PINK);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Testing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(200, HEIGHT));
		frame.add(new TestPanel());
		frame.pack();
		frame.setVisible(true);
	}
	
	public void drawHouse (Graphics g, Rectangle house, Color color) {
		g.setColor(color);
		g.fillRect(30, (int) (HEIGHT - house.getHeight()), (int) house.getWidth(), (int) house.getHeight());
	}
	
	public void addHeight(double change) {
		System.out.println("adding height "  + -1 * change);
		int height = (int) (_moving.getHeight() + -1 * change);
		System.out.println("height: " + height);
		setHeight(_moving, height);
		
		this.checkHeights();
		this.repaint();
	}
	
	public void checkHeights () {
		if(_house1.getHeight() < 100) {
			setHeight(_house1, 100);
		}
		if(_house1.getHeight() > 1000) {
			setHeight(_house1, 1000);
		}
		if(_moving.equals(_house1) &&_house1.getHeight() > _house2.getHeight()) {
			setHeight(_house1,_house2.getHeight());
		}
		if(_moving.equals(_house2) && _house2.getHeight() < _house1.getHeight()) {
			setHeight(_house2, _house1.getHeight());
		}
		if(_house2.getHeight() > 1000){
			setHeight(_house2, 1000);
		}
	}
	
	public void setHeight(Rectangle house, double h) {
		int height = (int) h;
		house.setSize((int) _moving.getWidth(), height);
		house.setLocation((int) _moving.getX(), (int) (HEIGHT - height));
	}
	
	private class HouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("clicked");
		}

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("pressed");
			_startLoc = e.getLocationOnScreen();
			if(_house1.contains(_startLoc)) {
				_moving = _house1;
			}
			else if(_house2.contains(_startLoc)){
				_moving = _house2;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println("released");
			_startLoc = null;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			System.out.println("entered");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			System.out.println("exited");
		}
		
	}
	
	private class HouseMotionListener implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			System.out.println("dragged");
			if(Math.abs(e.getY() - _startLoc.getY()) < 30)
				addHeight(e.getY() - _startLoc.getY());
			_startLoc = e.getPoint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
}
