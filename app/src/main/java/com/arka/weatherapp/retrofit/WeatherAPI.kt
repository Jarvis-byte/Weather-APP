package com.arka.weatherapp.retrofit

import com.arka.weatherapp.model.Current.CurrentWeather
import com.arka.weatherapp.model.Future.FutureWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("weather")
    suspend fun getTodayWeather(
        @Query("q") q: String,
        @Query("APPID") APPID: String
    ): Response<CurrentWeather>

    @GET("forecast")
    suspend fun getFutureWeather(
        @Query("q") q: String,
        @Query("APPID") APPID: String
    ): Response<FutureWeather>
}