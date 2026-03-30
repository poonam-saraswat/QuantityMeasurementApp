package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;

import java.util.logging.Logger;
public class QuantityMeasurementController {
    private static final Logger logger = Logger.getLogger(
            QuantityMeasurementController.class.getName());

    private IQuantityMeasurementService quantityMeasurementService;

    public QuantityMeasurementController(IQuantityMeasurementService quantityMeasurementService) {
        this.quantityMeasurementService = quantityMeasurementService;
        logger.info("QuantityMeasurementController initialized.");
    }

    // Comparison
    public boolean performComparison(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        return quantityMeasurementService.compare(thisQuantityDTO, thatQuantityDTO);
    }

    // Conversion
    public QuantityDTO performConversion(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        return quantityMeasurementService.convert(thisQuantityDTO, thatQuantityDTO);
    }

    // Addition
    public QuantityDTO performAddition(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        return quantityMeasurementService.add(thisQuantityDTO, thatQuantityDTO);
    }

    public QuantityDTO performAddition(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) {
        return quantityMeasurementService.add(thisQuantityDTO, thatQuantityDTO, targetUnitDTO);
    }

    // Subtraction
    public QuantityDTO performSubtraction(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        return quantityMeasurementService.subtract(thisQuantityDTO, thatQuantityDTO);
    }

    public QuantityDTO performSubtraction(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) {
        return quantityMeasurementService.subtract(thisQuantityDTO, thatQuantityDTO, targetUnitDTO);
    }

    // Division
    public double performDivision(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        return quantityMeasurementService.divide(thisQuantityDTO, thatQuantityDTO);
    }

    // Main method for quick testing
    public static void main(String[] args) {
        System.out.println("QuantityMeasurementControllerUC-16");
    }
}
