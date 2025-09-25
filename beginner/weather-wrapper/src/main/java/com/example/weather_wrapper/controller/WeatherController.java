package com.example.weather_wrapper.controller;

import com.example.weather_wrapper.models.CityForecast;
import com.example.weather_wrapper.services.CachedWeatherForecast;
import com.example.weather_wrapper.services.CachedWeatherService;
import com.example.weather_wrapper.services.ForecastWeatherService;
import com.example.weather_wrapper.services.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    private final CachedWeatherService cachedWeatherService;

    // Constructor injection
    public WeatherController(CachedWeatherService cachedWeatherService) {
        this.cachedWeatherService = cachedWeatherService;

    }

    @GetMapping
    public CityForecast getWeather(@RequestParam String city) {
        return cachedWeatherService.getForecastForCity(city);
    }
}
