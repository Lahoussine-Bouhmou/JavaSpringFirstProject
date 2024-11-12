package com.lab1.SpringBootLab1.SpringDonnees.records;

import java.util.List;

public record Room(Long Id, String name, Integer floor, Double currentTemperature, Double targetTemperature, List<Window> windows) {
}

