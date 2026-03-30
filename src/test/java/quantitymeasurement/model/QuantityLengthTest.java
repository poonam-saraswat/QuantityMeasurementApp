package quantitymeasurement.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static quantitymeasurement.model.LengthUnit.*;

import org.junit.jupiter.api.Test;

public class QuantityLengthTest {
	@Test
    void testEquality_FeetToFeet_SameValue() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);
        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_InchToFeet_EquivalentValue() {
        QuantityLength q1 = new QuantityLength(12.0, LengthUnit.INCH);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);
        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_DifferentValue() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(2.0, LengthUnit.FEET);
        assertFalse(q1.equals(q2));
    }

    @Test
    void testEquality_NullComparison() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        assertFalse(q1.equals(null));
    }

    @Test
    void testEquality_SameReference() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        assertTrue(q1.equals(q1));
    }
    
    
    @Test
    void testConversion_FeetToInches() {
        assertEquals(12.0,
            QuantityLength.convert(1.0, FEET, INCH),
            1e-6);
    }

    @Test
    void testConversion_YardsToInches() {
        assertEquals(36.0,
            QuantityLength.convert(1.0, YARDS, INCH),
            1e-6);
    }

    @Test
    void testConversion_RoundTrip() {
        double original = 5.0;

        double converted =
            QuantityLength.convert(original, FEET, YARDS);

        double back =
            QuantityLength.convert(converted, YARDS, FEET);

        assertEquals(original, back, 1e-6);
    }

    @Test
    void testConversion_InvalidUnit() {
        assertThrows(IllegalArgumentException.class,
            () -> QuantityLength.convert(1.0, null, FEET));
    }
    
}
