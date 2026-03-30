package com.app.quantitymeasurement.model;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS,
    FAHRENHEIT,
    KELVIN;

    /**
     * Temperature does NOT support arithmetic (add/subtract/divide).
     * Override the interface default to return false.
     */
    @Override
    public boolean supportArithmetic() {
        return false;
    }

    /**
     * Throw an UnsupportedOperationException for any arithmetic attempt.
     */
    @Override
    public void validOperationSupport(String operation) {
        throw new UnsupportedOperationException(
                this.name() + " does not support " + operation + " operations.");
    }

    @Override
    public double getConversionValue() {
        // Not a simple multiplicative factor — conversion handled in convertToBaseUnit
        return 1.0;
    }

    /** Convert this unit's value to Celsius (base unit). */
    @Override
    public double convertToBaseUnit(double value) {
        switch (this) {
            case FAHRENHEIT: return (value - 32.0) * 5.0 / 9.0;
            case KELVIN:     return value - 273.15;
            default:         return value; // CELSIUS
        }
    }

    /** Convert a Celsius (base) value back to this unit. */
    @Override
    public double convertFromBaseUnit(double baseValue) {
        switch (this) {
            case FAHRENHEIT: return (baseValue * 9.0 / 5.0) + 32.0;
            case KELVIN:     return baseValue + 273.15;
            default:         return baseValue; // CELSIUS
        }
    }

    /** Convert directly from this unit to a target unit. */
    public double convertTo(double value, TemperatureUnit targetUnit) {
        return targetUnit.convertFromBaseUnit(this.convertToBaseUnit(value));
    }

    @Override
    public String getUnitName() {
        return this.name();
    }

    @Override
    public String getMeasurementType() {
        return this.getClass().getSimpleName();
    }

    @Override
    public IMeasurable getUnitInstance(String unitName) {
        for (TemperatureUnit unit : TemperatureUnit.values()) {
            if (unit.getUnitName().equalsIgnoreCase(unitName)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Invalid temperature unit: " + unitName);
    }

    @Override
    public String toString() {
        return this.name();
    }

    public static void main(String[] args) {
        System.out.println("32F -> C = " + TemperatureUnit.FAHRENHEIT.convertTo(32, CELSIUS));
        System.out.println("0C  -> F = " + TemperatureUnit.CELSIUS.convertTo(0, FAHRENHEIT));
        System.out.println("0C  -> K = " + TemperatureUnit.CELSIUS.convertTo(0, KELVIN));
    }
}
