package com.arka.weatherapp.utils
import java.text.DecimalFormat
class TemperatureConverter {
    fun kelvinToCelsius(kelvin: Double): String {
        val celsius = kelvin - 273.15
        val decimalFormat = DecimalFormat("#")
        return decimalFormat.format(celsius)
    }
}