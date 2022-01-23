package fathian.ali.weatherapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import fathian.ali.weatherapp.data.local.Units
import fathian.ali.weatherapp.domain.entity.WeatherData
import java.text.SimpleDateFormat
import java.util.*

data class WeatherDto(
    @SerializedName("base") val base: String,
    @SerializedName("clouds") val clouds: Clouds,
    @SerializedName("cod") val cod: Int,
    @SerializedName("coord") val coord: Coord,
    @SerializedName("dt") val dt: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: Main,
    @SerializedName("name") val name: String,
    @SerializedName("sys") val sys: Sys,
    @SerializedName("timezone") val timezone: Long,
    @SerializedName("visibility") val visibility: Int,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("wind") val wind: Wind,
)

fun WeatherDto.toWeatherData(unit: Units): WeatherData {
    val unitSign = if (unit == Units.METRIC) "°C" else "°F"
    return WeatherData(
        city = name,
        country = sys.country,
        temp = main.temp.toString() + unitSign,
        feelsLike = main.feelsLike.toString() + unitSign,
        icon = weather[0].icon,
        description = weather[0].description,
        windSpeed = if (unit == Units.METRIC) {
            (wind.speed * 3.6).toString() + "km/h"
        } else {
            wind.speed.toString() + "mph"
        },
        humidity = main.humidity.toString() + "%",
        pressure = (main.pressure / 10).toString() + "kPa",
        visibility = (visibility / 1000).toString() + "km",
        cloudiness = clouds.all.toString() + "%",
        sunrise = SimpleDateFormat("HH:mm", Locale.US).format(Date(sys.sunrise * 1000)),
        sunset = SimpleDateFormat("HH:mm", Locale.US).format(Date(sys.sunset * 1000)),
        unit = unit
    )
}