package com.lab1.SpringBootLab1.SpringDonnees.records;

import java.util.List;

public record ApiGouvResponse(
        String version,
        String query,
        Integer limit,
        List<ApiGouvFeature> features
) {

}