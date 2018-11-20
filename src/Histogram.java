import ij.process.ImageProcessor;

public class Histogram {
	
	private int[] histogram = new int[256];
	private double[] normedHistogram = new double[256];
	private double mean;
	private double variance;
	private double skewness;
	private double kurtosis;
	private double entropy;
	private long numPixels;
	
	/**
	 * Creates the basic histogram structure by binning the image's pixels
	 * @param ip ImageProcessor to generate the histogram from
	 */
	public Histogram(ImageProcessor ip) {
		for(int w = 0; w < ip.getWidth(); w++) {
			for(int h = 0; h < ip.getHeight(); h++) {
				histogram[ip.getPixel(w, h) & 0xFF]++;
			}
		}
		
		//DEBUG OUTPUT
		for(int cnt = 0; cnt < histogram.length; cnt++) {
			System.out.println(cnt + ", " + histogram[cnt]);
		}
		//END DEBUG
		
		numPixels = ip.getPixelCount();
		for(int cnt = 0; cnt < histogram.length; cnt++) {
			normedHistogram[cnt] = (double)histogram[cnt]/(double)numPixels;
		}
		
		mean = calculateMean();
		variance = calculateNthMoment(Moment.VARIANCE);
		skewness = calculateNthMoment(Moment.SKEWNESS);
		kurtosis = calculateNthMoment(Moment.KURTOSIS);
		entropy= calculateEntropy();
	}

	/**
	 * A getter for the histogram array
	 * @return the histogram array
	 */
	public int[] getHistogramArray() {
		return this.histogram;
	}
	
	/**
	 * A getter for the histogram's mean value
	 * @return the mean value
	 */
	public double getMean() {
		return this.mean;
	}
	
	/**
	 * A getter for the histogram's variance
	 * @return the variance
	 */
	public double getVariance() {
		return this.variance;
	}
	
	/**
	 * A getter for the histogram's skewness
	 * @return the skewness
	 */
	public double getSkewness() {
		return this.skewness;
	}
	
	/**
	 * A getter for the histogram's kurtosis
	 * @return the kurtosis
	 */
	public double getKurtosis() {
		return this.kurtosis;
	}
	
	/**
	 * A getter for the histogram's entropy
	 * @return the entropy
	 */
	public double getEntropy() {
		return this.entropy;
	}
	
	/**
	 * Calculates the images mean pixel value from the histogram
	 * @param histogram The histogram as int array to calculate the mean from
	 * @return The mean value
	 */
	private double calculateMean() {
		double sum = 0.0;
		for(int cnt = 0; cnt < normedHistogram.length; cnt++) {
			sum += normedHistogram[cnt] * cnt;
		}
		return sum;
	}
	
	/**
	 * Calculates the nth moment of the pixel values of the image
	 * @param histogram The histogram of the image to work on
	 * @param mean The mean pixel value of the image
	 * @param moment The moment to calculate ({@link Moment})
	 * @return The nth moment
	 */
	private double calculateNthMoment(Moment moment) {
		double sum = 0.0;
		for(int cnt = 0; cnt < normedHistogram.length; cnt++) {
			sum += Math.pow(cnt - mean, moment.getExponent()) * normedHistogram[cnt];
		}
		switch (moment) {
		case KURTOSIS:
			return (sum/Math.pow(Math.sqrt(variance), moment.getExponent())) - 3.0;
		case SKEWNESS:
			return sum/Math.pow(Math.sqrt(variance), moment.getExponent());
		case VARIANCE:
			return sum;
		}
		return 0.0;
	}
	
	/**
	 * Calculates entropy of the image based on the histogram
	 * @return The image's entropy
	 */
	private double calculateEntropy() {
		double sum = 0.0;
		for(int cnt = 0; cnt < histogram.length; cnt++) {
			double histval = normedHistogram[cnt];
			if(histval > 0) {
				sum += normedHistogram[cnt] * Tools.log2(normedHistogram[cnt]);
			}
		}
		return -sum;
	}
	
	private enum Moment {
		VARIANCE(2),
		SKEWNESS(3),
		KURTOSIS(4);
		
		public int getExponent() {
			return exponent;
		}

		private final int exponent;
		
		private Moment(int exponent) {
			this.exponent = exponent;
		}

	}
}
