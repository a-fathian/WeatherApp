package fathian.ali.weatherapp.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import fathian.ali.weatherapp.R
import fathian.ali.weatherapp.data.local.Preference
import fathian.ali.weatherapp.databinding.FragmentMainBinding
import fathian.ali.weatherapp.domain.entity.WeatherData
import fathian.ali.weatherapp.domain.entity.WeatherItem
import fathian.ali.weatherapp.presentation.BaseFragment
import fathian.ali.weatherapp.util.safeNavigate
import fathian.ali.weatherapp.util.showGone
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : BaseFragment() {

    @Inject
    lateinit var preference: Preference

    override fun observers() {
        lifecycleScope.launchWhenStarted {
            viewModel.weather.collectLatest { weather ->
                if (weather != WeatherData.Default) {
                    val unit = preference.getString("unit", "metric")
                    val unitSign = if (unit == "metric") "°C" else "°F"
                    binding.tvTemperature.text =
                        getString(R.string.format_string, weather.temp.toString(), unitSign)
                    binding.tvCityName.text =
                        getString(R.string.city_country_name, weather.city, weather.country)
                    binding.tvFeelsLike.text = getString(R.string.format_string,
                        weather.feelsLike.toString(),
                        unitSign)
                    binding.ivWeatherIcon.load(getString(R.string.weather_icon_url, weather.icon)) {
                        error(R.drawable.cloud)
                    }
                    binding.weatherDescription.text = weather.description
                    binding.feelsLikeText.visibility = View.VISIBLE
                    binding.recycler.adapter = MainAdapter(setItems(weather))
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.loading.collectLatest { loading ->
                binding.loading.root.showGone(loading)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.error.collectLatest { error ->
                Snackbar.make(
                    view ?: return@collectLatest,
                    error,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setItems(weather: WeatherData): List<WeatherItem> {
        val unit = preference.getString("unit", "metric")
        val unitSign = if (unit == "metric") "°C" else "°F"
        val windSpeed = if (unit == "metric") {
            getString(R.string.format_string, (weather.windSpeed * 3.6).toString(), " km/h")
        } else {
            getString(R.string.format_string, weather.windSpeed.toString(), " mph")
        }
        val wind = WeatherItem(
            R.drawable.wind,
            "Wind",
            windSpeed
        )
        val humidityPercent = getString(R.string.format_string, weather.humidity.toString(), "%")
        val humidity = WeatherItem(
            R.drawable.humidity,
            "Humidity",
            humidityPercent
        )
        val pressure = WeatherItem(
            R.drawable.barometer,
            "Pressure",
            getString(R.string.format_string, (weather.pressure / 10).toString(), "kPa")
        )
        val visibility = WeatherItem(
            R.drawable.telescope,
            "Visibility",
            getString(R.string.format_string, (weather.visibility / 1000).toString(), "km")
        )
        val cloudiness = WeatherItem(
            R.drawable.cloud,
            "Cloudiness",
            getString(R.string.format_string, weather.cloudiness.toString(), "%")
        )
        val sunrise = WeatherItem(
            R.drawable.sunrise,
            "Sunrise",
            SimpleDateFormat("HH:mm", Locale.US).format(Date(weather.sunrise))
        )
        val sunset = WeatherItem(
            R.drawable.sunset,
            "Sunset",
            SimpleDateFormat("HH:mm", Locale.US).format(Date(weather.sunset))
        )
        return listOf(
            wind,
            humidity,
            pressure,
            visibility,
            cloudiness,
            sunrise,
            sunset
        )
    }

    override fun listeners() {
        binding.btnSearch.setOnClickListener {
//            binding.feelsLikeText.visibility = View.INVISIBLE
            viewModel.getWeather(binding.etSearchCity.text.toString(),
                preference.getString("unit", "imperial") ?: "imperial")
        }
        binding.btnUnits.setOnClickListener {
            safeNavigate(R.id.action_mainFragment_to_unitsDialog)
        }
    }

    private val viewModel: MainViewModel by viewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBackPressed() {
        activity?.finish()
    }
}