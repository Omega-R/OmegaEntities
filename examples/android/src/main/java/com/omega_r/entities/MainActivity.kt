package com.omega_r.entities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.omega_r.libs.entities.text.Text
import com.omega_r.libs.entities.text.setText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text = Text.from("ASD")
        textview_test.setText(text)
    }

}