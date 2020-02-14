package com.omega_r.entities

import android.app.Application
import com.omega_r.libs.entities.text.OmegaResourceText
import com.omega_r.libs.entities.text.StyledOmegaText
import com.omega_r.libs.entities.text.processor.OmegaResourceTextProcessor
import com.omega_r.libs.entities.text.processor.OmegaStyledTextProcessor
import com.omega_r.libs.entities.text.processor.TextProcessorsHolder

class OEApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        TextProcessorsHolder.addProcessors(
                OmegaResourceText::class to OmegaResourceTextProcessor(resources),
                StyledOmegaText::class to OmegaStyledTextProcessor(this)
        )
    }

}