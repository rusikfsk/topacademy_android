package com.example.topacademy_android.feature.weather.domain.repository

import com.example.topacademy_android.feature.weather.domain.model.DailyWeather

interface WeatherRepository {
    suspend fun getDailyForecast(
        lon: Double,
        lat: Double,
        datePattern: String,
        localeTag: String
    ): List<DailyWeather>
}
