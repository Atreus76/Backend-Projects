package com.example.weather_wrapper.models;

import java.io.Serializable;

public record TemperatureInfo(
        double max,
        double min,
        double temp
)implements Serializable {
}
