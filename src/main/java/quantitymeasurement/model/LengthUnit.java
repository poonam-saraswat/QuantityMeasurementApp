package quantitymeasurement.model;

public enum  LengthUnit {
	FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    // getter for test cases
    public double getConversionFactor() {
        return conversionFactor;
    }

    // convert value in this unit → base unit (feet)
    public double convertToBaseUnit(double value) {
        return value * conversionFactor;
    }

    // convert base unit (feet) → this unit
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / conversionFactor;
    }
}
