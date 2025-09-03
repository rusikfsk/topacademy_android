package com.example.topacademy_android

import android.content.Context

object WeatherUi {
    fun iconFor(weather: String): Int {
        val w = weather.lowercase()
        if (w.contains("ts")) return R.drawable.ic_weather_storm
        if (w.contains("snow")) return R.drawable.ic_weather_snow
        if (w.contains("rain") || w.contains("shower") || w.contains("rainsnow")) return R.drawable.ic_weather_rain
        if (w.contains("humid") || w.contains("fog")) return R.drawable.ic_weather_fog
        if (w.contains("cloudy") || w.contains("pcloudy") || w.contains("mcloudy")) return R.drawable.ic_weather_cloudy
        return R.drawable.ic_weather_sunny
    }

    fun label(context: Context, weather: String): String {
        val w = weather.lowercase()
        return when {
            w == "clear" -> context.getString(R.string.w_clear)
            w.contains("cloud") || w.contains("pcloudy") || w.contains("mcloudy") -> context.getString(R.string.w_clouds)
            w.contains("humid") || w.contains("fog") -> context.getString(R.string.w_fog)
            w.contains("snow") -> context.getString(R.string.w_snow)
            w.contains("ts") -> context.getString(R.string.w_thunder)
            w.contains("rain") || w.contains("shower") || w.contains("rainsnow") -> context.getString(R.string.w_rain)
            else -> context.getString(R.string.w_unknown)
        }
    }

    fun isWet(weather: String): Boolean {
        val w = weather.lowercase()
        return w.contains("rain") || w.contains("shower") || w.contains("snow")
    }

    fun cardColor(tMax: Int, weather: String): Int {
        if (isWet(weather)) return R.color.weather_wet
        return when {
            tMax >= 25 -> R.color.weather_hot
            tMax >= 15 -> R.color.weather_warm
            tMax >= 5 -> R.color.weather_cool
            else -> R.color.weather_cold
        }
    }
}
