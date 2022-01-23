package fathian.ali.weatherapp.domain.use_case

import fathian.ali.weatherapp.data.local.Units
import fathian.ali.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class SetUnitUseCaseImpl @Inject constructor(
    private val repository: WeatherRepository
) : SetUnitUseCase {

    override suspend fun invoke(key: String, value: Units) {
        repository.storeString(key, value)
    }
}

interface SetUnitUseCase {
    suspend operator fun invoke(key: String, value: Units)
}