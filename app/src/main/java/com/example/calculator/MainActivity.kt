package com.example.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var tvDisplay: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tvDisplay)
        val root = findViewById<LinearLayout>(R.id.root)
        registerButtonListeners(root)
    }

    private fun registerButtonListeners(parentLayout: LinearLayout) {
        for (i in 0 until parentLayout.childCount) {
            val view = parentLayout.getChildAt(i)

            // recurse if layout is found
            if (view is LinearLayout)
                registerButtonListeners(view)
            else
                view.setOnClickListener(this)
        }
    }

    override fun onClick(view: View?) {
        val button = view as Button
        val buttonText = button.text.toString()

        when (button.id) {
            R.id.btnClear -> onClear()
            R.id.btnAdd, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide -> onOperator(buttonText)
            R.id.btnBackspace -> onBackspace()
            R.id.btnDP -> onDecimalPoint()
            R.id.btnEqual -> onEqual()
            R.id.btnNegate -> onNegate()
            else -> onDigit(buttonText.toInt())
        }
    }

    private fun onClear() {
        tvDisplay?.text = ""
    }

    private fun onOperator(operator: String) {

    }

    private fun onBackspace() {
        val displayText = tvDisplay?.text.toString()
        if (displayText.isNotEmpty()) {
            tvDisplay?.text = displayText.dropLast(1)
        }
    }

    private fun onDecimalPoint() {

    }

    private fun onEqual() {

    }

    private fun onNegate() {

    }

    private fun onDigit(digit: Int) {
        val displayText = tvDisplay?.text.toString()
        tvDisplay?.append(digit.toString())
    }
}