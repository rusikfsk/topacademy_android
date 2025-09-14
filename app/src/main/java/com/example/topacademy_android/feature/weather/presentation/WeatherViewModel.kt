package com.example.topacademy_android.feature.weather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topacademy_android.feature.weather.domain.model.DailyWeather
import com.example.topacademy_android.feature.weather.domain.usecase.GetDailyForecastUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale

sealed class WeatherState {
    data object Loading : WeatherState()
    data class Data(val items: List<DailyWeather>) : WeatherState()
    data class Error(val message: String) : WeatherState()
}

class WeatherViewModel(
    private val getDailyForecast: GetDailyForecastUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val state: StateFlow<WeatherState> = _state

    fun load(lon: Double, lat: Double, datePattern: String, locale: Locale) {
        _state.value = WeatherState.Loading
        viewModelScope.launch {
            try {
                val data = getDailyForecast(lon, lat, datePattern, locale.toLanguageTag())
                _state.value = WeatherState.Data(data)
            } catch (e: Exception) {
                _state.value = WeatherState.Error(e.localizedMessage ?: "error")
            }
        }
    }
}
