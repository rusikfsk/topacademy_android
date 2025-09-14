package com.example.topacademy_android

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import com.example.topacademy_android.feature.calculator.presentation.CalculatorActivity
import com.example.topacademy_android.feature.cars.presentation.ListActivity
import com.example.topacademy_android.feature.weather.presentation.WeatherActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_home)
        toolbar.setNavigationOnClickListener { finish() }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home_root)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonCalc = findViewById<Button>(R.id.buttonCalc)
        val buttonList = findViewById<Button>(R.id.buttonList)
        val buttonWeather = findViewById<Button>(R.id.buttonWeather)

        buttonCalc.setOnClickListener {
            startActivity(Intent(this, CalculatorActivity::class.java))
        }

        buttonList.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }

        buttonWeather.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    com.example.topacademy_android.feature.weather.presentation.WeatherActivity::class.java
                )
            )
        }
    }
}
