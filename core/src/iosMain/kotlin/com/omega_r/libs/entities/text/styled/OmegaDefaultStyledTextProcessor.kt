package com.omega_r.libs.entities.text.styled

import com.omega_r.libs.entities.resources.OmegaResourceExtractor

actual object OmegaDefaultStyledTextProcessor : OmegaStyledTextProcessor {
    actual override fun extract(
        entity: OmegaStyledText,
        resourceExtractor: OmegaResourceExtractor
    ): CharSequence? {
        return null
    }


}