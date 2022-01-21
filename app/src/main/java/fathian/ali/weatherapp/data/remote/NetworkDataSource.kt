package fathian.ali.weatherapp.data.remote

import fathian.ali.weatherapp.common.Either
import fathian.ali.weatherapp.data.remote.dto.WeatherDto

interface NetworkDataSource {

    /**
     * get weather information
     */
    suspend fun getWeatherInformation(city: String, units: String): Either<WeatherDto, String?>
}