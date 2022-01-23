package fathian.ali.weatherapp.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import dagger.hilt.android.AndroidEntryPoint
import fathian.ali.weatherapp.R
import fathian.ali.weatherapp.data.local.Preference
import fathian.ali.weatherapp.databinding.DialogUnitsBinding
import fathian.ali.weatherapp.databinding.FragmentMainBinding
import javax.inject.Inject

@AndroidEntryPoint
class UnitsDialog : DialogFragment() {

    @Inject
    lateinit var preference: Preference

    private var _binding: DialogUnitsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DialogUnitsBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.dialogCelsius.setOnClickListener {
            preference.storeString("unit", "metric")
            dismiss()
        }
        binding.dialogFahrenheit.setOnClickListener {
            preference.storeString("unit", "imperial")
            dismiss()
        }
    }
}