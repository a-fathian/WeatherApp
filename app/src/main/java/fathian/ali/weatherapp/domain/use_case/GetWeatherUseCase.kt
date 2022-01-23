package fathian.ali.weatherapp.domain.use_case

import fathian.ali.weatherapp.common.Either
import fathian.ali.weatherapp.domain.entity.WeatherData
import fathian.ali.weatherapp.domain.repository.WeatherRepository

class GetWeatherUseCaseImpl(
    private val repository: WeatherRepository,
) : GetWeatherUseCase {

    override suspend fun invoke(city: String): Either<WeatherData, String?> {
        return repository.getWeather(city)
    }
}

interface GetWeatherUseCase {
    suspend operator fun invoke(city: String): Either<WeatherData, String?>
}