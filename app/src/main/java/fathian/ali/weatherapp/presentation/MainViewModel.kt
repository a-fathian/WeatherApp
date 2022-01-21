package fathian.ali.weatherapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fathian.ali.weatherapp.common.Either
import fathian.ali.weatherapp.domain.entity.WeatherData
import fathian.ali.weatherapp.domain.use_case.GetWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
) : ViewModel() {

    private var _weather = MutableStateFlow(WeatherData.Default)
    val weather = _weather.asStateFlow()

    fun getWeather(city: String, units: String = "metric") {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getWeatherUseCase(city, units)) {
                is Either.Success -> {
                    _weather.value = result.data
                }
                is Either.Error -> {

                }
            }
        }
    }
}