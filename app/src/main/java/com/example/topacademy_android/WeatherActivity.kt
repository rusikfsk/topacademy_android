package com.example.topacademy_android

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherActivity : AppCompatActivity() {

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

        val state = findViewById<TextView>(R.id.stateText)
        val swipe = findViewById<SwipeRefreshLayout>(R.id.swipe)
        swipe.setOnRefreshListener { load(state, swipe) }

        state.visibility = View.VISIBLE
        state.text = getString(R.string.weather_loading)
        load(state, swipe)
    }

    private fun load(state: TextView, swipe: SwipeRefreshLayout) {
        lifecycleScope.launch {
            try {
                val res = SevenTimerService.api.civillight(37.6173, 55.7558)
                val series = res.dataseries ?: emptyList()
                val list = ArrayList<DailyItem>(series.size)
                val inFmt = SimpleDateFormat("yyyyMMdd", Locale.US)
                val outFmt = SimpleDateFormat(getString(R.string.date_pattern), Locale.getDefault())
                for (d in series) {
                    val label = try { outFmt.format(inFmt.parse(d.date.toString())!!) } catch (e: Exception) { d.date.toString() }
                    val wind = d.wind10m_max ?: 0
                    list.add(DailyItem(label, d.weather, d.temp2m.max, d.temp2m.min, wind))
                }
                adapter.submitList(list)
                state.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
                state.text = if (list.isEmpty()) getString(R.string.weather_empty) else ""
            } catch (e: Exception) {
                state.visibility = View.VISIBLE
                state.text = getString(R.string.weather_error_reason, e.localizedMessage ?: "unknown")
            } finally {
                swipe.isRefreshing = false
            }
        }
    }

    private fun openDetail(item: DailyItem) {
        startActivity(WeatherDetailActivity.intent(this, item))
    }
}
