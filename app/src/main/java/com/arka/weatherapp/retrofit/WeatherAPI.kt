package com.arka.weatherapp.retrofit

import com.arka.weatherapp.model.Current.CurrentWeather
import com.arka.weatherapp.model.Future.FutureWeather
import retrofit2.Response
import retrofit2.http.GET

interface WeatherAPI {
    @GET("weather")
    suspend fun getTodayWeather(): Response<List<CurrentWeather>>

    @GET("forecast")
    suspend fun getFutureWeather(): Response<List<FutureWeather>>
}