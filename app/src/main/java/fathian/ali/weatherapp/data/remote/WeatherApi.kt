package fathian.ali.weatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApi {
    @GET("")
    suspend fun getWeatherInformation(@Path("city") city: String): String
}