package com.omega_r.libs.entities.text.processor

import com.omega_r.libs.entities.OmegaProcessor
import com.omega_r.libs.entities.text.OmegaText

interface OmegaTextProcessor<T : OmegaText> : OmegaProcessor<T, CharSequence?> {

    override fun T.extract(): CharSequence?

}