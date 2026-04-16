package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.entity.QuantityDTO;

import java.util.List;

public interface IQuantityMeasurementService {

    QuantityMeasurementDTO compare(QuantityDTO thisQ, QuantityDTO thatQ, String username);

    QuantityMeasurementDTO convert(QuantityDTO thisQ, String targetUnit, String username);

    QuantityMeasurementDTO add(QuantityDTO thisQ, QuantityDTO thatQ, String username);
    QuantityMeasurementDTO add(QuantityDTO thisQ, QuantityDTO thatQ, String targetUnit, String username);

    QuantityMeasurementDTO subtract(QuantityDTO thisQ, QuantityDTO thatQ, String username);
    QuantityMeasurementDTO subtract(QuantityDTO thisQ, QuantityDTO thatQ, String targetUnit, String username);

    QuantityMeasurementDTO divide(QuantityDTO thisQ, QuantityDTO thatQ, String username);

    /** All records for a specific user */
    List<QuantityMeasurementDTO> getAllMeasurements(String username);

    /** Records filtered by operation for a specific user */
    List<QuantityMeasurementDTO> getMeasurementsByOperation(String operation, String username);

    /** Records filtered by measurement type for a specific user */
    List<QuantityMeasurementDTO> getMeasurementsByType(String measurementType, String username);

    /** Error records for a specific user */
    List<QuantityMeasurementDTO> getErrorHistory(String username);

    /** Count of successful records for a given operation for a specific user */
    long getOperationCount(String operation, String username);
}
