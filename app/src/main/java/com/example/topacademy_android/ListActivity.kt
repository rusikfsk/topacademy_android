package com.example.topacademy_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topacademy_android.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {

    private lateinit var vb: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityListBinding.inflate(layoutInflater)
        setContentView(vb.root)
        setSupportActionBar(vb.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_list)
        vb.toolbar.setNavigationOnClickListener { finish() }

        val cars = listOf(
            Car("Toyota", "Camry", 2020, "Reliable family sedan", 26_000, R.drawable.ic_car),
            Car("Lixiang", "L9", 2024, "Crossover", 40_000, R.drawable.ic_car),
            Car("Tesla", "Model 3", 2022, "Electric with Autopilot", 48_000, R.drawable.ic_car),
            Car("Audi", "A4", 2019, "Comfort and tech", 35_000, R.drawable.ic_car),
            Car("Kia", "Sportage", 2021, "Compact crossover", 27_000, R.drawable.ic_car),
            Car("Mercedes", "C-Class", 2020, "Luxury compact sedan", 42_000, R.drawable.ic_car)
        )

        vb.recycler.layoutManager = LinearLayoutManager(this)
        vb.recycler.adapter = CarAdapter(cars)
    }
}
