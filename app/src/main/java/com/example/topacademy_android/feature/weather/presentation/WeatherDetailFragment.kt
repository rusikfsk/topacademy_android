package com.example.topacademy_android.feature.weather.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.topacademy_android.R
import com.example.topacademy_android.databinding.FragmentWeatherDetailBinding

class WeatherDetailFragment : Fragment() {

    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding get() = _binding!!

    private val args: WeatherDetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWeatherDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.icon.setImageResource(WeatherUi.iconFor(args.weather))
        binding.date.text = args.date
        binding.weather.text = WeatherUi.labelFor(requireContext(), args.weather)
        binding.tMax.text = getString(R.string.temp_c, args.tMax)
        binding.tMin.text = getString(R.string.temp_c, args.tMin)
        binding.wind.text = getString(R.string.wind_ms, args.wind)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
