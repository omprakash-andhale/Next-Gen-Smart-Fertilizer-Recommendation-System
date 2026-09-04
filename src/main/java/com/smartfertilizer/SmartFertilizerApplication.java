package com.smartfertilizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartFertilizerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartFertilizerApplication.class, args);
        System.out.println("===============================================================");
        System.out.println("🌾 Next Gen Smart Fertilizer Recommendation System is ACTIVE!");
        System.out.println("🚀 Web Dashboard: http://localhost:8080");
        System.out.println("📊 H2 Console:    http://localhost:8080/h2-console");
        System.out.println("📡 REST Endpoints: http://localhost:8080/api/fertilizer/...");
        System.out.println("===============================================================");
    }
}
