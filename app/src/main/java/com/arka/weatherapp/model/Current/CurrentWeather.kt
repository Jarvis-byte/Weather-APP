package com.arka.weatherapp.model.Current

data class CurrentWeather(
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val timezone: Int,
)