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
    
    // UC6 — Instance addition
    public QuantityLength add(QuantityLength other) {

        if (other == null)
            throw new IllegalArgumentException("Second operand cannot be null");

        // Convert both to base unit (feet)
        double sumInFeet = this.toBase() + other.toBase();

        // Convert result back to unit of first operand
        double resultValue = unit.fromFeet(sumInFeet);

        return new QuantityLength(resultValue, this.unit);
    }
    
    public static QuantityLength add(
            QuantityLength q1,
            QuantityLength q2,
            LengthUnit targetUnit) {

        if (q1 == null || q2 == null)
            throw new IllegalArgumentException("Operands cannot be null");

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double sumInFeet = q1.toBase() + q2.toBase();

        double resultValue = targetUnit.fromFeet(sumInFeet);

        return new QuantityLength(resultValue, targetUnit);
    }
    
    public static QuantityLength add(
            double v1, LengthUnit u1,
            double v2, LengthUnit u2,
            LengthUnit targetUnit) {

        QuantityLength q1 = new QuantityLength(v1, u1);
        QuantityLength q2 = new QuantityLength(v2, u2);

        return add(q1, q2, targetUnit);
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
