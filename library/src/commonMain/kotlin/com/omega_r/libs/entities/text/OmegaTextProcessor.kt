package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.processors.OmegaProcessor
import com.omega_r.libs.entities.resources.OmegaResourceExtractor

interface OmegaTextProcessor<T : OmegaText> : OmegaProcessor<T> {

    fun extract(entity: T, resourceExtractor: OmegaResourceExtractor): CharSequence?

}