package com.example.topacademy_android.feature.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.topacademy_android.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonWeather.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToWeather()
            findNavController().navigate(action)
        }
        binding.buttonCalculator.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToCalculator()
            findNavController().navigate(action)
        }
        binding.buttonCars.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToCars()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
