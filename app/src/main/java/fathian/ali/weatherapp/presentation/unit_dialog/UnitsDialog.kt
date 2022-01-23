package fathian.ali.weatherapp.presentation.unit_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import fathian.ali.weatherapp.data.local.Units
import fathian.ali.weatherapp.databinding.DialogUnitsBinding
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class UnitsDialog : DialogFragment() {

    private val viewModel: UnitDialogViewModel by viewModels()

    private var _binding: DialogUnitsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.unitSet.collectLatest { set ->
                if (set)
                    dismiss()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DialogUnitsBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.dialogCelsius.setOnClickListener {
            viewModel.setUnit(Units.METRIC)
        }
        binding.dialogFahrenheit.setOnClickListener {
            viewModel.setUnit(Units.IMPERIAL)
        }
    }
}