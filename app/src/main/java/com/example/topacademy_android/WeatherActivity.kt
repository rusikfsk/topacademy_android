package com.example.topacademy_android

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.topacademy_android.databinding.ActivityWeatherBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchWeather()
    }

    private fun fetchWeather() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.7timer.info/")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }).build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(WeatherApi::class.java)

        lifecycleScope.launch {
            try {
                val weatherData = withContext(Dispatchers.IO) {
                    api.getWeatherForecast(
                        longitude = 37.6176,  // Moscow
                        latitude = 55.7558
                    )
                }

                val weatherText = StringBuilder()
                weatherData.dataseries.forEach {
                    val line = "⏰ ${it.timepoint}h | 🌡 ${it.temp2m}°C | ☁ Cloud: ${it.cloudcover} | 🌦 Weather: ${it.weather}\n"
                    weatherText.append(line)
                    Log.i("WEATHER", line.trim())
                }

                binding.textWeather.text = weatherText.toString()

            } catch (e: Exception) {
                Log.e("WEATHER", "Ошибка запроса: ${e.message}")
                binding.textWeather.text = "Ошибка загрузки данных: ${e.message}"
            }
        }
    }
}




