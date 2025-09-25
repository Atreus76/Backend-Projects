package com.example.weather_wrapper.services;

import com.example.weather_wrapper.models.CityForecast;

public interface WeatherService {
    CityForecast getForecastForCity(String city);
}
