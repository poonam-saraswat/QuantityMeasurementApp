package quantitymeasurement;

import quantitymeasurement.model.LengthUnit;
import quantitymeasurement.model.QuantityLength;

public class QuantityMeasurementApp {
	public static void main(String[] args) {
		QuantityLength q1 =
		        new QuantityLength(1.0, LengthUnit.YARDS);

		QuantityLength q2 =
		        new QuantityLength(3.0, LengthUnit.FEET);

		System.out.println("Input: " + q1 + " and " + q2);
		System.out.println("Output: Equal (" + q1.equals(q2) + ")");
		System.out.println();

		QuantityLength q3 =
		        new QuantityLength(1.0, LengthUnit.CENTIMETERS);

		QuantityLength q4 =
		        new QuantityLength(0.393701, LengthUnit.INCH);

		System.out.println("Input: " + q3 + " and " + q4);
		System.out.println("Output: Equal (" + q3.equals(q4) + ")");	
	}
}
