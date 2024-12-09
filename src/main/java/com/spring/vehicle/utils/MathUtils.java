package com.spring.vehicle.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Random;

/**
 * MathUtils class provides utility methods for mathematical operations such as parsing,
 * rounding, random number generation, and arithmetic operations with precise control over scale.
 */
public class MathUtils {
	private static final int DEFAULT_SCALE = 2; // Default scale for operations, set to 2 decimal places.

	/**
	 * Converts a String to an integer, returning 0 if conversion fails.
	 *
	 * @param value The string to be converted.
	 * @return The parsed integer or 0 if parsing fails.
	 */
	public static int parseInt(String value) {
		int num = 0;
		if (value != null && !value.trim().isEmpty()) {
			try {
				num = Integer.parseInt(value);
			} catch (Exception ignored) { /* Handle parsing failure silently */ }
		}
		return num;
	}

	/**
	 * Converts a String to a float, returning 0 if conversion fails.
	 *
	 * @param value The string to be converted.
	 * @return The parsed float or 0 if parsing fails.
	 */
	public static float parseFloat(String value) {
		float num = 0;
		if (value != null && !value.trim().isEmpty()) {
			try {
				num = Float.parseFloat(value);
			} catch (Exception ignored) { /* Handle parsing failure silently */ }
		}
		return num;
	}

	/**
	 * Converts a String to a double, returning 0 if conversion fails.
	 *
	 * @param value The string to be converted.
	 * @return The parsed double or 0 if parsing fails.
	 */
	public static double parseDouble(String value) {
		double num = 0;
		if (value != null && !value.trim().isEmpty()) {
			try {
				num = Double.parseDouble(value);
			} catch (Exception ignored) { /* Handle parsing failure silently */ }
		}
		return num;
	}

	/**
	 * Converts a String to a long, returning 0 if conversion fails.
	 *
	 * @param value The string to be converted.
	 * @return The parsed long or 0 if parsing fails.
	 */
	public static long parseLong(String value) {
		long num = 0;
		if (value != null && !value.trim().isEmpty()) {
			try {
				num = Long.parseLong(value);
			} catch (Exception ignored) { /* Handle parsing failure silently */ }
		}
		return num;
	}

	/**
	 * Converts a String to a short, returning 0 if conversion fails.
	 *
	 * @param value The string to be converted.
	 * @return The parsed short or 0 if parsing fails.
	 */
	public static short parseShort(String value) {
		short num = 0;
		if (value != null && !value.trim().isEmpty()) {
			try {
				num = Short.parseShort(value);
			} catch (Exception ignored) { /* Handle parsing failure silently */ }
		}
		return num;
	}

	/**
	 * Rounds a double to a specified number of decimal places.
	 *
	 * @param value  The double to be rounded.
	 * @param places The number of decimal places.
	 * @return The rounded double value.
	 */
	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException("Scale must be non-negative");
		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	/**
	 * Generates a random integer within a specified range.
	 *
	 * @param min The lower bound of the range.
	 * @param max The upper bound of the range.
	 * @return A random integer between min and max (inclusive).
	 */
	public static int random(int min, int max) {
		Random random = new Random();
		return random.nextInt((max - min) + 1) + min;
	}

	/**
	 * Adds two doubles with a specified precision.
	 *
	 * @param v1 First operand.
	 * @param v2 Second operand.
	 * @return The sum of v1 and v2, rounded to the default scale.
	 */
	public static double add(Double v1, Double v2) {
		v1 = (v1 != null) ? v1 : 0;
		v2 = (v2 != null) ? v2 : 0;
		return add(v1, v2, DEFAULT_SCALE);
	}

	/**
	 * Adds two doubles with specified scale for precision.
	 *
	 * @param v1    First operand.
	 * @param v2    Second operand.
	 * @param scale The precision of the result.
	 * @return The sum of v1 and v2, rounded to the specified scale.
	 */
	public static double add(double v1, double v2, int scale) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).setScale(scale, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * Subtracts v2 from v1 with the default precision.
	 *
	 * @param v1 Minuend.
	 * @param v2 Subtrahend.
	 * @return The difference between v1 and v2, rounded to the default scale.
	 */
	public static double sub(double v1, double v2) {
		return sub(v1, v2, DEFAULT_SCALE);
	}

	/**
	 * Subtracts v2 from v1 with specified scale for precision.
	 *
	 * @param v1    Minuend.
	 * @param v2    Subtrahend.
	 * @param scale The precision of the result.
	 * @return The difference between v1 and v2, rounded to the specified scale.
	 */
	public static double sub(double v1, double v2, int scale) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).setScale(scale, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * Multiplies two doubles with the default precision.
	 *
	 * @param v1 First operand.
	 * @param v2 Second operand.
	 * @return The product of v1 and v2, rounded to the default scale.
	 */
	public static double mul(double v1, double v2) {
		return mul(v1, v2, DEFAULT_SCALE);
	}

	/**
	 * Multiplies two doubles with specified scale for precision.
	 *
	 * @param v1    First operand.
	 * @param v2    Second operand.
	 * @param scale The precision of the result.
	 * @return The product of v1 and v2, rounded to the specified scale.
	 */
	public static double mul(double v1, double v2, int scale) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).setScale(scale, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * Divides v1 by v2 with the default precision, throwing an exception for zero denominator.
	 *
	 * @param v1 Dividend.
	 * @param v2 Divisor.
	 * @return The quotient of v1 and v2, rounded to the default scale.
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEFAULT_SCALE);
	}

	/**
	 * Divides v1 by v2 with specified scale, rounding the result as necessary.
	 *
	 * @param v1    Dividend.
	 * @param v2    Divisor.
	 * @param scale The precision of the result.
	 * @return The quotient of v1 and v2, rounded to the specified scale.
	 * @throws IllegalArgumentException if the scale is negative.
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) throw new IllegalArgumentException("Scale must be non-negative");
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * Formats a double as a currency string in the Canada locale.
	 *
	 * @param price The price to be formatted.
	 * @return The price formatted as a currency string.
	 */
	public static String priceToString(Double price) {
		return (price != null) ? DecimalFormat.getCurrencyInstance(Locale.CANADA).format(price) : null;
	}
}
