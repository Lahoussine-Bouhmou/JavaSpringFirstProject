package com.lab1.SpringBootLab1.FakeDataGenerator;

import com.lab1.SpringBootLab1.SpringDonnees.enums.SensorType;
import com.lab1.SpringBootLab1.SpringDonnees.models.RoomEntity;
import com.lab1.SpringBootLab1.SpringDonnees.models.SensorEntity;
import com.lab1.SpringBootLab1.SpringDonnees.models.WindowEntity;

import java.util.List;

import static com.lab1.SpringBootLab1.SpringDonnees.enums.SensorType.TEMPERATURE;

public class FakeEntityBuilder {

    public static RoomEntity createRoomEntity(Long id, String name) {
        // Sensor is recreated before each test
        RoomEntity entity = new RoomEntity(
                name,
                createSensorEntity(1L, "Current Temperature", TEMPERATURE, 23.2),
                createSensorEntity(1L, "Target Temperature", TEMPERATURE, 18.2),
                1);

        entity.setId(id);
        entity.setWindows(List.of(
                createWindowEntity(id * 10 + 1L, "Window1" + name, entity),
                createWindowEntity(id * 10 + 2L, "Window2" + name, entity)
        ));
        return entity;
    }

    public static WindowEntity createWindowEntity(Long id, String name, RoomEntity roomEntity) {
        // Sensor is recreated before each test
        WindowEntity windowEntity = new WindowEntity(
                name,
                createSensorEntity(id * 10 + 1L, "Status" + id, SensorType.STATUS, 0.0),
                roomEntity
        );
        windowEntity.setId(id);
        return windowEntity;
    }

    public static SensorEntity createSensorEntity(Long id, String name, SensorType type, Double value) {
        // Sensor is recreated before each test
        SensorEntity sensorEntity = new SensorEntity(type, name);
        sensorEntity.setId(id);
        sensorEntity.setSensorValue(value);
        return sensorEntity;
    }
}