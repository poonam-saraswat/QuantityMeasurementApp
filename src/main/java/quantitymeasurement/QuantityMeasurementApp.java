package quantitymeasurement;

import quantitymeasurement.model.IMeasurable;
import quantitymeasurement.model.LengthUnit;
import quantitymeasurement.model.Quantity;
import quantitymeasurement.model.TemperatureUnit;
import quantitymeasurement.model.VolumeUnit;
import quantitymeasurement.model.WeightUnit;

public class QuantityMeasurementApp {

    public static <U extends IMeasurable> void demonstrateEquality(
            Quantity<U> q1,
            Quantity<U> q2) {

        System.out.println(q1 + " equals " + q2 + " -> " + q1.equals(q2));
    }

    public static <U extends IMeasurable> void demonstrateConversion(
            Quantity<U> q,
            U targetUnit) {

        System.out.println(q + " converted -> " + q.convertTo(targetUnit));
    }

    public static <U extends IMeasurable> void demonstrateAddition(
            Quantity<U> q1,
            Quantity<U> q2,
            U targetUnit) {

        System.out.println(q1 + " + " + q2 + " -> " + q1.add(q2, targetUnit));
    }

    public static void main(String[] args) {

        System.out.println(" LENGTH EXAMPLES ");

        Quantity<LengthUnit> l1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(12.0, LengthUnit.INCHES);

        demonstrateEquality(l1, l2);
        demonstrateConversion(l1, LengthUnit.INCHES);
        demonstrateAddition(l1, l2, LengthUnit.FEET);

        System.out.println("\n WEIGHT EXAMPLES ");

        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        demonstrateEquality(w1, w2);
        demonstrateConversion(w1, WeightUnit.GRAM);
        demonstrateAddition(w1, w2, WeightUnit.KILOGRAM);

        System.out.println("\n VOLUME EXAMPLES ");

        Quantity<VolumeUnit> volume1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> volume2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> volume3 = new Quantity<>(1.0, VolumeUnit.GALLON);

        System.out.println(volume1.equals(volume2));
        System.out.println(volume1.equals(volume3));

        System.out.println(volume1.convertTo(VolumeUnit.MILLILITRE));
        System.out.println(volume3.convertTo(VolumeUnit.LITRE));

        System.out.println(volume1.add(volume2));
        System.out.println(volume1.add(volume3, VolumeUnit.MILLILITRE));

        System.out.println("\n SUBTRACTION & DIVISION ");

        Quantity<LengthUnit> q1 = new Quantity<>(10, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(6, LengthUnit.INCHES);

        System.out.println(q1.subtract(q2));
        System.out.println(q1.subtract(q2, LengthUnit.INCHES));
        System.out.println(q1.divide(q2));

        System.out.println("\n TEMPERATURE EXAMPLES ");

        Quantity<TemperatureUnit> t1 =
                new Quantity<>(0.0, TemperatureUnit.CELSIUS);

        Quantity<TemperatureUnit> t2 =
                new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);

        Quantity<TemperatureUnit> t3 =
                new Quantity<>(273.15, TemperatureUnit.KELVIN);

        // Equality
        demonstrateEquality(t1, t2);
        demonstrateEquality(t1, t3);

        // Conversion
        demonstrateConversion(t1, TemperatureUnit.FAHRENHEIT);
        demonstrateConversion(t2, TemperatureUnit.CELSIUS);

        // Unsupported arithmetic
        try {
            System.out.println(t1.add(t2));
        } catch (UnsupportedOperationException e) {
            System.out.println("Arithmetic error: " + e.getMessage());
        }
    }
}