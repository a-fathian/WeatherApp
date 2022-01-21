package fathian.ali.weatherapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import fathian.ali.weatherapp.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MainFragment : BaseFragment() {

    override fun observers() {
        lifecycleScope.launchWhenStarted {
            viewModel.weather.collectLatest { weather ->
                binding.tvTemperature.text = weather.temp.toString()
            }
        }
    }

    override fun listeners() {
        binding.btnSearch.setOnClickListener {
            viewModel.getWeather(binding.etSearchCity.text.toString())
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBackPressed() {
        activity?.finish()
    }
}