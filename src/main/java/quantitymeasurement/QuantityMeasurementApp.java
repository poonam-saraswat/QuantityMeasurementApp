package quantitymeasurement;

import quantitymeasurement.model.LengthUnit;
import quantitymeasurement.model.QuantityLength;

public class QuantityMeasurementApp {
//	public static boolean areFeetEqual(double value1, double value2) {
//		Feet feet1 = new Feet(value1);
//	    Feet feet2 = new Feet(value2);
//	    return feet1.equals(feet2);
//	}
//	
//	public static boolean areInchesEqual(double value1, double value2) {
//        return new Inches(value1).equals(new Inches(value2));
//    }
//
//	public static void main(String[] args) {
//boolean inchresult = areFeetEqual(1.0, 1.0);
//	    
//	    boolean feetResult = areFeetEqual(1.0, 1.0);
//	    
//	    System.out.println("Input: 1.0 ft and 1.0 ft");
//	    System.out.println("Output: Equal (" + inchresult + ")\n");
//	    
//	    System.out.println("Input: 1.0 inch and 1.0 inch");
//        System.out.println("Output: Equal (" + feetResult + ")");

	public static void main(String[] args) {

        QuantityLength q1 =
                new QuantityLength(1.0, LengthUnit.FEET);

        QuantityLength q2 =
                new QuantityLength(12.0, LengthUnit.INCH);

        boolean result1 = q1.equals(q2);

        System.out.println("Input: " + q1 + " and " + q2);
        System.out.println("Output: Equal (" + result1 + ")");
        System.out.println();

        QuantityLength q3 =
                new QuantityLength(1.0, LengthUnit.INCH);

        QuantityLength q4 =
                new QuantityLength(1.0, LengthUnit.INCH);

        boolean result2 = q3.equals(q4);

        System.out.println("Input: " + q3 + " and " + q4);
        System.out.println("Output: Equal (" + result2 + ")");
    }
}
