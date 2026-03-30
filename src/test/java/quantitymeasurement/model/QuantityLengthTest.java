package quantitymeasurement.model;

import static org.junit.jupiter.api.Assertions.*;
import static quantitymeasurement.model.LengthUnit.*;

import org.junit.jupiter.api.Test;

public class QuantityLengthTest {

    @Test
    void testEquality_FeetToFeet_SameValue() {
        QuantityLength q1 = new QuantityLength(1.0, FEET);
        QuantityLength q2 = new QuantityLength(1.0, FEET);
        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_InchToFeet_EquivalentValue() {
        QuantityLength q1 = new QuantityLength(12.0, INCHES);
        QuantityLength q2 = new QuantityLength(1.0, FEET);
        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_DifferentValue() {
        QuantityLength q1 = new QuantityLength(1.0, FEET);
        QuantityLength q2 = new QuantityLength(2.0, FEET);
        assertFalse(q1.equals(q2));
    }

    @Test
    void testEquality_NullComparison() {
        QuantityLength q1 = new QuantityLength(1.0, FEET);
        assertFalse(q1.equals(null));
    }

    @Test
    void testEquality_SameReference() {
        QuantityLength q1 = new QuantityLength(1.0, FEET);
        assertTrue(q1.equals(q1));
    }

    // ---------------- CONVERSION TESTS ----------------

    @Test
    void testConversion_FeetToInches() {

        QuantityLength q = new QuantityLength(1.0, FEET);
        QuantityLength result = q.convertTo(INCHES);

        assertEquals(12.0, result.getValue(), 1e-6);
    }

    @Test
    void testConversion_YardsToInches() {

        QuantityLength q = new QuantityLength(1.0, YARDS);
        QuantityLength result = q.convertTo(INCHES);

        assertEquals(36.0, result.getValue(), 1e-6);
    }

    @Test
    void testConversion_RoundTrip() {

        QuantityLength q = new QuantityLength(5.0, FEET);

        QuantityLength yards = q.convertTo(YARDS);
        QuantityLength back = yards.convertTo(FEET);

        assertEquals(5.0, back.getValue(), 1e-6);
    }

    @Test
    void testConversion_InvalidUnit() {

        QuantityLength q = new QuantityLength(1.0, FEET);

        assertThrows(IllegalArgumentException.class,
                () -> q.convertTo(null));
    }


    @Test
    void testAddition_SameUnit_FeetPlusFeet() {

        QuantityLength q1 = new QuantityLength(1.0, FEET);
        QuantityLength q2 = new QuantityLength(2.0, FEET);

        QuantityLength result = q1.add(q2, FEET);

        assertEquals(new QuantityLength(3.0, FEET), result);
    }

    @Test
    void testAddition_CrossUnit_FeetPlusInches() {

        QuantityLength q1 = new QuantityLength(1.0, FEET);
        QuantityLength q2 = new QuantityLength(12.0, INCHES);

        QuantityLength result = q1.add(q2, FEET);

        assertEquals(new QuantityLength(2.0, FEET), result);
    }

    @Test
    void testAddition_Commutativity() {

        QuantityLength q1 = new QuantityLength(1.0, FEET);
        QuantityLength q2 = new QuantityLength(12.0, INCHES);

        QuantityLength r1 = q1.add(q2, FEET);
        QuantityLength r2 = q2.add(q1, FEET);

        assertTrue(r1.equals(r2));
    }

    @Test
    void testAddition_WithZero() {

        QuantityLength q1 = new QuantityLength(5.0, FEET);
        QuantityLength zero = new QuantityLength(0.0, INCHES);

        QuantityLength result = q1.add(zero, FEET);

        assertEquals(new QuantityLength(5.0, FEET), result);
    }

    @Test
    void testAddition_NullOperand() {

        QuantityLength q1 = new QuantityLength(1.0, FEET);

        assertThrows(IllegalArgumentException.class,
                () -> q1.add(null, FEET));
    }
}