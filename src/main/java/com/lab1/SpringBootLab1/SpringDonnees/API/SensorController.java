package com.lab1.SpringBootLab1.SpringDonnees.API;

import com.lab1.SpringBootLab1.SpringDonnees.enums.SensorType;
import com.lab1.SpringBootLab1.SpringDonnees.mappers.SensorMapper;
import com.lab1.SpringBootLab1.SpringDonnees.models.SensorEntity;
import com.lab1.SpringBootLab1.SpringDonnees.records.Sensor;
import com.lab1.SpringBootLab1.SpringDonnees.repository.SensorDao;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/sensors")
@Transactional
public class SensorController {
    private final SensorDao sensorDao;

    public SensorController(SensorDao sensorDao) {
        this.sensorDao = sensorDao;
    }

    @GetMapping //
    public List<Sensor> findAll() {
        return sensorDao.findAll()
                .stream()
                .map(SensorMapper::mapToSensor)
                .sorted(Comparator.comparing((Sensor::name)))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public Sensor findById(@PathVariable Long id) {
        return sensorDao.findById(id).map(SensorMapper::mapToSensor).orElse(null); //
    }

    @PostMapping //
    public ResponseEntity<Sensor> create(@RequestBody SensorCommand sensor) { //
        SensorEntity entity = new SensorEntity(sensor.sensorType(), sensor.name());
        entity.setSensorValue(sensor.value());
        SensorEntity saved = sensorDao.save(entity);
        return ResponseEntity.ok(SensorMapper.mapToSensor(saved));
    }


    @PutMapping(path = "/{id}") //
    public ResponseEntity<Sensor> update(@PathVariable Long id, @RequestBody SensorCommand sensor) {
        SensorEntity entity = sensorDao.findById(id).orElse(null);
        if (entity == null) {
            return ResponseEntity.badRequest().build();
        }
        entity.setSensorValue(sensor.value());
        entity.setName(sensor.name());
        entity.setSensorType(sensor.sensorType());
        // (11)
        return ResponseEntity.ok(SensorMapper.mapToSensor(entity));
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        sensorDao.deleteById(id);
    }

    public record SensorCommand(SensorType sensorType, String name, Double value) {}
}