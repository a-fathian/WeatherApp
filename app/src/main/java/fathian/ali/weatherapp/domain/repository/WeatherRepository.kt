package fathian.ali.weatherapp.domain.repository

import fathian.ali.weatherapp.common.Either
import fathian.ali.weatherapp.data.local.Units
import fathian.ali.weatherapp.domain.entity.WeatherData

interface WeatherRepository {

    suspend fun getWeather(city: String): Either<WeatherData, String?>

    suspend fun getString(key: String): Units

    suspend fun storeString(key: String, value: Units)

}