package fathian.ali.weatherapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import fathian.ali.weatherapp.domain.entity.WeatherData

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

fun WeatherDto.toWeatherData(): WeatherData {
    return WeatherData(
        city = name,
        country = sys.country,
        temp = main.temp,
        feelsLike = main.feelsLike,
        icon = weather[0].icon,
        description = weather[0].description,
        windSpeed = wind.speed,
        humidity = main.humidity,
        pressure = main.pressure,
        visibility = visibility,
        cloudiness = clouds.all,
        sunrise = sys.sunrise * 1000,
        sunset = sys.sunset * 1000
    )
}