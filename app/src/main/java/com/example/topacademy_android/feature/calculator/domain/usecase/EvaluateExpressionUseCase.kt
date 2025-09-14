package com.example.topacademy_android.feature.calculator.domain.usecase

import com.example.topacademy_android.feature.calculator.domain.repository.CalculatorRepository

class EvaluateExpressionUseCase(private val repository: CalculatorRepository) {
    operator fun invoke(expression: String): Double = repository.evaluate(expression)
}
