package bv.gruppeB.projekt3;

/**
 * Enum for Luma coefficients for different colorspaces
 */
public enum Colorspace {
	Average(0.3333333, 0.3333333, 0.3333333),
	BT601(0.299, 0.587, 0.114),
	BT709(0.2126, 0.7152, 0.0722),
	BT2020(0.2627, 0.678, 0.0593);
	
	
	public double getFactorR() {
		return this.factorR;
	}

	public double getFactorG() {
		return this.factorG;
	}

	public double getFactorB() {
		return this.factorB;
	}

	private final double factorR;
	private final double factorG;
	private final double factorB;
	
	private Colorspace(double factorR, double factorG, double factorB) {
		this.factorR = factorR;
		this.factorG = factorG;
		this.factorB = factorB;
	}
}
