package com.lab1.SpringBootLab1.SpringDonnees.records;

import com.lab1.SpringBootLab1.SpringDonnees.enums.WindowStatus;

public record Window(Long id, String name, WindowStatus windowStatus, Long roomId) {
}