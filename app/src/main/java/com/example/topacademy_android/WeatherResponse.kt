package com.example.topacademy_android

data class WeatherResponse(
    val product: String,
    val init: String,
    val dataseries: List<DataSeries>
)

data class DataSeries(
    val timepoint: Int,
    val cloudcover: Int,
    val temp2m: Int,
    val weather: String
)