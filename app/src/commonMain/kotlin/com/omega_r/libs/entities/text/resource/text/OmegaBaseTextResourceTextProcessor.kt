package com.omega_r.libs.entities.text.resource.text

import com.omega_r.libs.entities.resources.OmegaResource
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import com.omega_r.libs.entities.text.resource.OmegaResourceTextProcessor

abstract class OmegaBaseTextResourceTextProcessor : OmegaResourceTextProcessor<OmegaTextResourceText, OmegaResource.Text>() {

    override fun extractWithoutArgs(entity: OmegaTextResourceText, extractor: OmegaResourceExtractor): CharSequence? {
        return extractor.getCharSequence(entity.resource)
    }

}