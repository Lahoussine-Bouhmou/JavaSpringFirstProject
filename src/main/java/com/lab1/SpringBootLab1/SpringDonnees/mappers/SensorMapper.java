package com.lab1.SpringBootLab1.SpringDonnees.mappers;

import com.lab1.SpringBootLab1.SpringDonnees.models.SensorEntity;
import com.lab1.SpringBootLab1.SpringDonnees.records.Sensor;

public class SensorMapper {
    public static Sensor mapToSensor(SensorEntity sensorEntity) {
        return new Sensor(sensorEntity.getId(), sensorEntity.getName(), sensorEntity.getSensorValue(), sensorEntity.getSensorType());
    }
}
