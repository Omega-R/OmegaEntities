package com.omega_r.entities

import android.app.Application
import com.omega_r.libs.entities.text.OmegaResourceTextProcessor
import com.omega_r.libs.entities.text.OmegaResourceText
import com.omega_r.libs.entities.text.processor.OmegaTextProcessors

class OEApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        OmegaTextProcessors.addProcessor(
            OmegaResourceText::class.simpleName!!,
            OmegaResourceTextProcessor(resources)
        )

    }
}