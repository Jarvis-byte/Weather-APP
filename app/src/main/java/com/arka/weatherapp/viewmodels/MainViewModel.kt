package com.arka.weatherapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arka.weatherapp.model.Current.CurrentWeather
import com.arka.weatherapp.model.Future.FutureWeather
import com.arka.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val _errorLiveData: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    val currentWeatherLiveData: LiveData<CurrentWeather>
        get() = repository.currentWeather

    val futureWeatherLiveData: LiveData<FutureWeather>
        get() = repository.futureWeather

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                repository.getCurrentWeather()
                repository.getFutureWeather()
            } catch (e: Exception) {
                // Handle the exception and set the error message
                _errorLiveData.value = "Something went wrong: ${e.message}"
            }
        }
    }

    fun retry() {
        // Implement your retry logic here
        // You may want to call the repository functions again
        fetchData()
    }
}