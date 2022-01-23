package fathian.ali.weatherapp.data.remote

import fathian.ali.weatherapp.common.Constants
import fathian.ali.weatherapp.common.Either
import fathian.ali.weatherapp.data.remote.dto.WeatherDto
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(
    private val api: WeatherApi,
) : NetworkDataSource {

    override suspend fun getWeatherInformation(
        city: String,
        units: String,
    ): Either<WeatherDto, String?> {
        return try {
            val result = api.getWeatherInformation(
                city = city,
                units = units,
                appid = Constants.API_KEY_VALUE)
            Either.Success(result)
        } catch (e: Exception) {
            Either.Error(error = e.localizedMessage)
        }
    }
}