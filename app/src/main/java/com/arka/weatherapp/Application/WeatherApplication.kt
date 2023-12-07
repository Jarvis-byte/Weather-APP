package com.arka.weatherapp.Application

import android.app.Application
import com.arka.weatherapp.di.ApplicationComponent
import com.arka.weatherapp.di.DaggerApplicationComponent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApplication : Application() {

    lateinit var application: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        application = DaggerApplicationComponent.builder().build()
    }
}