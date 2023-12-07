package com.arka.weatherapp.di

import com.arka.weatherapp.ui.HomeScreen_1
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {

    fun inject(homescreen1: HomeScreen_1)


}