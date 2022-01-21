package fathian.ali.weatherapp.data.repository

import fathian.ali.weatherapp.common.Either
import fathian.ali.weatherapp.data.remote.NetworkDataSource
import fathian.ali.weatherapp.data.remote.dto.toWeatherData
import fathian.ali.weatherapp.domain.entity.WeatherData
import fathian.ali.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val network: NetworkDataSource,
) : WeatherRepository {

    override suspend fun getWeather(city: String, units: String): Either<WeatherData, String?> {
        return when (val weatherDto = network.getWeatherInformation(city, units)) {
            is Either.Success -> {
                Either.Success(weatherDto.data.toWeatherData())
            }
            is Either.Error -> {
                Either.Error(weatherDto.error)
            }
        }
    }
}
