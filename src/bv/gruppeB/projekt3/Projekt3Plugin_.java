package bv.gruppeB.projekt3;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Projekt3Plugin_ implements PlugInFilter{

	@Override
	public int setup(String arg, ImagePlus imp) {
		// TODO Auto-generated method stub
		return DOES_RGB;
	}

	@Override
	public void run(ImageProcessor ip) {
		//Execution incomplete - sample code below
		convertToGreyscaleClassic(ip, Colorspace.BT709);
		Histogram histogram = new Histogram(ip);
	}
	
	
	/**
	 * Converts the RGB image to a grayscale image (achromatic RGB image)
	 * @param ip ImageProcessor of the image to modify
	 * @param scaleRadius true if the Radius should be scaled from sqrt(3*255^2) instead of just 255
	 */
	private void convertToGrayscaleByIntensity(ImageProcessor ip, boolean scaleRadius) {
		double scale = 255.0/Math.sqrt(3*255*255);
		for(int w = 0; w < ip.getWidth(); w++) {
			for(int h = 0; h < ip.getHeight(); h++) {
				int pixel = ip.getPixel(w, h);
				int r = (pixel >> 16) & 0xFF;
				int g = (pixel >> 8) & 0xFF;
				int b = pixel & 0xFF;
				int vectorLength = (int)Math.sqrt(r*r+g*g+b*b);
				
				int grey;
				if(scaleRadius) {
					grey = Tools.clamp((int)(((double)vectorLength)/scale), 0, 255);
				}else {
					grey = Tools.clamp(vectorLength, 0, 255);
				}
				ip.set(w, h, grey);
			}
		}
	}
	
	/**
	 * Converts the RGB image to a grayscale image (achromatic RGB image) using a given colorspace
	 * @param ip ImageProcessor of the image to mmodify
	 * @param colorspace The {@link Colorspace} to use for calculation
	 */
	private void convertToGreyscaleClassic(ImageProcessor ip, Colorspace colorspace) {
		for(int w = 0; w < ip.getWidth(); w++) {
			for(int h = 0; h < ip.getHeight(); h++) {
				int pixel = ip.getPixel(w, h);
				int r = (pixel >> 16) & 0xFF;
				int g = (pixel >> 8) & 0xFF;
				int b = pixel & 0xFF;
				int newR = (int)(r * colorspace.getFactorR());
				int newG = (int)(g * colorspace.getFactorG());
				int newB = (int)(b * colorspace.getFactorB());
				int newPixel = newR + newG + newB;
				newPixel = newPixel << 16 | newPixel << 8 | newPixel;
				ip.set(w, h, newPixel);
			}
		}
	}	

}
