package com.lab1.SpringBootLab1.SpringDonnees.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Entity
@Table(name = "SP_ROOM")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"windows", "currentTemperature"})
public class RoomEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private int floor;

    @Column(nullable = false)
    private String name;

    @OneToOne
    private SensorEntity currentTemperature;

    @OneToOne
    private SensorEntity targetTemperature;

    @OneToMany(mappedBy="room")
    @JsonManagedReference
    private List<WindowEntity> windows;

    public RoomEntity(String name, SensorEntity currentTemperature, SensorEntity targetTemperature, int floor) {
        this.floor = floor;
        this.name = name;
        this.currentTemperature = currentTemperature;
        this.targetTemperature = targetTemperature;
    }

}
