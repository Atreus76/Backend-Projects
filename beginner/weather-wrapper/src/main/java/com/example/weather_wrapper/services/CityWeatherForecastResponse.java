package com.example.weather_wrapper.services;

import com.example.weather_wrapper.models.CityForecast;
import com.example.weather_wrapper.models.CityInfo;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.Collection;

public record CityWeatherForecastResponse(
        double latitude,
        double longitude,
        String resolvedAddress,
        String address,
        @JsonAlias("timezone") String timeZone,
        @JsonAlias("tzoffset") double timeZoneOffset,
        Collection<DayForecastResponse> days
) {
    public CityForecast toCityForecast(){
        return new CityForecast(
                new CityInfo(latitude, longitude, resolvedAddress),
                days.stream().map(DayForecastResponse::toDayForecast).toList()
        );
    }
}
