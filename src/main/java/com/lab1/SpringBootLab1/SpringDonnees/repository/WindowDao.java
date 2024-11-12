package com.lab1.SpringBootLab1.SpringDonnees.repository;

import com.lab1.SpringBootLab1.SpringDonnees.models.WindowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WindowDao extends JpaRepository<WindowEntity, Long>, WindowDaoCustom {
}
