package com.smartfertilizer.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RecommendationResult {
    private String cropName;
    private SoilReading inputReading;
    private String primaryFertilizer;
    private String secondaryFertilizer;
    private String soilConditionSummary;
    private String acidityCondition;
    private String dosageRecommendation;
    private List<String> actionableAdvice = new ArrayList<>();
    private List<NutrientStatus> nutrientStatuses = new ArrayList<>();
    private LocalDateTime generatedAt;

    public static class NutrientStatus {
        private String parameter;
        private Double measuredValue;
        private Double optimalMin;
        private Double optimalMax;
        private String unit;
        private String status; // LOW, OPTIMAL, HIGH
        private String suggestion;

        public NutrientStatus(String parameter, Double measuredValue, Double optimalMin, Double optimalMax, String unit, String status, String suggestion) {
            this.parameter = parameter;
            this.measuredValue = measuredValue;
            this.optimalMin = optimalMin;
            this.optimalMax = optimalMax;
            this.unit = unit;
            this.status = status;
            this.suggestion = suggestion;
        }

        public String getParameter() { return parameter; }
        public Double getMeasuredValue() { return measuredValue; }
        public Double getOptimalMin() { return optimalMin; }
        public Double getOptimalMax() { return optimalMax; }
        public String getUnit() { return unit; }
        public String getStatus() { return status; }
        public String getSuggestion() { return suggestion; }
    }

    public RecommendationResult() {
        this.generatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getCropName() { return cropName; }
    public void setCropName(String cropName) { this.cropName = cropName; }

    public SoilReading getInputReading() { return inputReading; }
    public void setInputReading(SoilReading inputReading) { this.inputReading = inputReading; }

    public String getPrimaryFertilizer() { return primaryFertilizer; }
    public void setPrimaryFertilizer(String primaryFertilizer) { this.primaryFertilizer = primaryFertilizer; }

    public String getSecondaryFertilizer() { return secondaryFertilizer; }
    public void setSecondaryFertilizer(String secondaryFertilizer) { this.secondaryFertilizer = secondaryFertilizer; }

    public String getSoilConditionSummary() { return soilConditionSummary; }
    public void setSoilConditionSummary(String soilConditionSummary) { this.soilConditionSummary = soilConditionSummary; }

    public String getAcidityCondition() { return acidityCondition; }
    public void setAcidityCondition(String acidityCondition) { this.acidityCondition = acidityCondition; }

    public String getDosageRecommendation() { return dosageRecommendation; }
    public void setDosageRecommendation(String dosageRecommendation) { this.dosageRecommendation = dosageRecommendation; }

    public List<String> getActionableAdvice() { return actionableAdvice; }
    public void setActionableAdvice(List<String> actionableAdvice) { this.actionableAdvice = actionableAdvice; }

    public List<NutrientStatus> getNutrientStatuses() { return nutrientStatuses; }
    public void setNutrientStatuses(List<NutrientStatus> nutrientStatuses) { this.nutrientStatuses = nutrientStatuses; }

    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
}
