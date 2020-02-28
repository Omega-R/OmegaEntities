package com.omega_r.libs.entities.colors.argb

import com.omega_r.libs.entities.colors.OmegaColorProcessor
import com.omega_r.libs.entities.resources.OmegaResourceExtractor

object OmegaArgbColorProcessor : OmegaColorProcessor<OmegaArgbColor> {

    override fun getColorInt(entity: OmegaArgbColor, extractor: OmegaResourceExtractor): Int {
        return (entity.alpha shl 24) or (entity.red shl 16) or (entity.green shl 8) or entity.blue
    }

}