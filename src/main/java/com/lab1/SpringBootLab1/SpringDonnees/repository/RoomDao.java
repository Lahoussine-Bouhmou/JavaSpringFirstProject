package com.lab1.SpringBootLab1.SpringDonnees.repository;

import com.lab1.SpringBootLab1.SpringDonnees.models.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomDao extends JpaRepository<RoomEntity, Long> {
}
