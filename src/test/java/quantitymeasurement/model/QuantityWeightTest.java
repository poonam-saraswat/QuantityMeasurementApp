package quantitymeasurement.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class QuantityWeightTest {
	private static final double EPSILON = 1e-6;

    // Equality Tests
    @Test
    void testEquality_KilogramToKilogram_SameValue() {
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);

        assertEquals(w1, w2);
    }

    @Test
    void testEquality_KilogramToKilogram_DifferentValue() {
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(2.0, WeightUnit.KILOGRAM);

        assertNotEquals(w1, w2);
    }

    @Test
    void testEquality_KilogramToGram_EquivalentValue() {
        QuantityWeight kg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight g = new QuantityWeight(1000.0, WeightUnit.GRAM);

        assertEquals(kg, g);
    }

    @Test
    void testEquality_GramToKilogram_EquivalentValue() {
        QuantityWeight g = new QuantityWeight(1000.0, WeightUnit.GRAM);
        QuantityWeight kg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);

        assertEquals(g, kg);
    }

    @Test
    void testEquality_PoundToGram_EquivalentValue() {
        QuantityWeight pound = new QuantityWeight(1.0, WeightUnit.POUND);
        QuantityWeight gram = new QuantityWeight(453.592, WeightUnit.GRAM);

        assertEquals(pound, gram);
    }

    @Test
    void testEquality_NullComparison() {
        QuantityWeight kg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);

        assertNotEquals(kg, null);
    }

    @Test
    void testEquality_SameReference() {
        QuantityWeight kg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);

        assertEquals(kg, kg);
    }

    @Test
    void testEquality_ZeroValue() {
        QuantityWeight kg = new QuantityWeight(0.0, WeightUnit.KILOGRAM);
        QuantityWeight g = new QuantityWeight(0.0, WeightUnit.GRAM);

        assertEquals(kg, g);
    }

    @Test
    void testEquality_NegativeWeight() {
        QuantityWeight kg = new QuantityWeight(-1.0, WeightUnit.KILOGRAM);
        QuantityWeight g = new QuantityWeight(-1000.0, WeightUnit.GRAM);

        assertEquals(kg, g);
    }

    // -----------------------------
    // Conversion Tests
    // -----------------------------

    @Test
    void testConversion_KilogramToGram() {
        QuantityWeight kg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);

        QuantityWeight result = kg.convertTo(WeightUnit.GRAM);

        assertEquals(1000.0, result.getValue(), EPSILON);
        assertEquals(WeightUnit.GRAM, result.getUnit());
    }

    @Test
    void testConversion_PoundToKilogram() {
        QuantityWeight pound = new QuantityWeight(2.20462, WeightUnit.POUND);

        QuantityWeight result = pound.convertTo(WeightUnit.KILOGRAM);

        assertEquals(1.0, result.getValue(), 0.01);
    }

    @Test
    void testConversion_KilogramToPound() {
        QuantityWeight kg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);

        QuantityWeight result = kg.convertTo(WeightUnit.POUND);

        assertEquals(2.20462, result.getValue(), 0.01);
    }

    @Test
    void testConversion_SameUnit() {
        QuantityWeight kg = new QuantityWeight(5.0, WeightUnit.KILOGRAM);

        QuantityWeight result = kg.convertTo(WeightUnit.KILOGRAM);

        assertEquals(5.0, result.getValue(), EPSILON);
    }

    @Test
    void testConversion_ZeroValue() {
        QuantityWeight kg = new QuantityWeight(0.0, WeightUnit.KILOGRAM);

        QuantityWeight result = kg.convertTo(WeightUnit.GRAM);

        assertEquals(0.0, result.getValue(), EPSILON);
    }

    @Test
    void testConversion_NegativeValue() {
        QuantityWeight kg = new QuantityWeight(-1.0, WeightUnit.KILOGRAM);

        QuantityWeight result = kg.convertTo(WeightUnit.GRAM);

        assertEquals(-1000.0, result.getValue(), EPSILON);
    }

    @Test
    void testConversion_RoundTrip() {
        QuantityWeight kg = new QuantityWeight(1.5, WeightUnit.KILOGRAM);

        QuantityWeight result =
                kg.convertTo(WeightUnit.GRAM)
                        .convertTo(WeightUnit.KILOGRAM);

        assertEquals(1.5, result.getValue(), EPSILON);
    }

    // -----------------------------
    // Addition Tests
    // -----------------------------

    @Test
    void testAddition_SameUnit_KilogramPlusKilogram() {
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(2.0, WeightUnit.KILOGRAM);

        QuantityWeight result = w1.add(w2);

        assertEquals(3.0, result.getValue(), EPSILON);
        assertEquals(WeightUnit.KILOGRAM, result.getUnit());
    }

    @Test
    void testAddition_CrossUnit_KilogramPlusGram() {
        QuantityWeight kg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight g = new QuantityWeight(1000.0, WeightUnit.GRAM);

        QuantityWeight result = kg.add(g);

        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(WeightUnit.KILOGRAM, result.getUnit());
    }

    @Test
    void testAddition_CrossUnit_PoundPlusKilogram() {
        QuantityWeight pound = new QuantityWeight(2.20462, WeightUnit.POUND);
        QuantityWeight kg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);

        QuantityWeight result = pound.add(kg);

        assertEquals(4.40924, result.getValue(), 0.01);
    }

    @Test
    void testAddition_ExplicitTargetUnit_Gram() {
        QuantityWeight kg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight g = new QuantityWeight(1000.0, WeightUnit.GRAM);

        QuantityWeight result = kg.add(g, WeightUnit.GRAM);

        assertEquals(2000.0, result.getValue(), EPSILON);
        assertEquals(WeightUnit.GRAM, result.getUnit());
    }

    @Test
    void testAddition_WithZero() {
        QuantityWeight kg = new QuantityWeight(5.0, WeightUnit.KILOGRAM);
        QuantityWeight zero = new QuantityWeight(0.0, WeightUnit.GRAM);

        QuantityWeight result = kg.add(zero);

        assertEquals(5.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_NegativeValues() {
        QuantityWeight kg = new QuantityWeight(5.0, WeightUnit.KILOGRAM);
        QuantityWeight g = new QuantityWeight(-2000.0, WeightUnit.GRAM);

        QuantityWeight result = kg.add(g);

        assertEquals(3.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_LargeValues() {
        QuantityWeight w1 = new QuantityWeight(1e6, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1e6, WeightUnit.KILOGRAM);

        QuantityWeight result = w1.add(w2);

        assertEquals(2e6, result.getValue(), EPSILON);
    }

    // Validation Tests

    @Test
    void testConstructor_NullUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuantityWeight(1.0, null));
    }

}
