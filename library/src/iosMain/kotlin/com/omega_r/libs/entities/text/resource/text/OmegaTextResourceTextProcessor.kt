package com.omega_r.libs.entities.text.resource.text

import com.omega_r.libs.entities.resources.OmegaResource
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import com.omega_r.libs.entities.text.resource.OmegaResourceTextProcessor

actual object OmegaTextResourceTextProcessor :
    OmegaResourceTextProcessor<OmegaTextResourceText, OmegaResource.Text>() {
    override fun extract(entity: OmegaTextResourceText, resourceExtractor: OmegaResourceExtractor): CharSequence? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun extractWithArgs(
        entity: OmegaTextResourceText,
        formatArgs: Array<out Any>,
        resourceExtractor: OmegaResourceExtractor
    ): CharSequence? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}