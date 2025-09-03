package com.example.topacademy_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WeatherDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_weather_detail)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_weather)
        supportActionBar?.subtitle = getString(R.string.weather_location_moscow)
        toolbar.setNavigationOnClickListener { finish() }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.toolbar)) { v, insets ->
            val s = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(s.left, s.top, s.right, v.paddingBottom)
            insets
        }

        val item = readExtra()

        val icon = findViewById<ImageView>(R.id.icon)
        val date = findViewById<TextView>(R.id.date)
        val desc = findViewById<TextView>(R.id.desc)
        val temp = findViewById<TextView>(R.id.temp)
        val extra = findViewById<TextView>(R.id.extra)

        date.text = item.date
        desc.text = WeatherUi.label(this, item.weather)
        temp.text = getString(R.string.temp_range_format, item.tMin, item.tMax)
        extra.text = getString(R.string.wind_format, item.wind)
        icon.setImageResource(WeatherUi.iconFor(item.weather))
    }

    private fun readExtra(): DailyItem {
        val date = intent.getStringExtra("date") ?: ""
        val weather = intent.getStringExtra("weather") ?: ""
        val tMax = intent.getIntExtra("tMax", 0)
        val tMin = intent.getIntExtra("tMin", 0)
        val wind = intent.getIntExtra("wind", 0)
        return DailyItem(date, weather, tMax, tMin, wind)
    }

    companion object {
        fun intent(context: Context, item: DailyItem): Intent {
            return Intent(context, WeatherDetailActivity::class.java)
                .putExtra("date", item.date)
                .putExtra("weather", item.weather)
                .putExtra("tMax", item.tMax)
                .putExtra("tMin", item.tMin)
                .putExtra("wind", item.wind)
        }
    }
}
