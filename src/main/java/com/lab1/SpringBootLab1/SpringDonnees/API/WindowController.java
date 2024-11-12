package com.lab1.SpringBootLab1.SpringDonnees.API;

import com.lab1.SpringBootLab1.SpringDonnees.mappers.WindowMapper;
import com.lab1.SpringBootLab1.SpringDonnees.models.RoomEntity;
import com.lab1.SpringBootLab1.SpringDonnees.models.SensorEntity;
import com.lab1.SpringBootLab1.SpringDonnees.models.WindowEntity;
import com.lab1.SpringBootLab1.SpringDonnees.records.Window;
import com.lab1.SpringBootLab1.SpringDonnees.repository.WindowDao;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/windows")
@Transactional
public class WindowController {
    private final WindowDao windowDao;

    public WindowController(WindowDao windowDao) {
        this.windowDao = windowDao;
    }

    @GetMapping
    public List<Window> findALll() {
        return windowDao.findAll().stream().map((WindowMapper::mapToWindow)).collect(Collectors.toList());
    }

    @PostMapping //
    public ResponseEntity<Window> create(@RequestBody WindowCommand window) { //
        WindowEntity entity = new WindowEntity(window.name(), window.windowStatus(), window.roomEntity());
        WindowEntity saved = windowDao.save(entity);
        return ResponseEntity.ok(WindowMapper.mapToWindow(saved));
    }

    @PutMapping(path = "/{id}") //
    public ResponseEntity<Window> update(@PathVariable Long id, @RequestBody WindowCommand window) {
        WindowEntity entity = windowDao.findById(id).orElse(null);
        if (entity == null) {
            return ResponseEntity.badRequest().build();
        }
        entity.setWindowStatus(window.windowStatus());
        entity.setName(window.name());
        entity.setRoom(window.roomEntity());
        return ResponseEntity.ok(WindowMapper.mapToWindow(entity));
    }

    @GetMapping(path = "/{id}")
    public Window findById(@PathVariable Long id) {
        return windowDao.findById(id).map(WindowMapper::mapToWindow).orElse(null); //
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        windowDao.deleteById(id);
    }

    public record WindowCommand(String name, SensorEntity windowStatus, RoomEntity roomEntity) {}
}
