package com.omega_r.libs.entities.text.resource.text

import com.omega_r.libs.entities.resources.OmegaResource
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import com.omega_r.libs.entities.text.OmegaText
import com.omega_r.libs.entities.text.resource.OmegaResourceTextProcessor

actual object OmegaTextResourceTextProcessor: OmegaBaseTextResourceTextProcessor() {
    override fun extract(entity: OmegaTextResourceText, resourceExtractor: OmegaResourceExtractor): CharSequence? {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return null
    }

    override fun extractWithArgs(
        entity: OmegaTextResourceText,
        formatArgs: Array<out Any>,
        resourceExtractor: OmegaResourceExtractor
    ): CharSequence? {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return null
    }

    override fun extractWithoutArgs(
        entity: OmegaTextResourceText,
        extractor: OmegaResourceExtractor
    ): CharSequence? {
//        TODO("Not yet implemented")
        return null
    }
}