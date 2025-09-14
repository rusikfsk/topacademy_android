package com.example.topacademy_android.feature.weather.data.repository

import com.example.topacademy_android.feature.weather.data.remote.SevenTimerApi
import com.example.topacademy_android.feature.weather.domain.model.DailyWeather
import com.example.topacademy_android.feature.weather.domain.repository.WeatherRepository
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherRepositoryImpl(
    private val api: SevenTimerApi
) : WeatherRepository {
    override suspend fun getDailyForecast(
        lon: Double,
        lat: Double,
        datePattern: String,
        localeTag: String
    ): List<DailyWeather> {
        val res = api.civillight(lon, lat)
        val series = res.dataseries ?: emptyList()
        val inFmt = SimpleDateFormat("yyyyMMdd", Locale.US)
        val outFmt = SimpleDateFormat(datePattern, Locale.forLanguageTag(localeTag))
        val list = ArrayList<DailyWeather>(series.size)
        for (d in series) {
            val label = try { outFmt.format(inFmt.parse(d.date.toString())!!) } catch (_: Exception) { d.date.toString() }
            val wind = d.wind10m_max ?: 0
            list.add(DailyWeather(label, d.weather, d.temp2m.max, d.temp2m.min, wind))
        }
        return list
    }
}
