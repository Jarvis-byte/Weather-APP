package com.arka.weatherapp.adapter

import android.content.Context
import android.util.Log
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
import java.util.Locale

class futureWeatherAdapter(val context: Context, val weather: List<Info>) :
    RecyclerView.Adapter<WeatherViewHolder>() {
    private var weatherList: List<Info> = emptyList()
    private val todayDayName: String = getDayNameFromTimestamp(System.currentTimeMillis() / 1000L)

    init {
        weatherList = weather
    }

    data class GroupedWeather(
        val day: String,
        val averageTemp: Double
    )

    // Group the data by day and calculate the average temperature
    private val groupedWeatherList: List<GroupedWeather> by lazy {
        weatherList.groupBy { getDayNameFromTimestamp(it.dt.toLong()) }
            .map { (day, infos) ->
                val averageTemp = infos.map { it.main.temp }.average()
                GroupedWeather(day, averageTemp)
            }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        return WeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
        // Exclude today's data from the count
        Log.i("Count", groupedWeatherList.size.toString())
        return groupedWeatherList.size - 1
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {

        val groupedWeather = groupedWeatherList[position + 1]
        Log.i("Day", groupedWeather.day + " at " + position.toString())
        // Find all items in the original list that belong to the current group
        val itemsInGroup = weatherList.filter {
            getDayNameFromTimestamp(it.dt.toLong()) == groupedWeather.day
        }

        // Calculate the average temperature for the current group
        val averageTemp = itemsInGroup.map { it.main.temp }.average()


        val temperatureConverter = TemperatureConverter()
        // Call the kelvinToCelsius function using the instance

        holder.temp.text = "${temperatureConverter.kelvinToCelsius(averageTemp)} C"
        holder.day.text = groupedWeather.day


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