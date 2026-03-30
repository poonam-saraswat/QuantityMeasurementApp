package quantitymeasurement;

import quantitymeasurement.model.LengthUnit;
import quantitymeasurement.model.QuantityLength;

public class QuantityMeasurementApp {
	public static void demonstrateLengthConversion(
	        double value,
	        LengthUnit from,
	        LengthUnit to) {

	    double result =
	            QuantityLength.convert(value, from, to);

	    System.out.println("convert(" + value + ", "
	            + from + ", " + to + ") → " + result);
	}

	public static void demonstrateLengthConversion(
	        QuantityLength quantity,
	        LengthUnit target) {

	    QuantityLength converted =
	            quantity.convertTo(target);

	    System.out.println(quantity + " → " + converted);
	}
	
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
		
		 //UC5 Conversion Tests
	    demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCH);
	    demonstrateLengthConversion(3.0, LengthUnit.YARDS, LengthUnit.FEET);
	    demonstrateLengthConversion(36.0, LengthUnit.INCH, LengthUnit.YARDS);
	    demonstrateLengthConversion(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCH);
	    demonstrateLengthConversion(0.0, LengthUnit.FEET, LengthUnit.INCH);

	    System.out.println();


	    //UC5 Instance Method Conversion
	    QuantityLength lengthInYards =
	            new QuantityLength(2.0, LengthUnit.YARDS);

	    demonstrateLengthConversion(lengthInYards, LengthUnit.INCH);
	    
	    
	    
	    System.out.println();

	 // UC6 Addition Tests
	 QuantityLength a =
	         new QuantityLength(1.0, LengthUnit.FEET);

	 QuantityLength b =
	         new QuantityLength(12.0, LengthUnit.INCH);

	 QuantityLength result1 = a.add(b);

	 System.out.println("add(" + a + ", " + b + ")");
	 System.out.println("Output: " + result1);
	 System.out.println();

	 QuantityLength result2 =
	         QuantityLength.add(a, b, LengthUnit.INCH);

	 System.out.println("add(" + a + ", " + b + ") in INCH");
	 System.out.println("Output: " + result2);
		
	}
}
