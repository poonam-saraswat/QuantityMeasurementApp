package quantitymeasurement.model;

import java.util.Objects;

public class QuantityLength {
	private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }

        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    private double convertToBase() {
        return unit.toFeet(value);   // convert everything to FEET
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        QuantityLength other = (QuantityLength) obj;

        return Double.compare(this.convertToBase(),
                              other.convertToBase()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(convertToBase());
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", \"" + unit.name().toLowerCase() + "\")";
    }
}
