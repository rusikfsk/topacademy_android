package com.example.topacademy_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val buttonCalculator = findViewById<Button>(R.id.buttonCalculator)
        val buttonList = findViewById<Button>(R.id.buttonList)
        val buttonWeather = findViewById<Button>(R.id.buttonWeather)


        buttonCalculator.setOnClickListener {
            Log.d("HomeActivity", "Calculator button clicked")
            val intent = Intent(this, CalculatorActivity::class.java)
            startActivity(intent)
        }

        buttonList.setOnClickListener {
            Log.d("HomeActivity", "List button clicked")
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }

        buttonWeather.setOnClickListener {
            Log.d("HomeActivity", "Weather button clicked")
            val intent = Intent(this, WeatherActivity::class.java)
            startActivity(intent)
        }
    }
}
