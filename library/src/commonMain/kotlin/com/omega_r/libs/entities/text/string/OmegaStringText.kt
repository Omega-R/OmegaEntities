package com.omega_r.libs.entities.text.string

import com.omega_r.libs.entities.text.OmegaText
import com.omega_r.libs.entities.text.DefaultTextProcessorsHolder

data class OmegaStringText(val value: String?) : OmegaText {

    companion object {
        init {
            DefaultTextProcessorsHolder.addProcessor(
                OmegaStringText::class,
                OmegaStringTextProcessor()
            )
        }
    }

}