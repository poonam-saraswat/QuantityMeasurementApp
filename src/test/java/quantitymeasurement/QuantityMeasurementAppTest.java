package quantitymeasurement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quantitymeasurement.model.LengthUnit;
import quantitymeasurement.model.Quantity;
import quantitymeasurement.model.WeightUnit;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuantityMeasurementAppTest {

	// Length addition: Feet + Inches
    @Test
    void testAddition_FeetPlusInches() {

        Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> inches = new Quantity<>(12.0, LengthUnit.INCHES);

        Quantity<LengthUnit> result = feet.add(inches, LengthUnit.FEET);

        assertEquals(2.0, result.getValue());
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    // Length addition: Inches + Feet
    @Test
    void testAddition_InchesPlusFeet() {

        Quantity<LengthUnit> inches = new Quantity<>(12.0, LengthUnit.INCHES);
        Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> result = inches.add(feet, LengthUnit.INCHES);

        assertEquals(24.0, result.getValue());
        assertEquals(LengthUnit.INCHES, result.getUnit());
    }

    // Weight addition: Kilogram + Gram
    @Test
    void testAddition_KilogramPlusGram() {

        Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> gram = new Quantity<>(1000.0, WeightUnit.GRAM);

        Quantity<WeightUnit> result = kg.add(gram, WeightUnit.KILOGRAM);

        assertEquals(2.0, result.getValue());
        assertEquals(WeightUnit.KILOGRAM, result.getUnit());
    }

    // Weight addition: Gram + Kilogram
    @Test
    void testAddition_GramPlusKilogram() {

        Quantity<WeightUnit> gram = new Quantity<>(1000.0, WeightUnit.GRAM);
        Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> result = gram.add(kg, WeightUnit.GRAM);

        assertEquals(2000.0, result.getValue());
        assertEquals(WeightUnit.GRAM, result.getUnit());
    }

    // Addition with zero
    @Test
    void testAddition_WithZero() {

        Quantity<LengthUnit> l1 = new Quantity<>(0.0, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(12.0, LengthUnit.INCHES);

        Quantity<LengthUnit> result = l1.add(l2, LengthUnit.FEET);

        assertEquals(1.0, result.getValue());
    }

    // Addition with negative value
    @Test
    void testAddition_NegativeValue() {

        Quantity<WeightUnit> w1 = new Quantity<>(-1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        Quantity<WeightUnit> result = w1.add(w2, WeightUnit.KILOGRAM);

        assertEquals(0.0, result.getValue());
    }

    // Commutativity test (a + b = b + a)
    @Test
    void testAddition_Commutativity() {

        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        Quantity<WeightUnit> result1 = w1.add(w2, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> result2 = w2.add(w1, WeightUnit.KILOGRAM);

        assertEquals(result1.getValue(), result2.getValue());
    }

    // Large value addition
    @Test
    void testAddition_LargeValues() {

        Quantity<WeightUnit> w1 = new Quantity<>(5000.0, WeightUnit.GRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(5.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> result = w1.add(w2, WeightUnit.KILOGRAM);

        assertEquals(10.0, result.getValue());
    }
}