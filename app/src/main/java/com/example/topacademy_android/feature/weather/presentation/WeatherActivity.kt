package com.example.topacademy_android.feature.weather.presentation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.topacademy_android.R
import com.example.topacademy_android.feature.weather.domain.model.DailyWeather
import com.example.topacademy_android.feature.weather.presentation.adapter.WeatherAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale
import kotlinx.coroutines.launch

class WeatherActivity : AppCompatActivity() {

    private val vm: WeatherViewModel by viewModel()
    private val adapter = WeatherAdapter { openDetail(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_weather)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_weather)
        supportActionBar?.subtitle = getString(R.string.weather_location_moscow)
        toolbar.setNavigationOnClickListener { finish() }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.weather_main)) { v, insets ->
            val s = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(s.left, s.top, s.right, s.bottom)
            insets
        }

        val recycler = findViewById<RecyclerView>(R.id.weatherRecycler)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        val stateText = findViewById<TextView>(R.id.stateText)
        val swipe = findViewById<SwipeRefreshLayout>(R.id.swipe)
        swipe.setOnRefreshListener { load() }

        observeState(stateText, swipe)
        load()
    }

    private fun observeState(stateText: TextView, swipe: SwipeRefreshLayout) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.state.collect { s ->
                    when (s) {
                        is WeatherState.Loading -> {
                            stateText.visibility = View.VISIBLE
                            stateText.text = getString(R.string.weather_loading)
                            swipe.isRefreshing = true
                        }
                        is WeatherState.Data -> {
                            adapter.submitList(s.items)
                            val empty = s.items.isEmpty()
                            stateText.visibility = if (empty) View.VISIBLE else View.GONE
                            stateText.text = if (empty) getString(R.string.weather_empty) else ""
                            swipe.isRefreshing = false
                        }
                        is WeatherState.Error -> {
                            stateText.visibility = View.VISIBLE
                            stateText.text = getString(R.string.weather_error_reason, s.message)
                            swipe.isRefreshing = false
                        }
                    }
                }
            }
        }
    }

    private fun load() {
        vm.load(
            lon = 37.6173,
            lat = 55.7558,
            datePattern = getString(R.string.date_pattern),
            locale = Locale.getDefault()
        )
    }

    private fun openDetail(item: DailyWeather) {
        startActivity(WeatherDetailActivity.intent(this, item))
    }
}
