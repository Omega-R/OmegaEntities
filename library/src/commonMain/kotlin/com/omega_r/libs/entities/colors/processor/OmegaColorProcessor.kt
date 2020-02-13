package com.omega_r.libs.entities.colors.processor

import com.omega_r.libs.entities.OmegaProcessor
import com.omega_r.libs.entities.colors.OmegaColor

interface OmegaColorProcessor<T : OmegaColor> : OmegaProcessor<T, Int?> {

    override fun T.extract(): Int?

}