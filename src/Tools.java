public class Tools {

	/**
	 * Limits an input value to an input interval.
	 * @param value The value to be clamped to the interval.
	 * @param min The minimum value of the interval.
	 * @param max The maximum value of the interval.
	 * @return The clamped value.
	 */
	public static int clamp(int value, int min, int max) {
		return Math.min(Math.max(min, value), max);
	}
	
	/**
	 * A simple method to calculate log2 of Integer values.
	 * Beware of precision loss due to rounding error if used in a precision critical application.
	 * @param n The value to calculate log2 of.
	 * @return log2(n)
	 */
	public static double log2(double n){
	    return Math.log(n)/Math.log(2.0);
	}
}
