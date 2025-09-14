package com.example.topacademy_android.feature.weather.data.remote

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

data class Temp2m(val max: Int, val min: Int)
