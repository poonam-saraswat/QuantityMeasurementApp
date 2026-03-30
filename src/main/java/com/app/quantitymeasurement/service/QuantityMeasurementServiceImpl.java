package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.core.Quantity;
import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.entity.QuantityModel;
import com.app.quantitymeasurement.exeption.QuantityMeasurementException;
import com.app.quantitymeasurement.model.*;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;

import java.util.function.DoubleBinaryOperator;
import java.util.logging.Logger;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private static final Logger logger =
            Logger.getLogger(QuantityMeasurementServiceImpl.class.getName());

    private IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
        logger.info("QuantityMeasurementServiceImpl initialized with: "
                + repository.getClass().getSimpleName());
    }

    @Override
    public boolean compare(QuantityDTO thisQ, QuantityDTO thatQ) {

        QuantityModel<IMeasurable> m1 = getQuantityModel(thisQ);
        QuantityModel<IMeasurable> m2 = getQuantityModel(thatQ);

        Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());
        Quantity<IMeasurable> q2 = new Quantity<>(m2.getValue(), m2.getUnit());

        boolean result = q1.equals(q2);

        String res = result ? "Equal" : "Not Equal";
        repository.save(new QuantityMeasurementEntity(m1, m2, "COMPARE", res));

        return result;
    }

    @Override
    public QuantityDTO convert(QuantityDTO thisQ, QuantityDTO thatQ) {

        QuantityModel<IMeasurable> m1 = getQuantityModel(thisQ);
        QuantityModel<IMeasurable> m2 = getQuantityModel(thatQ);

        double value;

        if (m1.getUnit() instanceof TemperatureUnit) {
            double base = m1.getUnit().convertToBaseUnit(m1.getValue());
            value = m2.getUnit().convertFromBaseUnit(base);
        } else {
            Quantity<IMeasurable> quantity =
                    new Quantity<>(m1.getValue(), m1.getUnit());

            value = quantity.convertTo(m2.getUnit()).getValue();
        }

        repository.save(new QuantityMeasurementEntity(
                m1, m2, "CONVERT",
                new QuantityModel<>(value, m2.getUnit())
        ));

        return new QuantityDTO(value, thatQ.getUnit(), thatQ.getMeasurementType());
    }

    @Override
    public QuantityDTO add(QuantityDTO a, QuantityDTO b) {
        return add(a, b, a);
    }

    @Override
    public QuantityDTO add(QuantityDTO thisQ, QuantityDTO thatQ, QuantityDTO targetQ) {
        return doArithmetic(thisQ, thatQ, targetQ, "ADD", (x, y) -> x + y);
    }

    @Override
    public QuantityDTO subtract(QuantityDTO a, QuantityDTO b) {
        return subtract(a, b, a);
    }

    @Override
    public QuantityDTO subtract(QuantityDTO thisQ, QuantityDTO thatQ, QuantityDTO targetQ) {
        return doArithmetic(thisQ, thatQ, targetQ, "SUBTRACT", (x, y) -> x - y);
    }

    @Override
    public double divide(QuantityDTO thisQ, QuantityDTO thatQ) {

        QuantityModel<IMeasurable> m1 = getQuantityModel(thisQ);
        QuantityModel<IMeasurable> m2 = getQuantityModel(thatQ);

        validate(m1, m2, null, false);

        double value1 = m1.getUnit().convertToBaseUnit(m1.getValue());
        double value2 = m2.getUnit().convertToBaseUnit(m2.getValue());

        double result = value1 / value2;

        repository.save(new QuantityMeasurementEntity(m1, m2, "DIVIDE", result));

        return result;
    }

    private QuantityDTO doArithmetic(
            QuantityDTO thisQ,
            QuantityDTO thatQ,
            QuantityDTO targetQ,
            String operation,
            DoubleBinaryOperator function) {

        QuantityModel<IMeasurable> m1 = getQuantityModel(thisQ);
        QuantityModel<IMeasurable> m2 = getQuantityModel(thatQ);
        QuantityModel<IMeasurable> mt = getQuantityModel(targetQ);

        validate(m1, m2, mt, true);

        double value1 = m1.getUnit().convertToBaseUnit(m1.getValue());
        double value2 = m2.getUnit().convertToBaseUnit(m2.getValue());

        double baseResult = function.applyAsDouble(value1, value2);

        double finalValue = mt.getUnit().convertFromBaseUnit(baseResult);

        repository.save(new QuantityMeasurementEntity(
                m1, m2, operation,
                new QuantityModel<>(finalValue, mt.getUnit())
        ));

        return new QuantityDTO(finalValue, targetQ.getUnit(), targetQ.getMeasurementType());
    }

    private QuantityModel<IMeasurable> getQuantityModel(QuantityDTO q) {

        if (q == null)
            throw new QuantityMeasurementException("Quantity cannot be null");

        IMeasurable unit;

        switch (q.getMeasurementType()) {

            case "LengthUnit":
                unit = LengthUnit.FEET.getUnitInstance(q.getUnit());
                break;

            case "WeightUnit":
                unit = WeightUnit.GRAM.getUnitInstance(q.getUnit());
                break;

            case "VolumeUnit":
                unit = VolumeUnit.LITRE.getUnitInstance(q.getUnit());
                break;

            case "TemperatureUnit":
                unit = TemperatureUnit.CELSIUS.getUnitInstance(q.getUnit());
                break;

            default:
                throw new QuantityMeasurementException(
                        "Unsupported type: " + q.getMeasurementType());
        }

        return new QuantityModel<>(q.getValue(), unit);
    }

    private <U extends IMeasurable> void validate(
            QuantityModel<U> a,
            QuantityModel<U> b,
            QuantityModel<U> t,
            boolean needTarget) {

        if (a == null || b == null)
            throw new QuantityMeasurementException("Operands cannot be null");

        if (!a.getUnit().getClass().equals(b.getUnit().getClass()))
            throw new QuantityMeasurementException("Cross-category operation not allowed");

        if (needTarget) {

            if (t == null)
                throw new QuantityMeasurementException("Target unit cannot be null");

            if (!a.getUnit().getClass().equals(t.getUnit().getClass()))
                throw new QuantityMeasurementException("Invalid target unit category");
        }

        a.getUnit().validOperationSupport("arithmetic");
    }

    public static void main(String[] args) {
        System.out.println("QuantityMeasurementServiceImpl - UC16");
    }
}