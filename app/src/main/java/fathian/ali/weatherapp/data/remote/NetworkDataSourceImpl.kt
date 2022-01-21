package fathian.ali.weatherapp.data.remote

import fathian.ali.weatherapp.common.Constants
import fathian.ali.weatherapp.common.Either
import fathian.ali.weatherapp.data.remote.dto.WeatherDto
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(
    private val api: WeatherApi,
) : NetworkDataSource {

    override suspend fun getWeatherInformation(
        city: String,
        units: String,
    ): Either<WeatherDto, String?> {
        return try {
            val weatherDto = api.getWeatherInformation(
                city = city,
                units = units,
                appid = Constants.API_KEY_VALUE)
            Either.Success(weatherDto)
        } catch (e: HttpException) {
            Either.Error(error = e.localizedMessage)
        } catch (e: IOException) {
            Either.Error(error = e.localizedMessage)
        } catch (e: Exception) {
            Either.Error(error = e.localizedMessage)
        }
    }
}