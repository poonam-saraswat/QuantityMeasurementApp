package quantitymeasurement.model;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import quantitymeasurement.model.Feet;

public class FeetTest {
	@Test
    void testEquality_SameValue() {
        assertTrue(new Feet(1.0).equals(new Feet(1.0)));
    }

    @Test
    void testEquality_DifferentValue() {
        assertFalse(new Feet(1.0).equals(new Feet(2.0)));
    }

    @Test
    void testEquality_NullComparison() {
        assertFalse(new Feet(1.0).equals(null));
    }

    @Test
    void testEquality_SameReference() {
        Feet feet = new Feet(1.0);
        assertTrue(feet.equals(feet));
    }

    @Test
    void testEquality_NonNumericInput() {
        assertFalse(new Feet(1.0).equals("Invalid"));
    }
}
