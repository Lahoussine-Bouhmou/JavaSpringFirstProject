package com.lab1.SpringBootLab1.SpringDonnees.records;

import com.lab1.SpringBootLab1.SpringDonnees.enums.SensorType;

public record Sensor(Long Id, String name, Double sensorValue, SensorType sensorType) {
}
