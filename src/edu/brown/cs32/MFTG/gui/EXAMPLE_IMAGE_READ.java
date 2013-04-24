/*package old;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import edu.brown.cs32.MFTG.gui.Constants.Orientation;

public abstract class PropertyButton extends JButton {
	
	protected BufferedImage _background;
	protected Orientation _orientation;
	
	public PropertyButton(PropertyColor color, Orientation orientation) {
		super();
		this.setMargin(new Insets(0, 0, 0, 0));
		this.setBackground(Color.BLACK);
		this.setBorder(null);
		_orientation = orientation;
		
		try {
			_background = ImageIO.read(new File(color.getFile()));
			this.addActionListener(new PropertyButtonListener());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class PropertyButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("hi");
		}
	}
	
	 To draw the buffered image use 
	 g.drawImage(image, 0, 0, null); where g is a Graphics 
}
*/