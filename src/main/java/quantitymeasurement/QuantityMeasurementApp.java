package quantitymeasurement;

import quantitymeasurement.model.Feet;

public class QuantityMeasurementApp {
	public static boolean areFeetEqual(double value1, double value2) {
		Feet feet1 = new Feet(value1);
	    Feet feet2 = new Feet(value2);
	    return feet1.equals(feet2);
	}

	public static void main(String[] args) {
	    boolean result = areFeetEqual(1.0, 1.0);
	    System.out.println("Input: 1.0 ft and 1.0 ft");
	    System.out.println("Output: Equal (" + result + ")");
	}
}
