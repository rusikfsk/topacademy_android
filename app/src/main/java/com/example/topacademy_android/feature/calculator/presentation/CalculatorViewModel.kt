package com.example.topacademy_android.feature.calculator.presentation

import androidx.lifecycle.ViewModel
import com.example.topacademy_android.feature.calculator.domain.usecase.EvaluateExpressionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class CalcState {
    data object Idle : CalcState()
    data class Success(val value: String) : CalcState()
    data class Error(val message: String) : CalcState()
}

class CalculatorViewModel(
    private val evaluateUseCase: EvaluateExpressionUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<CalcState>(CalcState.Idle)
    val state: StateFlow<CalcState> = _state

    fun evaluate(expr: String) {
        try {
            val v = evaluateUseCase(expr)
            val text = if (v % 1.0 == 0.0) v.toLong().toString() else v.toString()
            _state.value = CalcState.Success(text)
        } catch (_: Exception) {
            _state.value = CalcState.Error("NaN")
        }
    }

    fun reset() {
        _state.value = CalcState.Idle
    }
}
