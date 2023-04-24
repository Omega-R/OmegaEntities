package com.omega_r.libs.entities.examples

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.omega_r.entities.image.glide.OmegaGlideImageProcessorsHolder
import com.omega_r.libs.entities.examples.databinding.ActivityMainBinding
import com.omega_r.libs.entities.extensions.setBackground
import com.omega_r.libs.entities.files.OmegaFileProcessorsHolder
import com.omega_r.libs.entities.files.url.OmegaUrlFile
import com.omega_r.libs.entities.files.url.OmegaUrlFileProcessor
import com.omega_r.libs.entities.images.OmegaImage

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.buttonTextExamples.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    TextExamplesActivity::class.java
                )
            )
        }

        OmegaFileProcessorsHolder.Default.addProcessor(OmegaUrlFile::class, OmegaUrlFileProcessor())

        val image = OmegaImage.from("https://dejagerart.com/wp-content/uploads/2018/09/Test-Logo-Circle-black-transparent.png")
        OmegaGlideImageProcessorsHolder.setAsCurrentImagesProcessor()
        binding.layoutContainer.setBackground(image)
    }

}