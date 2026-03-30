package quantitymeasurement.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import quantitymeasurement.model.Inches;

public class InchesTest {
	@Test
    void testEquality_SameValue() {
        assertTrue(new Inches(1.0).equals(new Inches(1.0)));
    }

    @Test
    void testEquality_DifferentValue() {
        assertFalse(new Inches(1.0).equals(new Inches(5.0)));
    }

    @Test
    void testEquality_NullComparison() {
        assertFalse(new Inches(1.0).equals(null));
    }

    @Test
    void testEquality_SameReference() {
        Inches inches = new Inches(1.0);
        assertTrue(inches.equals(inches));
    }

    @Test
    void testEquality_NonNumericInput() {
        assertFalse(new Inches(1.0).equals(100));
    }
}
