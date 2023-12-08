package com.arka.weatherapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arka.weatherapp.model.Current.CurrentWeather
import com.arka.weatherapp.model.Future.FutureWeather
import com.arka.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: WeatherRepository) : ViewModel() {
    val currentWeatherLiveData: LiveData<CurrentWeather>
        get() = repository.currentWeather

    val futureWeatherLiveData: LiveData<FutureWeather>
        get() = repository.futureWeather

    init {
        viewModelScope.launch {
            repository.getCurrentWeather()
            repository.getFutureWeather()
        }
    }
}