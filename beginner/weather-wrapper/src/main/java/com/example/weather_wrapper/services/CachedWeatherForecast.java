package com.example.weather_wrapper.services;

import com.example.weather_wrapper.models.CityForecast;

import java.io.Serializable;

public record CachedWeatherForecast(
        CityForecast cityForecast,
        RuntimeException exception
)implements Serializable {
}
