package com.omega_r.libs.entities.text.resource.plurals

import com.omega_r.libs.entities.resources.OmegaResource
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import com.omega_r.libs.entities.text.resource.OmegaResourceTextProcessor

abstract class OmegaBaseTextResourceTextProcessor : OmegaResourceTextProcessor<OmegaPluralsResourceText, OmegaResource.Plurals>() {

    override fun extractWithoutArgs(entity: OmegaPluralsResourceText, extractor: OmegaResourceExtractor): CharSequence? {
        return extractor.getPluralsChatSequence(entity.resource, entity.quantity)
    }

}