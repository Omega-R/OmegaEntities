package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.text.processor.OmegaTextProcessor
import com.omega_r.libs.entities.text.processor.TextProcessorsHolder

data class OmegaStringText(val value: String?) : OmegaText {

    companion object {
        init {
            TextProcessorsHolder.addProcessor(OmegaStringText::class, OmegaStringTextProcessor())
        }
    }

    class OmegaStringTextProcessor : OmegaTextProcessor<OmegaStringText> {

        override fun OmegaStringText.extract(): CharSequence? = value

    }

}