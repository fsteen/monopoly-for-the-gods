package edu.brown.cs32.MFTG.gui;

import edu.brown.cs32.MFTG.gui.Constants.Orientation;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class Helper {

	public Helper() {
		// TODO Auto-generated constructor stub
	}
	
	public static BufferedImage rotate (BufferedImage image, Orientation orientation) {
		for(int i=0; i*Math.PI/2 < orientation.getRotation(); i++) {
			AffineTransform transform = new AffineTransform();
			transform.translate(image.getHeight()/2, image.getWidth()/2);
			transform.rotate(-Math.PI/2);
			transform.translate(-image.getWidth()/2, -image.getHeight()/2);
			AffineTransformOp transformOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
			BufferedImage rotate = new BufferedImage(image.getHeight(), image.getWidth(), image.getType());
			transformOp.filter(image, rotate);
			image = rotate;
		}
		image = resize(image, orientation.getWidth(), orientation.getHeight());
		return image;
	}
	
	public static BufferedImage resize(BufferedImage image, int width, int height) {
		Image im =  image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		BufferedImage buf = new BufferedImage(width, height, image.getType());
		Graphics2D brush = buf.createGraphics();
		brush.drawImage(im, 0, 0, null);
		return buf;
	}
	
	public static BufferedImage copy(BufferedImage original) {
		ColorModel cm = original.getColorModel();
		WritableRaster raster = original.copyData(null);
		return new BufferedImage(cm, raster, original.isAlphaPremultiplied(), null);
	}

}
