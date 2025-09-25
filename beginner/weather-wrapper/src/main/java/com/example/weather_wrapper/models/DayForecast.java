package com.example.weather_wrapper.models;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record DayForecast(
        LocalDate date,
        TemperatureInfo temperatureInfo,
        TemperatureInfo feelsLikeTemperatureInfo,
        double dew,
        double humidity,
        double windSpeed,
        double pressure,
        double visibility,
        LocalDateTime sunrise,
        LocalDateTime sunset,
        String condition,
        String description,
        String icon
)implements Serializable {
}
