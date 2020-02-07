package com.omega_r.libs.entities.text.processor

import com.omega_r.libs.entities.text.OmegaText

abstract class OmegaTextProcessor<T: OmegaText> {
    abstract fun T.extract(): String?
}