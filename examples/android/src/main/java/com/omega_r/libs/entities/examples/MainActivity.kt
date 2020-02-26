package com.omega_r.libs.entities.examples

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.omega_r.libs.entities.files.OmegaFile
import com.omega_r.libs.entities.files.OmegaFileProcessorsHolder
import com.omega_r.libs.entities.files.url.OmegaUrlFile
import com.omega_r.libs.entities.files.url.OmegaUrlFileProcessor
import com.omega_r.libs.entities.files.url.from
import io.ktor.util.asStream
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        OmegaFileProcessorsHolder.Default.addProcessor(OmegaUrlFile::class, OmegaUrlFileProcessor())

        val file: OmegaFile = OmegaFile.from("https://dejagerart.com/wp-content/uploads/2018/09/Test-Logo-Circle-black-transparent.png")

        GlobalScope.launch {
            val isExist = file.isExist()
            if(file.isExist() == true) {
                val input = file.getInput()
                input?.let {
                    val bitmap = BitmapFactory.decodeStream(it.asStream())
                    val drawable = BitmapDrawable(bitmap)
                    withContext(Dispatchers.Main) {
                        layout_container.background = drawable
                    }
                }
            }
        }
    }

}