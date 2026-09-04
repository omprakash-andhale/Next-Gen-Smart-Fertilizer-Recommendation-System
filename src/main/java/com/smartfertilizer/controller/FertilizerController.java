package com.smartfertilizer.controller;

import com.smartfertilizer.model.CropProfile;
import com.smartfertilizer.model.RecommendationResult;
import com.smartfertilizer.model.SoilReading;
import com.smartfertilizer.service.RecommendationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fertilizer")
@CrossOrigin(origins = "*")
public class FertilizerController {

    private final RecommendationService service;

    public FertilizerController(RecommendationService service) {
        this.service = service;
    }

    /**
     * Ingest real-time sensor packet from IoT device (Raspberry Pi Pico 2 W)
     */
    @PostMapping("/sensors/data")
    public ResponseEntity<Map<String, Object>> ingestSensorData(@Valid @RequestBody SoilReading reading) {
        SoilReading saved = service.saveReading(reading);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "status", "SUCCESS",
                "message", "Soil sensor data successfully received and logged.",
                "readingId", saved.getId(),
                "timestamp", saved.getRecordedAt()
        ));
    }

    /**
     * Get the most recent sensor reading
     */
    @GetMapping("/sensors/latest")
    public ResponseEntity<SoilReading> getLatestSensorData() {
        return service.getLatestReading()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    /**
     * Get historical sensor telemetry data (for graphing)
     */
    @GetMapping("/sensors/history")
    public ResponseEntity<List<SoilReading>> getHistoricalData() {
        return ResponseEntity.ok(service.getRecentReadings());
    }

    /**
     * Get all supported crops and their nutritional thresholds
     */
    @GetMapping("/crops")
    public ResponseEntity<List<CropProfile>> getAllCrops() {
        return ResponseEntity.ok(service.getAllCrops());
    }

    /**
     * Get fertilizer recommendation based on latest live sensor reading
     */
    @GetMapping("/recommend")
    public ResponseEntity<RecommendationResult> getRecommendationForLatest(
            @RequestParam(defaultValue = "Tomato") String crop) {
        SoilReading latest = service.getLatestReading()
                .orElseGet(() -> new SoilReading(125.0, 65.0, 140.0, 6.8, 29.0, 68.0));
        RecommendationResult recommendation = service.evaluate(latest, crop);
        return ResponseEntity.ok(recommendation);
    }

    /**
     * Evaluate custom soil reading input (manual testing / sandbox)
     */
    @PostMapping("/evaluate")
    public ResponseEntity<RecommendationResult> evaluateCustomReading(
            @Valid @RequestBody SoilReading reading,
            @RequestParam(defaultValue = "Tomato") String crop) {
        RecommendationResult recommendation = service.evaluate(reading, crop);
        return ResponseEntity.ok(recommendation);
    }
}
