package com.example.weather_wrapper.services;

import com.example.weather_wrapper.models.DayForecast;
import com.example.weather_wrapper.models.TemperatureInfo;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public record DayForecastResponse(
        @JsonAlias("datetime") String date,
        @JsonAlias("datetimeEpoch") long dateEpoch,
        @JsonAlias("tempmax") double maxTemperature,
        @JsonAlias("tempmin") double minTemperature,
        @JsonAlias("temp") double temperature,
        @JsonAlias("feelslikemax") double feelsMaxTemperature,
        @JsonAlias("feelslikemin") double feelsMinTemperature,
        @JsonAlias("feelslike") double feelsLikeTemperature,
        double dew,
        double humididy,
        @JsonAlias("windspeed") double windSpeed,
        double pressure,
        double visibility,
        String sunrise,
        long sunriseEpoch,
        String sunset,
        long sunsetEpoch,
        String conditions,
        String description,
        String icon
) {
    public DayForecast toDayForecast(){
        return new DayForecast(
                LocalDate.parse(date),
                new TemperatureInfo(maxTemperature, minTemperature, temperature),
                new TemperatureInfo(feelsMaxTemperature, feelsMinTemperature, feelsLikeTemperature),
                dew,
                humididy,
                windSpeed,
                pressure,
                visibility,
                LocalDateTime.ofEpochSecond(sunriseEpoch, 0, ZoneOffset.UTC),
                LocalDateTime.ofEpochSecond(sunsetEpoch, 0, ZoneOffset.UTC),
                conditions,
                description,
                icon
        );
    }
}
