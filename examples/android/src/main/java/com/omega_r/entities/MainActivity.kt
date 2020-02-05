package com.omega_r.entities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_text_examples.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    TextExamplesActivity::class.java
                )
            )
        }
    }

}