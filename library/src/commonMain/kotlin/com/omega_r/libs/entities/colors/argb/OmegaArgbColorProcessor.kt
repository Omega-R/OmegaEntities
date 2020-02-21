package com.omega_r.libs.entities.colors.argb

import com.omega_r.libs.entities.colors.OmegaColorProcessor
import com.omega_r.libs.entities.resources.OmegaResourceExtractor

object OmegaArgbColorProcessor : OmegaColorProcessor<OmegaArgbColor> {

    override fun OmegaArgbColor.getColorInt(extractor: OmegaResourceExtractor): Int {
        return (alpha shl 24) or (red shl 16) or (green shl 8) or blue
    }

}