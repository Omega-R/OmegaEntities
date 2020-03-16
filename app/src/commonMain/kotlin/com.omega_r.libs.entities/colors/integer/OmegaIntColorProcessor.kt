package com.omega_r.libs.entities.colors.integer

import com.omega_r.libs.entities.colors.OmegaColorProcessor
import com.omega_r.libs.entities.resources.OmegaResourceExtractor

object OmegaIntColorProcessor : OmegaColorProcessor<OmegaIntColor> {

    override fun getColorInt(entity: OmegaIntColor, extractor: OmegaResourceExtractor): Int = entity.colorInt

}