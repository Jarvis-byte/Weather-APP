package com.arka.weatherapp.ui

import android.os.Bundle

import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.arka.weatherapp.Application.WeatherApplication
import com.arka.weatherapp.R
import com.arka.weatherapp.adapter.futureWeatherAdapter
import com.arka.weatherapp.model.Future.Info
import com.arka.weatherapp.utils.TemperatureConverter
import com.arka.weatherapp.viewmodels.MainViewModel
import com.arka.weatherapp.viewmodels.MainViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val TAG = "Current_Data"

@AndroidEntryPoint
class HomeScreen_1 : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var RVList: RecyclerView

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    lateinit var adapter: futureWeatherAdapter
    private val currentWeathertxt: TextView
        get() = findViewById(R.id.current_weather)
    private val city: TextView
        get() = findViewById(R.id.city)

    val mutableLiveData: MutableLiveData<List<Info>> = MutableLiveData()

    // Declare FinalFutureWeatherList as LiveData
    lateinit var FinalFutureWeatherList: LiveData<List<Info>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RVList = findViewById<RecyclerView>(R.id.RVList)
        RVList.layoutManager = LinearLayoutManager(this@HomeScreen_1) // Set layout manager here
        (application as WeatherApplication).application.inject(this)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
        val animationView: LottieAnimationView = findViewById(R.id.loading_animation)
        animationView.playAnimation()
        mainViewModel.currentWeatherLiveData.observe(this, Observer { currentWeather ->

            val temperatureConverter = TemperatureConverter()
            // Call the kelvinToCelsius function using the instance
            val kelvinValue = currentWeather.main.temp


            val celsiusTemperature = temperatureConverter.kelvinToCelsius(kelvinValue)
            currentWeathertxt.text = "${celsiusTemperature}°C"
            city.text = currentWeather.name
        })

        mainViewModel.futureWeatherLiveData.observe(this, Observer { futureWeather ->
            mutableLiveData.value = futureWeather.list
        })

        mutableLiveData.observe(this, Observer { updatedList ->
            FinalFutureWeatherList = MutableLiveData(updatedList)
            adapter = futureWeatherAdapter(this@HomeScreen_1, updatedList)
            RVList.visibility = View.VISIBLE
            slideUpRecyclerView()
            currentWeathertxt.visibility = View.VISIBLE
            city.visibility = View.VISIBLE
            animationView.visibility = View.GONE
            RVList.adapter = adapter

        })

    }

    private fun slideUpRecyclerView() {
        val slideUpAnimation = TranslateAnimation(
            0f,
            0f,
            1000f,
            0f
        ) // Adjust the starting and ending Y values as needed
        slideUpAnimation.duration = 1000 // Adjust the duration as needed
        RVList.startAnimation(slideUpAnimation)
        RVList.visibility = View.VISIBLE
    }

}