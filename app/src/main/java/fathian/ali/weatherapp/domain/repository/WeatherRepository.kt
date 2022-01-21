package fathian.ali.weatherapp.domain.repository

import fathian.ali.weatherapp.common.Either
import fathian.ali.weatherapp.domain.entity.WeatherData

interface WeatherRepository {
    suspend fun getWeather(city: String, units: String): Either<WeatherData, String?>
}