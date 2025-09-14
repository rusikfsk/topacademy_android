package com.example.topacademy_android.feature.calculator.presentation

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.topacademy_android.R
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalculatorActivity : AppCompatActivity() {

    private val vm: CalculatorViewModel by viewModel()
    private lateinit var result: TextView
    private val expr = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calculator)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_calculator)
        toolbar.setNavigationOnClickListener { finish() }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.calculator_main)) { v, insets ->
            val s = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(s.left, s.top, s.right, s.bottom)
            insets
        }

        result = findViewById(R.id.textResult)
        setupDigits()
        setupOps()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.state.collect { st ->
                    when (st) {
                        is CalcState.Idle -> Unit
                        is CalcState.Success -> result.text = st.value
                        is CalcState.Error -> result.text = st.message
                    }
                }
            }
        }
    }

    private fun setupDigits() {
        val ids = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )
        ids.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                expr.append((it as Button).text)
                result.text = expr.toString()
                vm.reset()
            }
        }
    }

    private fun setupOps() {
        findViewById<Button>(R.id.btnPlus).setOnClickListener { appendOp(getString(R.string.btn_add)) }
        findViewById<Button>(R.id.btnMinus).setOnClickListener { appendOp(getString(R.string.btn_subtract)) }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { appendOp(getString(R.string.btn_multiply)) }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { appendOp(getString(R.string.btn_divide)) }
        findViewById<Button>(R.id.btnEquals).setOnClickListener { vm.evaluate(expr.toString()) }
        findViewById<Button>(R.id.btnClear).setOnClickListener {
            expr.clear()
            result.text = getString(R.string.initial_result)
            vm.reset()
        }
    }

    private fun appendOp(op: String) {
        if (expr.isNotEmpty()) {
            expr.append(op)
            result.text = expr.toString()
            vm.reset()
        }
    }
}
