package fathian.ali.weatherapp.presentation.unit_dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fathian.ali.weatherapp.common.Constants
import fathian.ali.weatherapp.data.local.Units
import fathian.ali.weatherapp.domain.entity.WeatherData
import fathian.ali.weatherapp.domain.use_case.SetUnitUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class UnitDialogViewModel @Inject constructor(
    private val setUnitUseCase: SetUnitUseCase,
) : ViewModel() {

    private var _unitSet = MutableStateFlow(false)
    val unitSet = _unitSet.asStateFlow()

    fun setUnit(value: Units) {
        viewModelScope.launch(Dispatchers.IO) {
            setUnitUseCase(Constants.KEY_UNIT, value)
            _unitSet.emit(true)
        }
    }

}