package com.omega_r.libs.entities.colors.integer

import com.omega_r.libs.entities.colors.OmegaColorProcessor
import com.omega_r.libs.entities.colors.integer.OmegaIntColor
import com.omega_r.libs.entities.resources.OmegaResourceExtractor

object OmegaIntColorProcessor : OmegaColorProcessor<OmegaIntColor> {

    override fun OmegaIntColor.getColorInt(extractor: OmegaResourceExtractor) = colorInt

}