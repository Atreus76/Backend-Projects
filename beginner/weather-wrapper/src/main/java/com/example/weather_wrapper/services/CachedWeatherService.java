package com.example.weather_wrapper.services;

import com.example.weather_wrapper.models.CityForecast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Primary
public class CachedWeatherService implements WeatherService{

    private static final Logger log = LoggerFactory.getLogger(CachedWeatherService.class);
    private int cacheDurationSeconds = 60;
    private final ForecastWeatherService weatherService;
    private final RedisTemplate<String, CachedWeatherForecast> redisTemplate;

    public CachedWeatherService(
            @Qualifier("external") ForecastWeatherService weatherService,
            RedisConnectionFactory redisConnectionFactory) {
        this.weatherService = weatherService;
        this.redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();
    }


    @Override
    public CityForecast getForecastForCity(String city) {
        log.info("Getting forecast for city: {}", city);
        CachedWeatherForecast cachedForecast = redisTemplate.opsForValue().get(city);
        if (cachedForecast == null) {
            log.info("Cache miss for city: {}", city);
            try {
                CityForecast forecast = weatherService.getForecastForCity(city);
                cachedForecast = new CachedWeatherForecast(forecast, null);
            } catch (RuntimeException e) {
                cachedForecast = new CachedWeatherForecast(null, e);
            }
            log.info("Caching forecast for city: {}", city);
            redisTemplate.opsForValue().set(city, cachedForecast, Duration.ofSeconds(cacheDurationSeconds));
        } else {
            log.info("Cache hit for city: {}", city);
        }
        if (cachedForecast.exception() != null) {
            throw cachedForecast.exception();
        }
        return cachedForecast.cityForecast();
    }
}
