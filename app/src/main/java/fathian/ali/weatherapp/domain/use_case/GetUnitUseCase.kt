package fathian.ali.weatherapp.domain.use_case

import fathian.ali.weatherapp.data.local.Units
import fathian.ali.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetUnitUseCaseImpl @Inject constructor(
    private val repository: WeatherRepository
): GetUnitUseCase {

    override suspend fun invoke(key: String): Units {
        return repository.getString(key)
    }
}

interface GetUnitUseCase {
    suspend operator fun invoke(key: String) : Units
}