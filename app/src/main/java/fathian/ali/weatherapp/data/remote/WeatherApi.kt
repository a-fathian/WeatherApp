package fathian.ali.weatherapp.data.remote

import fathian.ali.weatherapp.data.remote.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeatherInformation(
        @Query("q")
        city: String,
        @Query("appid")
        appid: String,
        @Query("units")
        units: String,
    ): WeatherDto

}