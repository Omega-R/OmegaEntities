package com.omega_r.entities

import android.app.Application
import com.omega_r.libs.entities.text.resource.OmegaResourceText
import com.omega_r.libs.entities.text.styled.OmegaStyledText
import com.omega_r.libs.entities.text.resource.OmegaResourceTextProcessor
import com.omega_r.libs.entities.text.styled.OmegaStyledTextProcessor
import com.omega_r.libs.entities.text.TextProcessorsHolder

class OEApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        TextProcessorsHolder.addProcessors(
            OmegaResourceText::class to OmegaResourceTextProcessor(resources),
            OmegaStyledText::class to OmegaStyledTextProcessor
        )
    }

}