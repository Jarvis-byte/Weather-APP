package com.arka.weatherapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arka.weatherapp.R
import com.arka.weatherapp.model.Future.Info
import com.arka.weatherapp.utils.TemperatureConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.*
class futureWeatherAdapter(val context: Context, val weather: List<Info>) :
    RecyclerView.Adapter<WeatherViewHolder>() {
    private var weatherList: List<Info> = emptyList()

    init {
        weatherList = weather
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        return WeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = weatherList[position]
        val dt_Unix = weather.dt.toString()
        val timestamp = dt_Unix.toLong()
        val dayName = getDayNameFromTimestamp(timestamp)

        val temperatureConverter = TemperatureConverter()
        // Call the kelvinToCelsius function using the instance
        val kelvinValue = weather.main.temp
        holder.temp.text = "${temperatureConverter.kelvinToCelsius(kelvinValue)} C"
        holder.day.text = dayName
    }

    private fun getDayNameFromTimestamp(dtUnix: Long): String {
        val instant = Instant.ofEpochSecond(dtUnix)
        val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        val dayOfWeek = localDateTime.dayOfWeek
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    }



}

class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var day = itemView.findViewById<TextView>(R.id.day)
    var temp = itemView.findViewById<TextView>(R.id.temp)

}