package quantitymeasurement.model;

import java.util.Objects;

public class QuantityLength {
	private final double value;
    private final LengthUnit unit;

    private static final double EPSILON = 1e-6;

    public QuantityLength(double value, LengthUnit unit) {
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid numeric value");

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        this.value = value;
        this.unit = unit;
    }

    //  UC5 
    public static double convert(double value,
                                 LengthUnit source,
                                 LengthUnit target) {

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid numeric value");

        if (source == null || target == null)
            throw new IllegalArgumentException("Unit cannot be null");

        // Step 1: Convert to base unit (feet)
        double inFeet = source.toFeet(value);

        // Step 2: Convert from base to target
        return target.fromFeet(inFeet);
    }

    // Instance conversion (returns new immutable object)
    public QuantityLength convertTo(LengthUnit target) {
        double convertedValue =
                convert(this.value, this.unit, target);

        return new QuantityLength(convertedValue, target);
    }

    private double toBase() {
        return unit.toFeet(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityLength)) return false;

        QuantityLength other = (QuantityLength) obj;

        return Math.abs(this.toBase() - other.toBase()) < EPSILON;
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}
