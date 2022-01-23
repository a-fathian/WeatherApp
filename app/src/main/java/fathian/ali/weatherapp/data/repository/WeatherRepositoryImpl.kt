package fathian.ali.weatherapp.data.repository

import fathian.ali.weatherapp.common.Constants
import fathian.ali.weatherapp.common.Either
import fathian.ali.weatherapp.data.local.Preference
import fathian.ali.weatherapp.data.local.Units
import fathian.ali.weatherapp.data.remote.NetworkDataSource
import fathian.ali.weatherapp.data.remote.dto.toWeatherData
import fathian.ali.weatherapp.domain.entity.WeatherData
import fathian.ali.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val network: NetworkDataSource,
    private val preference: Preference
) : WeatherRepository {

    override suspend fun getWeather(city: String): Either<WeatherData, String?> {
        return when (val weatherDto =
            network.getWeatherInformation(city, preference.getString(Constants.KEY_UNIT))) {
            is Either.Success -> {
                Either.Success(
                    weatherDto.data.toWeatherData(
                        unit = Units.getByName(
                            preference.getString(
                                Constants.KEY_UNIT
                            )
                        )
                    )
                )
            }
            is Either.Error -> {
                Either.Error(weatherDto.error)
            }
        }
    }

    override suspend fun getString(key: String): Units {
        return Units.getByName(preference.getString(key))
    }

    override suspend fun storeString(key: String, value: Units) {
        preference.storeString(key, value.name)
    }
}
