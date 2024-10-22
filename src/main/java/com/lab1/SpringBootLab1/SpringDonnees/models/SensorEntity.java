package com.lab1.SpringBootLab1.SpringDonnees.models;

import com.lab1.SpringBootLab1.SpringDonnees.enums.SensorType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "SP_SENSOR")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "notImportant")
public class SensorEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "sensor_value")
    private Double sensorValue;

    @Column(name = "sensor_type")
    @Enumerated(EnumType.STRING)
    private SensorType sensorType;

    @Transient
    private Integer notImportant;

    public SensorEntity(SensorType sensorType, String name) {
        this.name = name;
        this.sensorType = sensorType;
    }

}
