package com.arka.weatherapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arka.weatherapp.model.Current.CurrentWeather
import com.arka.weatherapp.model.Future.FutureWeather
import com.arka.weatherapp.retrofit.WeatherAPI
import com.arka.weatherapp.ui.TAG
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherAPI: WeatherAPI) {
    private val _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather: LiveData<CurrentWeather>
        get() = _currentWeather

    private val _futureWeather = MutableLiveData<FutureWeather>()
    val futureWeather: LiveData<FutureWeather>
        get() = _futureWeather

    suspend fun getCurrentWeather() {
        try {
            val result = weatherAPI.getTodayWeather("Bengaluru", "9b8cb8c7f11c077f8c4e217974d9ee40")
            if (result.isSuccessful && result.body() != null) {
                _currentWeather.postValue(result.body())
            } else {
                throw Exception("Error fetching current weather")
            }
        } catch (e: Exception) {
            // Log the error for debugging
            Log.e(TAG, "Error fetching current weather", e)
            throw e
        }
    }

    suspend fun getFutureWeather() {
        try {
            val result =
                weatherAPI.getFutureWeather("Bengaluru", "9b8cb8c7f11c077f8c4e217974d9ee40")
            if (result.isSuccessful && result.body() != null) {
                _futureWeather.postValue(result.body())
            } else {
                throw Exception("Error fetching future weather")
            }
        } catch (e: Exception) {
            // Log the error for debugging
            Log.e(TAG, "Error fetching future weather", e)
            throw e
        }
    }

}