package com.smartfertilizer;

import com.smartfertilizer.model.RecommendationResult;
import com.smartfertilizer.model.SoilReading;
import com.smartfertilizer.service.RecommendationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SmartFertilizerApplicationTests {

    @Autowired
    private RecommendationService recommendationService;

    @Test
    void contextLoads() {
        assertNotNull(recommendationService);
    }

    @Test
    void testLowNitrogenGeneratesUreaRecommendation() {
        // Soil with low Nitrogen (50 mg/kg vs target 140-180 mg/kg for Tomato)
        SoilReading lowN = new SoilReading(50.0, 75.0, 180.0, 6.5, 26.0, 65.0);
        RecommendationResult result = recommendationService.evaluate(lowN, "Tomato");

        assertEquals("Tomato", result.getCropName());
        assertTrue(result.getPrimaryFertilizer().contains("Urea"));
        assertTrue(result.getActionableAdvice().stream().anyMatch(a -> a.contains("Nitrogen")));
    }

    @Test
    void testHighPhGeneratesSulfurRecommendation() {
        // Soil with alkaline pH 8.0 (Tomato optimal is 6.0 - 6.8)
        SoilReading alkaline = new SoilReading(150.0, 75.0, 180.0, 8.0, 26.0, 65.0);
        RecommendationResult result = recommendationService.evaluate(alkaline, "Tomato");

        assertTrue(result.getAcidityCondition().contains("Alkaline"));
        assertTrue(result.getActionableAdvice().stream().anyMatch(a -> a.contains("sulfur")));
    }

    @Test
    void testBalancedNutrientsGeneratesNPKComposite() {
        // Balanced Soil for Tomato
        SoilReading balanced = new SoilReading(160.0, 75.0, 190.0, 6.5, 25.0, 70.0);
        RecommendationResult result = recommendationService.evaluate(balanced, "Tomato");

        assertTrue(result.getPrimaryFertilizer().contains("NPK Composite"));
    }
}
