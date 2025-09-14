package com.example.topacademy_android.feature.calculator.data.repository

import com.example.topacademy_android.feature.calculator.domain.repository.CalculatorRepository
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorRepositoryImpl : CalculatorRepository {
    override fun evaluate(expression: String): Double {
        val normalized = expression
            .replace('×', '*')
            .replace('÷', '/')
            .replace('−', '-')
        return ExpressionBuilder(normalized).build().evaluate()
    }
}
