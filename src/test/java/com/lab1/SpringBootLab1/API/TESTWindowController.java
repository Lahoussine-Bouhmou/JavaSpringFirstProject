package com.lab1.SpringBootLab1.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab1.SpringBootLab1.SpringDonnees.API.WindowController;
import com.lab1.SpringBootLab1.SpringDonnees.API.WindowController.WindowCommand;
import com.lab1.SpringBootLab1.SpringDonnees.enums.SensorType;
import com.lab1.SpringBootLab1.SpringDonnees.models.RoomEntity;
import com.lab1.SpringBootLab1.SpringDonnees.models.SensorEntity;
import com.lab1.SpringBootLab1.SpringDonnees.models.WindowEntity;
import com.lab1.SpringBootLab1.SpringDonnees.repository.WindowDao;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static com.lab1.SpringBootLab1.FakeDataGenerator.FakeEntityBuilder.*;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(WindowController.class)
class TESTWindowController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WindowDao windowDao;

    @Test
    void shouldFindAll() throws Exception {
        // Prepare mock data
        RoomEntity room1 = createRoomEntity(100L, "Room 100");
        WindowEntity window1 = createWindowEntity(1L, "Window 1", room1);
        window1.setWindowStatus(createSensorEntity(1L, "Window 1 Status", SensorType.STATUS, 1.0));

        RoomEntity room2 = createRoomEntity(200L, "Room 200");
        WindowEntity window2 = createWindowEntity(2L, "Window 2", room2);
        window2.setWindowStatus(createSensorEntity(2L, "Window 2 Status", SensorType.STATUS, 0.0));

        Mockito.when(windowDao.findAll()).thenReturn(List.of(window1, window2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/windows")
                        .accept(MediaType.APPLICATION_JSON))
                // Check the HTTP response
                .andExpect(MockMvcResultMatchers.status().isOk())
                // Check the JSON response
                .andExpect(MockMvcResultMatchers.jsonPath("[*].name")
                        .value(Matchers.containsInAnyOrder("Window 1", "Window 2")));
    }

    @Test
    void shouldCreate() throws Exception {
        // Prepare input data
        RoomEntity room = createRoomEntity(100L, "Room 100");
        SensorEntity sensor = createSensorEntity(1L, "Status1", SensorType.STATUS, 1.0);

        WindowCommand windowCommand = new WindowCommand(
                "New Window",
                sensor,
                room
        );

        WindowEntity savedWindow = new WindowEntity(
                windowCommand.name(),
                windowCommand.windowStatus(),
                windowCommand.roomEntity()
        );
        savedWindow.setId(1L);

        Mockito.when(windowDao.save(any(WindowEntity.class))).thenReturn(savedWindow);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/windows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(windowCommand)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Window"));
    }

    @Test
    void shouldUpdate() throws Exception {
        Long windowId = 1L;

        RoomEntity room = createRoomEntity(100L, "Room 100");
        WindowEntity existingWindow = createWindowEntity(windowId, "Old Window", room);
        SensorEntity sensor = createSensorEntity(1L, "Status1", SensorType.STATUS, 0.0);

        WindowCommand windowCommand = new WindowCommand(
                "Updated Window",
                sensor,
                room
        );

        Mockito.when(windowDao.findById(windowId)).thenReturn(Optional.of(existingWindow));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/windows/{id}", windowId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(windowCommand)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(windowId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Window"));
    }

    @Test
    void shouldFindById() throws Exception {
        Long windowId = 1L;
        RoomEntity room = createRoomEntity(100L, "Room 100");
        WindowEntity window = createWindowEntity(windowId, "Window", room);
        window.setWindowStatus(createSensorEntity(1L, "Window Status", SensorType.STATUS, 1.0));

        Mockito.when(windowDao.findById(windowId)).thenReturn(Optional.of(window));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/windows/{id}", windowId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(windowId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Window"));
    }

    @Test
    void shouldDelete() throws Exception {
        Long windowId = 1L;

        Mockito.doNothing().when(windowDao).deleteById(windowId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/windows/{id}", windowId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
