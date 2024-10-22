package com.lab1.SpringBootLab1.SpringDonnees.API;

import com.lab1.SpringBootLab1.SpringDonnees.mappers.RoomMapper;
import com.lab1.SpringBootLab1.SpringDonnees.models.RoomEntity;
import com.lab1.SpringBootLab1.SpringDonnees.models.SensorEntity;
import com.lab1.SpringBootLab1.SpringDonnees.records.Room;
import com.lab1.SpringBootLab1.SpringDonnees.repository.RoomDao;
import com.lab1.SpringBootLab1.SpringDonnees.repository.WindowDao;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/rooms")
@Transactional
public class RoomController {
    private final WindowDao windowDao;
    private final RoomDao roomDao;
    public RoomController(RoomDao roomDao, WindowDao windowDao) {
        this.windowDao = windowDao;
        this.roomDao = roomDao;
    }
    @GetMapping
    public List<Room> findALll() {
        return roomDao.findAll().stream().map((RoomMapper::mapToRoom)).collect(Collectors.toList());
    }
    @PostMapping
    public ResponseEntity<Room> create(@RequestBody RoomCommand room) { //
        RoomEntity entity = new RoomEntity(room.name(), room.currentTemperature(), room.targetTemperature(), room.floor());
        RoomEntity saved = roomDao.save(entity);
        return ResponseEntity.ok(RoomMapper.mapToRoom(saved));
    }
    @PutMapping(path = "/{id}") //
    public ResponseEntity<Room> update(@PathVariable Long id, @RequestBody RoomCommand room) {
        RoomEntity entity = roomDao.findById(id).orElse(null);
        if (entity == null) {
            return ResponseEntity.badRequest().build();
        }
        entity.setCurrentTemperature(room.currentTemperature());
        entity.setName(room.name());
        entity.setFloor(room.floor());
        return ResponseEntity.ok(RoomMapper.mapToRoom(entity));
    }
    @GetMapping(path = "/{id}")
    public Room findById(@PathVariable Long id) {
        return roomDao.findById(id).map(RoomMapper::mapToRoom).orElse(null);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        windowDao.deleteWindowByRoomId(id);
        roomDao.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping(path = "/{id}/openWindows")
    public ResponseEntity<Room> openWindows(@PathVariable Long id) {
        RoomEntity entity = roomDao.findById(id).orElseThrow(() -> new NoSuchElementException("Room not found"));
        if (entity == null) {
            return ResponseEntity.badRequest().build();
        }
        entity.getWindows().forEach((windowEntity -> {
            SensorEntity windowStatus = windowEntity.getWindowStatus();
            windowStatus.setSensorValue(1.0);
            windowEntity.setWindowStatus(windowStatus);
        }));
        roomDao.save(entity);
        return ResponseEntity.ok(RoomMapper.mapToRoom(entity));
    }
    @PutMapping(path = "/{id}/closeWindows")
    public ResponseEntity<Room> closeWindows(@PathVariable Long id) {
        RoomEntity entity = roomDao.findById(id).orElse(null);
        if (entity == null) {
            return ResponseEntity.badRequest().build();
        }
        entity.getWindows().forEach((windowEntity -> {
            SensorEntity windowStatus = windowEntity.getWindowStatus();
            windowStatus.setSensorValue(0.0);
            windowEntity.setWindowStatus(windowStatus);
        }));
        roomDao.save(entity);
        return ResponseEntity.ok(RoomMapper.mapToRoom(entity));
    }
    public record RoomCommand(String name, SensorEntity currentTemperature, SensorEntity targetTemperature, int floor) {}
}
