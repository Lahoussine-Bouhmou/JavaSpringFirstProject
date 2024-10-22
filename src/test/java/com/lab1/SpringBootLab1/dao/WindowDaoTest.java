package com.lab1.SpringBootLab1.dao;

import com.lab1.SpringBootLab1.SpringDonnees.models.RoomEntity;
import com.lab1.SpringBootLab1.SpringDonnees.models.WindowEntity;
import com.lab1.SpringBootLab1.SpringDonnees.repository.RoomDao;
import com.lab1.SpringBootLab1.SpringDonnees.repository.WindowDao;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;

@DataJpaTest //
public class WindowDaoTest {
    @Autowired //
    private WindowDao windowDao;

    @Autowired
    private RoomDao roomDao;

    @Test
    public void shouldFindAWindowById() {
        WindowEntity window = windowDao.getReferenceById(-10L); //
        Assertions.assertThat(window.getName()).isEqualTo("Window 1");
        Assertions.assertThat(window.getWindowStatus().getSensorValue()).isEqualTo(1.0);
    }

    @Test
    public void shouldFindRoomsWithOpenWindows() {
        List<WindowEntity> result = windowDao.findRoomsWithOpenWindows(-10L);
        Assertions.assertThat(result)
                .hasSize(1)
                .extracting("id", "name")
                .containsExactly(Tuple.tuple(-10L, "Window 1"));
    }

    @Test
    public void shouldCloseOrOpenAllWindowsByRoomId() {
        // Given
        Long roomId = -9L; // ID of Room2

        // When - Close all windows in Room2
        windowDao.closeOrOpenAllWindowsByRoomId(roomId, false);

        // Then - All windows should be closed (sensor value == 0.0)
        List<WindowEntity> windowsAfterClose = windowDao.findWindowByRoomName("Room2");
        Assertions.assertThat(windowsAfterClose)
                .hasSize(2)
                .allMatch(window -> window.getWindowStatus().getSensorValue() == 0.0);

        // When - Open all windows in Room2
        windowDao.closeOrOpenAllWindowsByRoomId(roomId, true);

        // Then - All windows should be open (sensor value > 0.0)
        List<WindowEntity> windowsAfterOpen = windowDao.findWindowByRoomName("Room2");
        Assertions.assertThat(windowsAfterOpen)
                .hasSize(2)
                .allMatch(window -> window.getWindowStatus().getSensorValue() > 0.0);
    }


    @Test
    public void shouldNotFindRoomsWithOpenWindows() {
        List<WindowEntity> result = windowDao.findRoomsWithOpenWindows(-9L);
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void shouldFindWindowByRoomName() {
        // Given
        String roomName = "Room1";

        // When
        List<WindowEntity> windows = windowDao.findWindowByRoomName(roomName);

        // Then
        Assertions.assertThat(windows)
                .hasSize(2)
                .extracting("id", "name")
                .containsExactlyInAnyOrder(
                        Tuple.tuple(-10L, "Window 1"),
                        Tuple.tuple(-9L, "Window 2")
                );
    }

    @Test
    public void shouldDeleteWindowByRoomId() {
        // Given
        Long roomId = -10L; // ID of Room1

        // When
        int deletedCount = windowDao.deleteWindowByRoomId(roomId);

        // Then
        Assertions.assertThat(deletedCount).isEqualTo(2);

        // Verify that windows are deleted
        List<WindowEntity> windows = windowDao.findWindowByRoomName("Room1");
        Assertions.assertThat(windows).isEmpty();
    }

    @Test
    public void shouldDeleteWindowsRoom() {
        RoomEntity room = roomDao.getById(-10L);
        List<Long> roomIds = room.getWindows().stream().map(WindowEntity::getId).collect(Collectors.toList());
        Assertions.assertThat(roomIds).hasSize(2);

        windowDao.deleteWindowByRoomId(-10L);
        List<WindowEntity> result = windowDao.findAllById(roomIds);
        Assertions.assertThat(result).isEmpty();

    }



}