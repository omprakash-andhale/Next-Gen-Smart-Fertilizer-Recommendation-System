package com.smartfertilizer.service;

import com.smartfertilizer.model.CropProfile;
import com.smartfertilizer.model.RecommendationResult;
import com.smartfertilizer.model.SoilReading;
import com.smartfertilizer.repository.SoilReadingRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationService {

    private final SoilReadingRepository repository;
    private final Map<String, CropProfile> cropDatabase = new HashMap<>();

    public RecommendationService(SoilReadingRepository repository) {
        this.repository = repository;
        for (CropProfile profile : CropProfile.getDefaultProfiles()) {
            cropDatabase.put(profile.getCropName().toLowerCase(), profile);
        }
        initSampleData();
    }

    private void initSampleData() {
        // Populate sample data matching Chapter 8, Table 8.1
        if (repository.count() == 0) {
            // Sample 1: Baseline from report
            repository.save(new SoilReading(125.0, 65.0, 140.0, 6.8, 29.0, 68.0));
            // Historical timeline data for charts (Figure 5.2)
            repository.save(new SoilReading(110.0, 58.0, 135.0, 6.5, 27.5, 62.0));
            repository.save(new SoilReading(118.0, 62.0, 142.0, 6.9, 28.0, 65.0));
            repository.save(new SoilReading(132.0, 70.0, 155.0, 7.1, 29.5, 67.0));
            repository.save(new SoilReading(125.0, 65.0, 140.0, 6.8, 29.0, 68.0));
        }
    }

    public List<CropProfile> getAllCrops() {
        return new ArrayList<>(cropDatabase.values());
    }

    public CropProfile getCropProfile(String cropName) {
        CropProfile profile = cropDatabase.get(cropName.toLowerCase());
        if (profile == null) {
            // Default to Tomato if not found
            return cropDatabase.get("tomato");
        }
        return profile;
    }

    public SoilReading saveReading(SoilReading reading) {
        return repository.save(reading);
    }

    public List<SoilReading> getRecentReadings() {
        return repository.findTop20ByOrderByRecordedAtDesc();
    }

    public Optional<SoilReading> getLatestReading() {
        return repository.findTopByOrderByRecordedAtDesc();
    }

    public RecommendationResult evaluate(SoilReading reading, String targetCropName) {
        CropProfile crop = getCropProfile(targetCropName);
        RecommendationResult result = new RecommendationResult();
        result.setCropName(crop.getCropName());
        result.setInputReading(reading);

        List<String> advice = new ArrayList<>();
        List<RecommendationResult.NutrientStatus> statuses = new ArrayList<>();
        List<String> requiredFertilizers = new ArrayList<>();

        // 1. Evaluate Nitrogen (N)
        double n = reading.getNitrogen();
        if (n < crop.getTargetNitrogenMin()) {
            double deficit = crop.getTargetNitrogenMin() - n;
            statuses.add(new RecommendationResult.NutrientStatus("Nitrogen (N)", n, crop.getTargetNitrogenMin(), crop.getTargetNitrogenMax(), "mg/kg", "LOW", "Deficit of " + String.format("%.1f", deficit) + " mg/kg"));
            requiredFertilizers.add("Urea (46% N)");
            advice.add("Apply Nitrogen-rich fertilizer (e.g. Urea at " + Math.round(deficit * 0.8) + " kg/acre) in split doses.");
        } else if (n > crop.getTargetNitrogenMax()) {
            statuses.add(new RecommendationResult.NutrientStatus("Nitrogen (N)", n, crop.getTargetNitrogenMin(), crop.getTargetNitrogenMax(), "mg/kg", "HIGH", "Excess Nitrogen detected"));
            advice.add("Hold nitrogenous fertilizers to prevent vegetative overgrowth and pest susceptibility.");
        } else {
            statuses.add(new RecommendationResult.NutrientStatus("Nitrogen (N)", n, crop.getTargetNitrogenMin(), crop.getTargetNitrogenMax(), "mg/kg", "OPTIMAL", "Optimal range"));
        }

        // 2. Evaluate Phosphorus (P)
        double p = reading.getPhosphorus();
        if (p < crop.getTargetPhosphorusMin()) {
            double deficit = crop.getTargetPhosphorusMin() - p;
            statuses.add(new RecommendationResult.NutrientStatus("Phosphorus (P)", p, crop.getTargetPhosphorusMin(), crop.getTargetPhosphorusMax(), "mg/kg", "LOW", "Deficit of " + String.format("%.1f", deficit) + " mg/kg"));
            requiredFertilizers.add("DAP (Diammonium Phosphate) / SSP");
            advice.add("Apply Diammonium Phosphate (DAP at " + Math.round(deficit * 0.6) + " kg/acre) during root development.");
        } else if (p > crop.getTargetPhosphorusMax()) {
            statuses.add(new RecommendationResult.NutrientStatus("Phosphorus (P)", p, crop.getTargetPhosphorusMin(), crop.getTargetPhosphorusMax(), "mg/kg", "HIGH", "Excess Phosphorus detected"));
            advice.add("Phosphorus is abundant. Avoid further phosphate fertilizers to prevent zinc/iron lockout.");
        } else {
            statuses.add(new RecommendationResult.NutrientStatus("Phosphorus (P)", p, crop.getTargetPhosphorusMin(), crop.getTargetPhosphorusMax(), "mg/kg", "OPTIMAL", "Optimal range"));
        }

        // 3. Evaluate Potassium (K)
        double k = reading.getPotassium();
        if (k < crop.getTargetPotassiumMin()) {
            double deficit = crop.getTargetPotassiumMin() - k;
            statuses.add(new RecommendationResult.NutrientStatus("Potassium (K)", k, crop.getTargetPotassiumMin(), crop.getTargetPotassiumMax(), "mg/kg", "LOW", "Deficit of " + String.format("%.1f", deficit) + " mg/kg"));
            requiredFertilizers.add("MOP (Muriate of Potash)");
            advice.add("Apply Muriate of Potash (MOP at " + Math.round(deficit * 0.5) + " kg/acre) to improve drought tolerance and fruit quality.");
        } else if (k > crop.getTargetPotassiumMax()) {
            statuses.add(new RecommendationResult.NutrientStatus("Potassium (K)", k, crop.getTargetPotassiumMin(), crop.getTargetPotassiumMax(), "mg/kg", "HIGH", "Excess Potassium detected"));
            advice.add("Potassium level is elevated. Monitor magnesium uptake.");
        } else {
            statuses.add(new RecommendationResult.NutrientStatus("Potassium (K)", k, crop.getTargetPotassiumMin(), crop.getTargetPotassiumMax(), "mg/kg", "OPTIMAL", "Optimal range"));
        }

        // 4. Evaluate pH
        double ph = reading.getPh();
        if (ph < crop.getTargetPhMin()) {
            result.setAcidityCondition("Soil is Acidic (pH " + ph + " < " + crop.getTargetPhMin() + ")");
            advice.add("Apply agricultural lime (calcium carbonate) or dolomite to raise soil pH.");
            statuses.add(new RecommendationResult.NutrientStatus("Soil pH", ph, crop.getTargetPhMin(), crop.getTargetPhMax(), "pH", "LOW (ACIDIC)", "Apply lime amendment"));
        } else if (ph > crop.getTargetPhMax()) {
            result.setAcidityCondition("Soil is Alkaline (pH " + ph + " > " + crop.getTargetPhMax() + ")");
            advice.add("Soil too alkaline. Add elemental sulfur or gypsum to lower pH and enhance nutrient availability.");
            statuses.add(new RecommendationResult.NutrientStatus("Soil pH", ph, crop.getTargetPhMin(), crop.getTargetPhMax(), "pH", "HIGH (ALKALINE)", "Add sulfur amendment"));
        } else {
            result.setAcidityCondition("Soil pH is Optimal (" + ph + ")");
            statuses.add(new RecommendationResult.NutrientStatus("Soil pH", ph, crop.getTargetPhMin(), crop.getTargetPhMax(), "pH", "OPTIMAL", "Ideal for nutrient absorption"));
        }

        // 5. Environmental conditions
        if (reading.getTemperature() > crop.getTargetTempMax()) {
            advice.add("High ambient temperature detected (" + reading.getTemperature() + "°C). Maintain adequate irrigation scheduling.");
        }
        if (reading.getHumidity() < crop.getTargetHumidityMin()) {
            advice.add("Low humidity (" + reading.getHumidity() + "%). Ensure soil moisture retention.");
        }

        // Determine Primary and Secondary Recommendations
        if (requiredFertilizers.isEmpty()) {
            result.setPrimaryFertilizer("NPK Composite Fertilizer (Balanced 19:19:19)");
            result.setSecondaryFertilizer("Organic Compost / Vermicompost for maintenance");
            result.setSoilConditionSummary("Balanced Nutrient Condition. Maintain soil fertility.");
            result.setDosageRecommendation("50 kg/acre maintenance application.");
        } else {
            result.setPrimaryFertilizer(requiredFertilizers.get(0));
            if (requiredFertilizers.size() > 1) {
                result.setSecondaryFertilizer(requiredFertilizers.get(1));
            } else {
                result.setSecondaryFertilizer("NPK 10:26:26 or Bio-fertilizer booster");
            }
            result.setSoilConditionSummary("Nutrient Deficiencies Detected: " + String.join(", ", requiredFertilizers));
            result.setDosageRecommendation("Custom split application based on deficit ratios.");
        }

        result.setActionableAdvice(advice);
        result.setNutrientStatuses(statuses);
        return result;
    }
}
