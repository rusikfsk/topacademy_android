package com.example.topacademy_android.feature.calculator.domain.repository

interface CalculatorRepository {
    fun evaluate(expression: String): Double
}
