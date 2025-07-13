package com.example.topacademy_android

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.topacademy_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var timeOnCreate: Long = 0
    private var timeOnStart: Long = 0

    companion object {
        private const val ON_CREATE = "ON_CREATE"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        timeOnCreate = System.currentTimeMillis()
        Log.i(ON_CREATE, "Активити создана!")
    }

    override fun onStart() {
        super.onStart()
        timeOnStart = System.currentTimeMillis()
        Log.i("LIFECYCLE", "onStart вызван, разница с onCreate: ${timeOnStart - timeOnCreate} мс")
    }

    override fun onResume() {
        super.onResume()
        val timeOnResume = System.currentTimeMillis()
        Log.i("LIFECYCLE", "onResume вызван, разница с onStart: ${timeOnResume - timeOnStart} мс")
    }

    override fun onPause() {
        super.onPause()
        Log.i("LIFECYCLE", "onPause вызван")
    }

    override fun onStop() {
        super.onStop()
        Log.i("LIFECYCLE", "onStop вызван")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("LIFECYCLE", "onRestart вызван")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("LIFECYCLE", "onDestroy вызван")
    }
}
