package com.smartfertilizer.repository;

import com.smartfertilizer.model.SoilReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SoilReadingRepository extends JpaRepository<SoilReading, Long> {

    // Find latest reading
    Optional<SoilReading> findTopByOrderByRecordedAtDesc();

    // Find last N readings ordered by recorded time
    List<SoilReading> findTop20ByOrderByRecordedAtDesc();

    // Find readings by device
    List<SoilReading> findByDeviceIdOrderByRecordedAtDesc(String deviceId);
}
