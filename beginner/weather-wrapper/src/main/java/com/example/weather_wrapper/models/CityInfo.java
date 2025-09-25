package com.example.weather_wrapper.models;

import java.io.Serializable;

public record CityInfo(
        double latitude,
        double longitude,
        String address
) implements Serializable {
}
