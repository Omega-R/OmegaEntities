package com.omega_r.libs.entities.text.string

import com.omega_r.libs.entities.text.OmegaText
import com.omega_r.libs.entities.text.TextProcessorsHolder

data class OmegaStringText(val value: String?) : OmegaText {

    companion object {
        init {
            TextProcessorsHolder.addProcessor(
                OmegaStringText::class,
                OmegaStringTextProcessor()
            )
        }
    }

}