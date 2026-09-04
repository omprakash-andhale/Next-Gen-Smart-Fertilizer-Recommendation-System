package com.smartfertilizer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "soil_readings")
public class SoilReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id")
    private String deviceId = "PICO-2W-001";

    @NotNull(message = "Nitrogen value is required")
    @Min(value = 0, message = "Nitrogen must be positive")
    @Max(value = 2000, message = "Nitrogen exceeds maximum sensor range")
    @Column(name = "nitrogen_mg_kg")
    private Double nitrogen; // mg/kg

    @NotNull(message = "Phosphorus value is required")
    @Min(value = 0, message = "Phosphorus must be positive")
    @Max(value = 2000, message = "Phosphorus exceeds maximum sensor range")
    @Column(name = "phosphorus_mg_kg")
    private Double phosphorus; // mg/kg

    @NotNull(message = "Potassium value is required")
    @Min(value = 0, message = "Potassium must be positive")
    @Max(value = 2000, message = "Potassium exceeds maximum sensor range")
    @Column(name = "potassium_mg_kg")
    private Double potassium; // mg/kg

    @NotNull(message = "pH value is required")
    @Min(value = 0, message = "pH cannot be negative")
    @Max(value = 14, message = "pH cannot exceed 14")
    @Column(name = "soil_ph")
    private Double ph;

    @NotNull(message = "Temperature is required")
    @Column(name = "temperature_c")
    private Double temperature; // °C

    @NotNull(message = "Humidity is required")
    @Min(value = 0, message = "Humidity cannot be negative")
    @Max(value = 100, message = "Humidity cannot exceed 100%")
    @Column(name = "humidity_pct")
    private Double humidity; // %

    @Column(name = "soil_moisture_pct")
    private Double soilMoisture = 45.0; // %

    @Column(name = "recorded_at")
    private LocalDateTime recordedAt;

    public SoilReading() {
        this.recordedAt = LocalDateTime.now();
    }

    public SoilReading(Double nitrogen, Double phosphorus, Double potassium, Double ph, Double temperature, Double humidity) {
        this.nitrogen = nitrogen;
        this.phosphorus = phosphorus;
        this.potassium = potassium;
        this.ph = ph;
        this.temperature = temperature;
        this.humidity = humidity;
        this.recordedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public Double getNitrogen() { return nitrogen; }
    public void setNitrogen(Double nitrogen) { this.nitrogen = nitrogen; }

    public Double getPhosphorus() { return phosphorus; }
    public void setPhosphorus(Double phosphorus) { this.phosphorus = phosphorus; }

    public Double getPotassium() { return potassium; }
    public void setPotassium(Double potassium) { this.potassium = potassium; }

    public Double getPh() { return ph; }
    public void setPh(Double ph) { this.ph = ph; }

    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }

    public Double getHumidity() { return humidity; }
    public void setHumidity(Double humidity) { this.humidity = humidity; }

    public Double getSoilMoisture() { return soilMoisture; }
    public void setSoilMoisture(Double soilMoisture) { this.soilMoisture = soilMoisture; }

    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }
}
