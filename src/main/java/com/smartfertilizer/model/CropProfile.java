package com.smartfertilizer.model;

import java.util.ArrayList;
import java.util.List;

public class CropProfile {
    private String cropName;
    private String category;
    private Double targetNitrogenMin;
    private Double targetNitrogenMax;
    private Double targetPhosphorusMin;
    private Double targetPhosphorusMax;
    private Double targetPotassiumMin;
    private Double targetPotassiumMax;
    private Double targetPhMin;
    private Double targetPhMax;
    private Double targetTempMin;
    private Double targetTempMax;
    private Double targetHumidityMin;
    private Double targetHumidityMax;
    private String optimalSoilType;
    private String icon;

    public CropProfile() {}

    public CropProfile(String cropName, String category, Double targetNitrogenMin, Double targetNitrogenMax,
                       Double targetPhosphorusMin, Double targetPhosphorusMax,
                       Double targetPotassiumMin, Double targetPotassiumMax,
                       Double targetPhMin, Double targetPhMax,
                       Double targetTempMin, Double targetTempMax,
                       Double targetHumidityMin, Double targetHumidityMax,
                       String optimalSoilType, String icon) {
        this.cropName = cropName;
        this.category = category;
        this.targetNitrogenMin = targetNitrogenMin;
        this.targetNitrogenMax = targetNitrogenMax;
        this.targetPhosphorusMin = targetPhosphorusMin;
        this.targetPhosphorusMax = targetPhosphorusMax;
        this.targetPotassiumMin = targetPotassiumMin;
        this.targetPotassiumMax = targetPotassiumMax;
        this.targetPhMin = targetPhMin;
        this.targetPhMax = targetPhMax;
        this.targetTempMin = targetTempMin;
        this.targetTempMax = targetTempMax;
        this.targetHumidityMin = targetHumidityMin;
        this.targetHumidityMax = targetHumidityMax;
        this.optimalSoilType = optimalSoilType;
        this.icon = icon;
    }

    public static List<CropProfile> getDefaultProfiles() {
        List<CropProfile> profiles = new ArrayList<>();
        // Tomato (as featured in Figure 5.3 of project report)
        profiles.add(new CropProfile("Tomato", "Vegetables", 140.0, 180.0, 60.0, 90.0, 160.0, 220.0, 6.0, 6.8, 20.0, 30.0, 60.0, 80.0, "Loamy / Sandy Loam", "🍅"));
        // Wheat
        profiles.add(new CropProfile("Wheat", "Cereals", 120.0, 160.0, 50.0, 75.0, 100.0, 150.0, 6.0, 7.5, 15.0, 25.0, 50.0, 70.0, "Clay Loam", "🌾"));
        // Rice / Paddy
        profiles.add(new CropProfile("Rice", "Cereals", 100.0, 140.0, 40.0, 60.0, 120.0, 160.0, 5.5, 6.5, 22.0, 34.0, 70.0, 90.0, "Clayey Loam", "🍚"));
        // Cotton
        profiles.add(new CropProfile("Cotton", "Cash Crop", 110.0, 150.0, 45.0, 70.0, 130.0, 180.0, 5.8, 7.5, 21.0, 32.0, 55.0, 75.0, "Black Soil", "🌱"));
        // Maize / Corn
        profiles.add(new CropProfile("Maize", "Cereals", 130.0, 170.0, 55.0, 80.0, 110.0, 150.0, 5.8, 7.0, 18.0, 30.0, 55.0, 75.0, "Well-drained Loam", "🌽"));
        // Sugarcane
        profiles.add(new CropProfile("Sugarcane", "Cash Crop", 150.0, 200.0, 60.0, 90.0, 150.0, 200.0, 6.5, 7.5, 20.0, 35.0, 60.0, 85.0, "Deep Loam", "🎋"));
        return profiles;
    }

    // Getters and Setters
    public String getCropName() { return cropName; }
    public void setCropName(String cropName) { this.cropName = cropName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Double getTargetNitrogenMin() { return targetNitrogenMin; }
    public void setTargetNitrogenMin(Double targetNitrogenMin) { this.targetNitrogenMin = targetNitrogenMin; }

    public Double getTargetNitrogenMax() { return targetNitrogenMax; }
    public void setTargetNitrogenMax(Double targetNitrogenMax) { this.targetNitrogenMax = targetNitrogenMax; }

    public Double getTargetPhosphorusMin() { return targetPhosphorusMin; }
    public void setTargetPhosphorusMin(Double targetPhosphorusMin) { this.targetPhosphorusMin = targetPhosphorusMin; }

    public Double getTargetPhosphorusMax() { return targetPhosphorusMax; }
    public void setTargetPhosphorusMax(Double targetPhosphorusMax) { this.targetPhosphorusMax = targetPhosphorusMax; }

    public Double getTargetPotassiumMin() { return targetPotassiumMin; }
    public void setTargetPotassiumMin(Double targetPotassiumMin) { this.targetPotassiumMin = targetPotassiumMin; }

    public Double getTargetPotassiumMax() { return targetPotassiumMax; }
    public void setTargetPotassiumMax(Double targetPotassiumMax) { this.targetPotassiumMax = targetPotassiumMax; }

    public Double getTargetPhMin() { return targetPhMin; }
    public void setTargetPhMin(Double targetPhMin) { this.targetPhMin = targetPhMin; }

    public Double getTargetPhMax() { return targetPhMax; }
    public void setTargetPhMax(Double targetPhMax) { this.targetPhMax = targetPhMax; }

    public Double getTargetTempMin() { return targetTempMin; }
    public void setTargetTempMin(Double targetTempMin) { this.targetTempMin = targetTempMin; }

    public Double getTargetTempMax() { return targetTempMax; }
    public void setTargetTempMax(Double targetTempMax) { this.targetTempMax = targetTempMax; }

    public Double getTargetHumidityMin() { return targetHumidityMin; }
    public void setTargetHumidityMin(Double targetHumidityMin) { this.targetHumidityMin = targetHumidityMin; }

    public Double getTargetHumidityMax() { return targetHumidityMax; }
    public void setTargetHumidityMax(Double targetHumidityMax) { this.targetHumidityMax = targetHumidityMax; }

    public String getOptimalSoilType() { return optimalSoilType; }
    public void setOptimalSoilType(String optimalSoilType) { this.optimalSoilType = optimalSoilType; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
}
