package com.omega_r.libs.entities.colors

import com.omega_r.libs.entities.processors.OmegaProcessor
import com.omega_r.libs.entities.resources.OmegaResourceExtractor

interface OmegaColorProcessor<T : OmegaColor> : OmegaProcessor<T> {

    fun getColorInt(entity: T, extractor: OmegaResourceExtractor): Int

}