package com.omega_r.entities

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.omega_r.libs.entities.images.Image

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val image: Image = object: Image {
            override fun getDrawable(context: Context): Drawable? {
                return null
            }

        }
    }

}