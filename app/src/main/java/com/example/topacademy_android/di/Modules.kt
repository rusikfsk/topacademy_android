package com.example.topacademy_android.di

import com.example.topacademy_android.feature.calculator.data.repository.CalculatorRepositoryImpl
import com.example.topacademy_android.feature.calculator.domain.repository.CalculatorRepository
import com.example.topacademy_android.feature.calculator.domain.usecase.EvaluateExpressionUseCase
import com.example.topacademy_android.feature.calculator.presentation.CalculatorViewModel
import com.example.topacademy_android.feature.weather.data.remote.SevenTimerApi
import com.example.topacademy_android.feature.weather.data.repository.WeatherRepositoryImpl
import com.example.topacademy_android.feature.weather.domain.repository.WeatherRepository
import com.example.topacademy_android.feature.weather.domain.usecase.GetDailyForecastUseCase
import com.example.topacademy_android.feature.weather.presentation.WeatherViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val coreModule = module {
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://www.7timer.info/") // HTTPS обязательно
            .client(get<OkHttpClient>())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }
    single<SevenTimerApi> { get<Retrofit>().create(SevenTimerApi::class.java) }
}

val weatherModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
    factory { GetDailyForecastUseCase(get()) }
    viewModel { WeatherViewModel(get()) }
}

val calculatorModule = module {
    single<CalculatorRepository> { CalculatorRepositoryImpl() }
    factory { EvaluateExpressionUseCase(get()) }
    viewModel { CalculatorViewModel(get()) }
}
