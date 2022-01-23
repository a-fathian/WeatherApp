package fathian.ali.weatherapp.domain.entity

import fathian.ali.weatherapp.data.local.Units

data class WeatherData(
    val city: String,
    val country: String,
    val temp: String,
    val feelsLike: String,
    val icon: String,
    val description: String,
    val windSpeed: String,
    val humidity: String,
    val pressure: String,
    val visibility: String,
    val cloudiness: String,
    val sunrise: String,
    val sunset: String,
    val unit: Units
) {
    companion object {
        val Default = WeatherData(
            city = "",
            country = "",
            temp = "",
            feelsLike = "",
            icon = "",
            description = "",
            windSpeed = "",
            humidity = "",
            pressure = "",
            visibility = "",
            cloudiness = "",
            sunrise = "",
            sunset = "",
            unit = Units.METRIC
        )
    }
}