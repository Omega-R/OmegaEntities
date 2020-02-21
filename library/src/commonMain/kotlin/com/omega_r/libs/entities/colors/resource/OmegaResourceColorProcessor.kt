package com.omega_r.libs.entities.colors.resource

import com.omega_r.libs.entities.colors.OmegaColorProcessor
import com.omega_r.libs.entities.resources.OmegaResourceExtractor

object OmegaResourceColorProcessor: OmegaColorProcessor<OmegaResourceColor> {

    override fun getColorInt(entity: OmegaResourceColor, extractor: OmegaResourceExtractor): Int {
        return  extractor.getColorInt(entity.resource)
    }

}