package com.example.topacademy_android.feature.weather.domain.model

data class DailyWeather(
    val date: String,
    val weather: String,
    val tMax: Int,
    val tMin: Int,
    val wind: Int
)
