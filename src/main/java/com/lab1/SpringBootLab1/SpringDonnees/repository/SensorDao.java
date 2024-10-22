package com.lab1.SpringBootLab1.SpringDonnees.repository;

import com.lab1.SpringBootLab1.SpringDonnees.models.SensorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorDao extends JpaRepository<SensorEntity, Long> {
}
