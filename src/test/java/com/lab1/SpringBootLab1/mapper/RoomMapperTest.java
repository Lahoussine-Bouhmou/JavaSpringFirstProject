package com.lab1.SpringBootLab1.mapper;

import com.lab1.SpringBootLab1.FakeDataGenerator.FakeEntityBuilder;
import com.lab1.SpringBootLab1.SpringDonnees.enums.WindowStatus;
import com.lab1.SpringBootLab1.SpringDonnees.mappers.RoomMapper;
import com.lab1.SpringBootLab1.SpringDonnees.models.RoomEntity;
import com.lab1.SpringBootLab1.SpringDonnees.records.Room;
import com.lab1.SpringBootLab1.SpringDonnees.records.Window;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RoomMapperTest {

    @Test
    void shouldMapRoom() {
        // Arrange
        RoomEntity roomEntity = FakeEntityBuilder.createRoomEntity(11L, "Room");
        // There is no createBuildingEntity method in FakeEntityBuilder.

        // Act
        Room room = RoomMapper.mapToRoom(roomEntity);

        // Assert
        Room expectedRoom = new Room(
                11L,
                "Room",
                1,
                23.2,
                18.2,
                List.of(
                        new Window(
                                111L,
                                "Window1Room",
                                WindowStatus.CLOSED,
                                11L
                        ),
                        new Window(
                                112L,
                                "Window2Room",
                                WindowStatus.CLOSED,
                                11L
                        )
                )
        );
        Assertions.assertThat(room).usingRecursiveAssertion().isEqualTo(expectedRoom);
    }
}