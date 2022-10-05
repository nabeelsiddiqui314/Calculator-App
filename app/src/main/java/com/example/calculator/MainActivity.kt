package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        Toast.makeText(this, "button pressed", Toast.LENGTH_SHORT).show()
    }
}