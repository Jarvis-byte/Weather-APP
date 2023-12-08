package com.arka.weatherapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arka.weatherapp.Application.WeatherApplication
import com.arka.weatherapp.R
import com.arka.weatherapp.viewmodels.MainViewModel
import com.arka.weatherapp.viewmodels.MainViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val TAG = "Current_Data"

@AndroidEntryPoint
class HomeScreen_1 : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as WeatherApplication).application.inject(this)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
        mainViewModel.currentWeatherLiveData.observe(this, Observer { currentWeather ->
            Log.d(TAG, currentWeather.main.temp.toString())
        })

        mainViewModel.futureWeatherLiveData.observe(this, Observer { futureWeather ->
            Log.d(TAG, futureWeather.list.get(0).main.temp.toString())
        })
    }
}