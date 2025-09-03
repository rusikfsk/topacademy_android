package com.example.topacademy_android

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SevenTimerApi {
    @GET("bin/api.pl")
    suspend fun civillight(
        @Query("lon") lon: Double,
        @Query("lat") lat: Double,
        @Query("product") product: String = "civillight",
        @Query("output") output: String = "json"
    ): CivilLightResponse
}

object SevenTimerService {
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val api: SevenTimerApi = Retrofit.Builder()
        .baseUrl("http://www.7timer.info/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(SevenTimerApi::class.java)
}

data class CivilLightResponse(
    val product: String?,
    val init: String?,
    val dataseries: List<CivilLightDay>?
)

data class CivilLightDay(
    val date: Int,
    val weather: String,
    val temp2m: Temp2m,
    val wind10m_max: Int?
)

data class Temp2m(
    val max: Int,
    val min: Int
)

data class DailyItem(
    val date: String,
    val weather: String,
    val tMax: Int,
    val tMin: Int,
    val wind: Int
)
