package com.example.weather_wrapper.models;

import java.io.Serializable;
import java.util.Collection;

public record CityForecast(
        CityInfo cityInfo,
        Collection<DayForecast> days
)implements Serializable {
}
