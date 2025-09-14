package com.example.topacademy_android.feature.weather.presentation

import android.content.Context
import com.example.topacademy_android.R

object WeatherUi {

    fun iconFor(weather: String): Int {
        val w = weather.lowercase()
        return when {
            "snow" in w -> R.drawable.ic_weather_snow
            "rain" in w || "shower" in w || "rainsnow" in w -> R.drawable.ic_weather_rain
            "humid" in w || "fog" in w -> R.drawable.ic_weather_fog
            "cloudy" in w || "pcloudy" in w || "mcloudy" in w -> R.drawable.ic_weather_cloudy
            "ts" in w || "thunder" in w -> R.drawable.ic_weather_storm
            else -> R.drawable.ic_weather_sunny
        }
    }

    fun labelFor(context: Context, weather: String): String {
        val w = weather.lowercase()
        return when {
            w == "clear" -> context.getString(R.string.w_clear)
            "cloud" in w || "pcloudy" in w || "mcloudy" in w -> context.getString(R.string.w_clouds)
            "fog" in w || "humid" in w -> context.getString(R.string.w_fog)
            "snow" in w -> context.getString(R.string.w_snow)
            "ts" in w || "thunder" in w -> context.getString(R.string.w_thunder)
            "rain" in w || "shower" in w -> context.getString(R.string.w_rain)
            else -> context.getString(R.string.w_unknown)
        }
    }

    private fun isWet(weather: String): Boolean {
        val w = weather.lowercase()
        return "rain" in w || "shower" in w || "snow" in w
    }

    fun colorFor(tMax: Int, weather: String): Int {
        return if (isWet(weather)) {
            R.color.weather_wet
        } else {
            when {
                tMax >= 25 -> R.color.weather_hot
                tMax >= 15 -> R.color.weather_warm
                tMax >= 5  -> R.color.weather_cool
                else       -> R.color.weather_cold
            }
        }
    }
}
