package com.example.weather_wrapper.services;

import com.example.weather_wrapper.models.CityForecast;
import com.example.weather_wrapper.models.CityInfo;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.Collection;
import java.util.Objects;

public final class CityWeatherForecastResponse {
    private final double latitude;
    private final double longitude;
    private final String resolvedAddress;
    private final String address;
    @JsonAlias("timezone")
    private final String timeZone;
    @JsonAlias("tzoffset")
    private final double timeZoneOffset;
    private final Collection<DayForecastResponse> days;

    public CityWeatherForecastResponse(
            double latitude,
            double longitude,
            String resolvedAddress,
            String address,
            @JsonAlias("timezone") String timeZone,
            @JsonAlias("tzoffset") double timeZoneOffset,
            Collection<DayForecastResponse> days
    ) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.resolvedAddress = resolvedAddress;
        this.address = address;
        this.timeZone = timeZone;
        this.timeZoneOffset = timeZoneOffset;
        this.days = days;
    }

    public CityForecast toCityForecast() {
        return new CityForecast(
                new CityInfo(latitude, longitude, resolvedAddress),
                days.stream().map(DayForecastResponse::toDayForecast).toList()
        );
    }

    public double latitude() {
        return latitude;
    }

    public double longitude() {
        return longitude;
    }

    public String resolvedAddress() {
        return resolvedAddress;
    }

    public String address() {
        return address;
    }

    @JsonAlias("timezone")
    public String timeZone() {
        return timeZone;
    }

    @JsonAlias("tzoffset")
    public double timeZoneOffset() {
        return timeZoneOffset;
    }

    public Collection<DayForecastResponse> days() {
        return days;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CityWeatherForecastResponse) obj;
        return Double.doubleToLongBits(this.latitude) == Double.doubleToLongBits(that.latitude) &&
                Double.doubleToLongBits(this.longitude) == Double.doubleToLongBits(that.longitude) &&
                Objects.equals(this.resolvedAddress, that.resolvedAddress) &&
                Objects.equals(this.address, that.address) &&
                Objects.equals(this.timeZone, that.timeZone) &&
                Double.doubleToLongBits(this.timeZoneOffset) == Double.doubleToLongBits(that.timeZoneOffset) &&
                Objects.equals(this.days, that.days);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, resolvedAddress, address, timeZone, timeZoneOffset, days);
    }

    @Override
    public String toString() {
        return "CityWeatherForecastResponse[" +
                "latitude=" + latitude + ", " +
                "longitude=" + longitude + ", " +
                "resolvedAddress=" + resolvedAddress + ", " +
                "address=" + address + ", " +
                "timeZone=" + timeZone + ", " +
                "timeZoneOffset=" + timeZoneOffset + ", " +
                "days=" + days + ']';
    }

}
