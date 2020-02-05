package com.omega_r.entities

import android.app.Application
import com.omega_r.libs.entities.text.IntResTextProcessor
import com.omega_r.libs.entities.text.ResourceText
import com.omega_r.libs.entities.text.processor.TextProcessors

class OEApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        TextProcessors.current = TextProcessors.default.apply {
            addProcessor(ResourceText::class, IntResTextProcessor(resources))
        }
    }
}