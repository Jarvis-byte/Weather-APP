package com.arka.weatherapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arka.weatherapp.retrofit.WeatherAPI
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherAPI: WeatherAPI){


}