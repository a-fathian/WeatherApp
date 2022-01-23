package fathian.ali.weatherapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fathian.ali.weatherapp.common.Either
import fathian.ali.weatherapp.domain.entity.WeatherData
import fathian.ali.weatherapp.domain.use_case.GetWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
) : ViewModel() {

    private var _weather = MutableStateFlow(WeatherData.Default)
    val weather = _weather.asStateFlow()

    private var _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    fun getWeather(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            val result = getWeatherUseCase(city)
            _loading.value = false
            when (result) {
                is Either.Success -> {
                    _weather.value = result.data
                }
                is Either.Error -> {
                    result.error?.let { error ->
                        _error.emit(error)
                    }
                }
            }
        }
    }
}