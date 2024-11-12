package com.lab1.SpringBootLab1.SpringDonnees.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "SP_WINDOW")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"windowStatus", "room"})
public class WindowEntity {
    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne
    private SensorEntity windowStatus;

    @ManyToOne
    @JsonBackReference
    private RoomEntity room;

    public WindowEntity(String name, SensorEntity windowStatus, RoomEntity roomEntity) {
        this.name = name;
        this.windowStatus = windowStatus;
        this.room = roomEntity;
    }

}