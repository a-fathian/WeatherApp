package fathian.ali.weatherapp.presentation.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import fathian.ali.weatherapp.R
import fathian.ali.weatherapp.databinding.FragmentMainBinding
import fathian.ali.weatherapp.domain.entity.WeatherData
import fathian.ali.weatherapp.domain.entity.WeatherItem
import fathian.ali.weatherapp.presentation.BaseFragment
import fathian.ali.weatherapp.presentation.unit_dialog.UnitsDialog
import fathian.ali.weatherapp.util.hideKeyboard
import fathian.ali.weatherapp.util.showGone
import kotlinx.coroutines.flow.collectLatest
import java.util.*


@AndroidEntryPoint
class MainFragment : BaseFragment(), UnitsDialog.OnUnitChangeListener {


    private val viewModel: MainViewModel by viewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        childFragmentManager.addFragmentOnAttachListener { fragmentManager, fragment ->
            if (fragment is UnitsDialog) {
                fragment.listener = this
            }
        }
    }

    override fun observers() {
        lifecycleScope.launchWhenStarted {
            viewModel.weather.collectLatest { weather ->
                binding.refresh.isRefreshing = false
                if (weather != WeatherData.Default) {
                    binding.tvTemperature.text = weather.temp
                    binding.tvCityName.text =
                        getString(R.string.city_country_name, weather.city, weather.country)
                    binding.tvFeelsLike.text = weather.feelsLike
                    binding.ivWeatherIcon.load(getString(R.string.weather_icon_url, weather.icon)) {
                        error(R.drawable.cloud)
                    }
                    binding.weatherDescription.text = weather.description
                    binding.feelsLikeText.visibility = View.VISIBLE
                    binding.recycler.adapter = MainAdapter()
                    (binding.recycler.adapter as MainAdapter).setItems(setItems(weather))
                    binding.recycler.scheduleLayoutAnimation()
                }
                viewModel.resetState()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.loading.collectLatest { loading ->
                binding.loading.root.showGone(loading)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.error.collectLatest { error ->
                binding.refresh.isRefreshing = false
                Snackbar.make(
                    view ?: return@collectLatest,
                    error,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setItems(weather: WeatherData): List<WeatherItem> {
        val wind = WeatherItem(
            R.drawable.wind,
            "Wind",
            weather.windSpeed
        )
        val humidity = WeatherItem(
            R.drawable.humidity,
            "Humidity",
            weather.humidity
        )
        val pressure = WeatherItem(
            R.drawable.barometer,
            "Pressure",
            weather.pressure
        )
        val visibility = WeatherItem(
            R.drawable.telescope,
            "Visibility",
            weather.visibility
        )
        val cloudiness = WeatherItem(
            R.drawable.cloud,
            "Cloudiness",
            weather.cloudiness
        )
        val sunrise = WeatherItem(
            R.drawable.sunrise,
            "Sunrise",
            weather.sunrise
        )
        val sunset = WeatherItem(
            R.drawable.sunset,
            "Sunset",
            weather.sunset
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
            searchForCity()
        }
        binding.etSearchCity.setOnKeyListener { _, keyCode, keyEvent ->
            if ((keyEvent.action == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                searchForCity()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        binding.refresh.setOnRefreshListener {
            searchForCity()
        }
    }

    private fun searchForCity() {
        if (binding.etSearchCity.text.isNotBlank()) {
            binding.etSearchCity.hideKeyboard()
            viewModel.getWeather(binding.etSearchCity.text.toString())
        } else {
            binding.refresh.isRefreshing = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_menu_units) {
            UnitsDialog().show(childFragmentManager, null)
            return true
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBackPressed() {
        activity?.finish()
    }

    override fun onUnitChanged() {
        if (binding.etSearchCity.text.isNotBlank())
            viewModel.getWeather(binding.etSearchCity.text.toString())
    }
}