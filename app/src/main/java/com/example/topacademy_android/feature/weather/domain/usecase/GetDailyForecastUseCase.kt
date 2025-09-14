package com.example.topacademy_android.feature.weather.domain.usecase

import com.example.topacademy_android.feature.weather.domain.model.DailyWeather
import com.example.topacademy_android.feature.weather.domain.repository.WeatherRepository

class GetDailyForecastUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(
        lon: Double,
        lat: Double,
        datePattern: String,
        localeTag: String
    ): List<DailyWeather> {
        return repository.getDailyForecast(lon, lat, datePattern, localeTag)
    }
}
