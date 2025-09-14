package com.example.topacademy_android.feature.weather.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.topacademy_android.R
import com.example.topacademy_android.feature.weather.domain.model.DailyWeather

class WeatherDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_weather_detail)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_weather)
        toolbar.setNavigationOnClickListener { finish() }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detail_root)) { v, insets ->
            val s = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(s.left, s.top, s.right, s.bottom)
            insets
        }

        val date = intent.getStringExtra(EXTRA_DATE).orEmpty()
        val weather = intent.getStringExtra(EXTRA_WEATHER).orEmpty()
        val tMax = intent.getIntExtra(EXTRA_TMAX, 0)
        val tMin = intent.getIntExtra(EXTRA_TMIN, 0)
        val wind = intent.getIntExtra(EXTRA_WIND, 0)

        supportActionBar?.subtitle = date

        findViewById<ImageView>(R.id.icon).setImageResource(WeatherUi.iconFor(weather))
        findViewById<TextView>(R.id.date).text = date
        findViewById<TextView>(R.id.weather).text = WeatherUi.labelFor(this, weather)
        findViewById<TextView>(R.id.tMax).text = getString(R.string.temp_c, tMax)
        findViewById<TextView>(R.id.tMin).text = getString(R.string.temp_c, tMin)
        findViewById<TextView>(R.id.wind).text = getString(R.string.wind_ms, wind)
    }

    companion object {
        private const val EXTRA_DATE = "date"
        private const val EXTRA_WEATHER = "weather"
        private const val EXTRA_TMAX = "tMax"
        private const val EXTRA_TMIN = "tMin"
        private const val EXTRA_WIND = "wind"

        fun intent(context: Context, item: DailyWeather): Intent =
            Intent(context, WeatherDetailActivity::class.java).apply {
                putExtra(EXTRA_DATE, item.date)
                putExtra(EXTRA_WEATHER, item.weather)
                putExtra(EXTRA_TMAX, item.tMax)
                putExtra(EXTRA_TMIN, item.tMin)
                putExtra(EXTRA_WIND, item.wind)
            }
    }
}
