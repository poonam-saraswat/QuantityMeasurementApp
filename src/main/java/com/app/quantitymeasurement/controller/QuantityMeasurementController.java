package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.dto.request.ArithmeticRequestDTO;
import com.app.quantitymeasurement.dto.request.CompareRequestDTO;
import com.app.quantitymeasurement.dto.request.ConvertRequestDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller.
 * FIX: All endpoints now extract the authenticated username from the JWT
 * (via SecurityContextHolder) and pass it to the service so that history
 * is always scoped to the logged-in user only.
 */
@RestController
@RequestMapping("/api/measurements")
@Tag(name = "Quantity Measurements", description = "REST API for quantity measurement operations")
@CrossOrigin(origins = "*")
public class QuantityMeasurementController {

    private static final Logger logger =
            LoggerFactory.getLogger(QuantityMeasurementController.class);

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    /** Extracts the authenticated username from the JWT via Spring Security context. */
    private String currentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.isAuthenticated()) ? auth.getName() : "anonymous";
    }

    // ── POST operations ──────────────────────────────────────────

    @PostMapping("/compare")
    @Operation(summary = "Compare two quantities",
            description = "Returns true/false in the resultString field.")
    public ResponseEntity<QuantityMeasurementDTO> compare(
            @Valid @RequestBody CompareRequestDTO request) {
        logger.info("POST /compare");
        return ResponseEntity.ok(
                service.compare(request.getThisQuantity(), request.getThatQuantity(), currentUsername()));
    }

    @PostMapping("/convert")
    @Operation(summary = "Convert a quantity to a different unit")
    public ResponseEntity<QuantityMeasurementDTO> convert(
            @Valid @RequestBody ConvertRequestDTO request) {
        logger.info("POST /convert");
        return ResponseEntity.ok(
                service.convert(request.getThisQuantity(), request.getTargetUnit(), currentUsername()));
    }

    @PostMapping("/add")
    @Operation(summary = "Add two quantities")
    public ResponseEntity<QuantityMeasurementDTO> add(
            @Valid @RequestBody ArithmeticRequestDTO request) {
        logger.info("POST /add");
        QuantityMeasurementDTO result = request.getTargetUnit() != null
                ? service.add(request.getThisQuantity(), request.getThatQuantity(), request.getTargetUnit(), currentUsername())
                : service.add(request.getThisQuantity(), request.getThatQuantity(), currentUsername());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/subtract")
    @Operation(summary = "Subtract two quantities")
    public ResponseEntity<QuantityMeasurementDTO> subtract(
            @Valid @RequestBody ArithmeticRequestDTO request) {
        logger.info("POST /subtract");
        QuantityMeasurementDTO result = request.getTargetUnit() != null
                ? service.subtract(request.getThisQuantity(), request.getThatQuantity(), request.getTargetUnit(), currentUsername())
                : service.subtract(request.getThisQuantity(), request.getThatQuantity(), currentUsername());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/divide")
    @Operation(summary = "Divide two quantities")
    public ResponseEntity<QuantityMeasurementDTO> divide(
            @Valid @RequestBody ArithmeticRequestDTO request) {
        logger.info("POST /divide");
        return ResponseEntity.ok(
                service.divide(request.getThisQuantity(), request.getThatQuantity(), currentUsername()));
    }

    // ── GET history (all scoped to logged-in user) ────────────────

    @GetMapping("/history")
    @Operation(summary = "Get all measurement history for the logged-in user")
    public ResponseEntity<List<QuantityMeasurementDTO>> getHistory() {
        return ResponseEntity.ok(service.getAllMeasurements(currentUsername()));
    }

    @GetMapping("/history/{operation}")
    @Operation(summary = "Get history by operation type for the logged-in user")
    public ResponseEntity<List<QuantityMeasurementDTO>> getByOperation(
            @Parameter(description = "Operation type e.g. COMPARE, ADD")
            @PathVariable String operation) {
        return ResponseEntity.ok(service.getMeasurementsByOperation(operation.toUpperCase(), currentUsername()));
    }

    @GetMapping("/history/type/{measurementType}")
    @Operation(summary = "Get history by measurement type for the logged-in user")
    public ResponseEntity<List<QuantityMeasurementDTO>> getByType(
            @Parameter(description = "Measurement type e.g. LengthUnit, WeightUnit")
            @PathVariable String measurementType) {
        return ResponseEntity.ok(service.getMeasurementsByType(measurementType, currentUsername()));
    }

    @GetMapping("/history/errored")
    @Operation(summary = "Get all error records for the logged-in user")
    public ResponseEntity<List<QuantityMeasurementDTO>> getErrorHistory() {
        return ResponseEntity.ok(service.getErrorHistory(currentUsername()));
    }

    @GetMapping("/count/{operation}")
    @Operation(summary = "Count successful operations by type for the logged-in user")
    public ResponseEntity<Map<String, Object>> getOperationCount(
            @Parameter(description = "Operation type e.g. COMPARE, ADD")
            @PathVariable String operation) {
        long count = service.getOperationCount(operation, currentUsername());
        return ResponseEntity.ok(Map.of(
                "operation", operation.toUpperCase(),
                "count",     count));
    }
}
