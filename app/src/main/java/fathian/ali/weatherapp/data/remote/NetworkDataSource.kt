package fathian.ali.weatherapp.data.remote

import fathian.ali.weatherapp.common.Either

interface NetworkDataSource {

    /**
     * get
     */
    suspend fun getWeatherInformation(): Either<String, String?>
}