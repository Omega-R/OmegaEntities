package com.omega_r.entities

import android.app.Application
import com.omega_r.libs.entities.text.IntResOmegaTextProcessor
import com.omega_r.libs.entities.text.ResourceOmegaText
import com.omega_r.libs.entities.text.processor.OmegaTextProcessors

class OEApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        OmegaTextProcessors.addProcessor(
            ResourceOmegaText::class.simpleName!!,
            IntResOmegaTextProcessor(resources)
        )

    }
}