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

        if (tvDisplay?.text.toString() == getString(R.string.Undefined)) {
            tvDisplay?.text = ""
        }

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
        if (tvDisplay?.text.isNullOrEmpty() && operator == "-")
            tvDisplay?.append("-")
        else if (!containsOperator() && isLastDigit())
            tvDisplay?.append(operator)
        else {
            onEqual()
            if (!containsOperator() && isLastDigit())
                tvDisplay?.append(operator)
        }
    }

    private fun onBackspace() {
        val displayText = tvDisplay?.text.toString()
        if (displayText.isNotEmpty()) {
            tvDisplay?.text = displayText.dropLast(1)
        }
    }

    private fun onDecimalPoint() {
        val displayText = tvDisplay?.text.toString()
        val dpCount = displayText.count {it == '.'}

        if (isLastDigit()) {
            if ((!containsOperator() && dpCount == 0) || (containsOperator() && dpCount < 2)) {
                tvDisplay?.append(".")
            }
        }
    }

    private fun onEqual() {
        val displayText = tvDisplay?.text.toString()

        val negativeCount = displayText.count { it == '-' }

        if (containsOperator() && isLastDigit()) {
            if (displayText.contains("+")) {
                val operands = getOperands("+")
                val answer = operands[0] + operands[1]
                tvDisplay?.text = answer.toString()
            }
            else if (displayText.contains("*")) {
                val operands = getOperands("*")
                val answer = operands[0] * operands[1]
                tvDisplay?.text = answer.toString()
            }
            else if (displayText.contains("/")) {
                val operands = getOperands("/")

                if (operands[1] == 0.0)
                    tvDisplay?.text = getString(R.string.Undefined)
                else {
                    val answer = operands[0] / operands[1]
                    tvDisplay?.text = answer.toString()
                }
            }
            else {
                val operands = getOperands("-")
                val answer = operands[0] - operands[1]
                tvDisplay?.text = answer.toString()
            }
        }
    }

    private fun getOperands(operator: String) : List<Double> {
        var displayText = tvDisplay?.text.toString()
        var stringOperands: List<String>

        if (operator == "-" && hasNegativePrefix()) {
            displayText = displayText.removePrefix("-")
            val operands = displayText.split(operator)
            stringOperands =  listOf("-" + operands[0], operands[1])
        }
        else {
            stringOperands = displayText.split(operator)
        }

        return listOf(stringOperands[0].toDouble(), stringOperands[1].toDouble())
    }

    private fun onNegate() {
        val displayText = tvDisplay?.text.toString()

       if (!containsOperator() && displayText.isNotEmpty()) {
           if (hasNegativePrefix()) {
               tvDisplay?.text = displayText.removePrefix("-")
           }
           else {
               tvDisplay?.text = "-" + displayText
           }
       }
    }

    private fun onDigit(digit: Int) {
        tvDisplay?.append(digit.toString())
    }

    private fun isLastDigit() : Boolean {
        return tvDisplay?.text?.lastOrNull()?.isDigit() ?: false
    }

    private fun hasNegativePrefix() : Boolean {
        val displayText = tvDisplay?.text.toString()
        return displayText.isNotEmpty() && displayText.elementAt(0) == '-'
    }

    private fun containsOperator() : Boolean {
        var displayText = tvDisplay?.text.toString()

        if (hasNegativePrefix())
            displayText = displayText.removePrefix("-")

        return displayText.contains("+") || displayText.contains("-")
                || displayText.contains("*") || displayText.contains("/")
    }
}