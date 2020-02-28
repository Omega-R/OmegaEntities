package com.omega_r.libs.entities.text.styled

import com.omega_r.libs.entities.resources.OmegaResourceExtractor

expect object OmegaDefaultStyledTextProcessor : OmegaStyledTextProcessor {

    override fun extract(entity: OmegaStyledText, resourceExtractor: OmegaResourceExtractor): CharSequence?

}