package com.lab1.SpringBootLab1.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab1.SpringBootLab1.SpringDonnees.API.RoomController;
import com.lab1.SpringBootLab1.SpringDonnees.API.RoomController.RoomCommand;
import com.lab1.SpringBootLab1.SpringDonnees.enums.SensorType;
import com.lab1.SpringBootLab1.SpringDonnees.models.RoomEntity;
import com.lab1.SpringBootLab1.SpringDonnees.models.SensorEntity;
import com.lab1.SpringBootLab1.SpringDonnees.models.WindowEntity;
import com.lab1.SpringBootLab1.SpringDonnees.repository.RoomDao;
import com.lab1.SpringBootLab1.SpringDonnees.repository.WindowDao;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoomController.class)
class TESTRoomController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoomDao roomDao;

    @MockBean
    private WindowDao windowDao;

    @Test
    void shouldFindAll() throws Exception {
        SensorEntity currTemp1 = createSensorEntity(1L, "TempSensor1", SensorType.TEMPERATURE, 20.0);
        SensorEntity tarTemp1 = createSensorEntity(1L, "TempSensor1", SensorType.TEMPERATURE, 25.0);
        SensorEntity currTemp2 = createSensorEntity(2L, "TempSensor2", SensorType.TEMPERATURE, 22.0);
        SensorEntity tarTemp2 = createSensorEntity(2L, "TempSensor2", SensorType.TEMPERATURE, 27.0);

        RoomEntity room1 = createRoomEntity(1L, "Room 1");
        RoomEntity room2 = createRoomEntity(2L, "Room 2");
        room1.setCurrentTemperature(currTemp1);
        room1.setTargetTemperature(tarTemp1);
        room2.setCurrentTemperature(currTemp2);
        room2.setTargetTemperature(tarTemp2);

        when(roomDao.findAll()).thenReturn(List.of(room1, room2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/rooms")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[*].name")
                        .value(Matchers.containsInAnyOrder("Room 1", "Room 2")));
    }

    @Test
    void shouldCreate() throws Exception {
        SensorEntity currTemp = createSensorEntity(1L, "TempSensor", SensorType.TEMPERATURE, 21.0);
        SensorEntity tarTemp = createSensorEntity(1L, "TempSensor", SensorType.TEMPERATURE, 22.0);

        RoomCommand roomCommand = new RoomCommand(
                "New Room",
                currTemp,
                tarTemp,
                1
        );

        RoomEntity savedRoom = new RoomEntity(
                roomCommand.name(),
                roomCommand.currentTemperature(),
                roomCommand.targetTemperature(),
                roomCommand.floor()
        );
        SensorEntity windowSensor = createSensorEntity(2L, "WindowSensor", SensorType.STATUS, 0.0);
        WindowEntity window = createWindowEntity(1L, "Window", savedRoom);
        window.setWindowStatus(windowSensor);
        savedRoom.setId(1L);
        savedRoom.setWindows(List.of(window));
        when(roomDao.save(any(RoomEntity.class))).thenReturn(savedRoom);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomCommand)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Room"));
    }
    @Test
    void shouldUpdate() throws Exception {
        Long roomId = 1L;
        SensorEntity currTemp = createSensorEntity(1L, "TempSensor", SensorType.TEMPERATURE, 20.0);
        SensorEntity tarTemp = createSensorEntity(1L, "TempSensor", SensorType.TEMPERATURE, 25.0);
        RoomEntity existingRoom = createRoomEntity(roomId, "Old Room");
        existingRoom.setCurrentTemperature(currTemp);
        existingRoom.setTargetTemperature(tarTemp);
        SensorEntity newCurrTemp = createSensorEntity(2L, "NewTempSensor", SensorType.TEMPERATURE, 22.0);
        SensorEntity newTarTemp = createSensorEntity(2L, "NewTempSensor", SensorType.TEMPERATURE, 19.0);
        RoomCommand roomCommand = new RoomCommand(
                "Updated Room",
                newCurrTemp,
                newTarTemp,
                2
        );
        when(roomDao.findById(roomId)).thenReturn(Optional.of(existingRoom));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/rooms/{id}", roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomCommand)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Id").value(roomId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Room"));
    }
    @Test
    void shouldFindById() throws Exception {
        Long roomId = 1L;
        SensorEntity currTemp = createSensorEntity(1L, "TempSensor", SensorType.TEMPERATURE, 20.0);
        SensorEntity tarTemp = createSensorEntity(1L, "TempSensor", SensorType.TEMPERATURE, 25.0);
        RoomEntity room = createRoomEntity(roomId, "Room");
        room.setCurrentTemperature(currTemp);
        room.setTargetTemperature(tarTemp);

        when(roomDao.findById(roomId)).thenReturn(Optional.of(room));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/rooms/{id}", roomId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Id").value(roomId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Room"));
    }

    @Test
    void shouldDeleteRoom() throws Exception {
        Long roomId = 1L;

        when(windowDao.deleteWindowByRoomId(roomId)).thenReturn(1);

        doNothing().when(roomDao).deleteById(roomId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/rooms/{id}", roomId))
                .andExpect(status().isNoContent());

        verify(windowDao, times(1)).deleteWindowByRoomId(roomId);
        verify(roomDao, times(1)).deleteById(roomId);
    }

    @Test
    void shouldOpenWindows() throws Exception {
        Long roomId = 1L;

        SensorEntity currTemp = createSensorEntity(1L, "TempSensor", SensorType.TEMPERATURE, 20.0);
        SensorEntity tarTemp = createSensorEntity(1L, "TempSensor", SensorType.TEMPERATURE, 25.0);
        RoomEntity room = createRoomEntity(roomId, "Room");
        room.setCurrentTemperature(currTemp);
        room.setTargetTemperature(tarTemp);

        SensorEntity windowSensor = createSensorEntity(2L, "WindowSensor", SensorType.STATUS, 0.0);
        WindowEntity window = createWindowEntity(1L, "Window", room);
        window.setWindowStatus(windowSensor);

        room.setWindows(List.of(window));

        when(roomDao.findById(roomId)).thenReturn(Optional.of(room));
        when(roomDao.save(any(RoomEntity.class))).thenReturn(room);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/rooms/{id}/openWindows", roomId))
                .andExpect(status().isOk());

        ArgumentCaptor<RoomEntity> roomCaptor = ArgumentCaptor.forClass(RoomEntity.class);
        verify(roomDao).save(roomCaptor.capture());

        RoomEntity updatedRoom = roomCaptor.getValue();
        SensorEntity updatedWindowSensor = updatedRoom.getWindows().getFirst().getWindowStatus();

        assertEquals(1.0, updatedWindowSensor.getSensorValue(), "The window should be opened (sensor value should be 1.0)");
    }

    @Test
    void shouldCloseWindows() throws Exception {
        Long roomId = 1L;

        SensorEntity currTemp = createSensorEntity(1L, "TempSensor", SensorType.TEMPERATURE, 20.0);
        SensorEntity tarTemp = createSensorEntity(1L, "TempSensor", SensorType.TEMPERATURE, 25.0);
        RoomEntity room = createRoomEntity(roomId, "Room");
        room.setCurrentTemperature(currTemp);
        room.setTargetTemperature(tarTemp);

        SensorEntity windowSensor = createSensorEntity(2L, "WindowSensor", SensorType.STATUS, 1.0);
        WindowEntity window = createWindowEntity(1L, "Window", room);
        window.setWindowStatus(windowSensor);

        room.setWindows(List.of(window));
        when(roomDao.findById(roomId)).thenReturn(Optional.of(room));
        when(roomDao.save(any(RoomEntity.class))).thenReturn(room);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/rooms/{id}/closeWindows", roomId))
                .andExpect(status().isOk());
        ArgumentCaptor<RoomEntity> roomCaptor = ArgumentCaptor.forClass(RoomEntity.class);
        verify(roomDao).save(roomCaptor.capture());
        RoomEntity updatedRoom = roomCaptor.getValue();
        SensorEntity updatedWindowSensor = updatedRoom.getWindows().getFirst().getWindowStatus();
        assertEquals(0.0, updatedWindowSensor.getSensorValue(), "The window should be closed (sensor value should be 0.0)");
    }
}
