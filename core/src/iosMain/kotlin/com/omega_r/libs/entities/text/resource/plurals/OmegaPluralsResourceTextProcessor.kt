package com.omega_r.libs.entities.text.resource.plurals

import com.omega_r.libs.entities.resources.OmegaResource
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import com.omega_r.libs.entities.text.resource.OmegaResourceTextProcessor

actual object OmegaPluralsResourceTextProcessor: OmegaBaseTextResourceTextProcessor() {

    override fun extract(entity: OmegaPluralsResourceText, resourceExtractor: OmegaResourceExtractor): CharSequence? {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return null
    }

    override fun extractWithArgs(
        entity: OmegaPluralsResourceText,
        formatArgs: Array<out Any>,
        resourceExtractor: OmegaResourceExtractor
    ): CharSequence? {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return null
    }

    override fun extractWithoutArgs(
        entity: OmegaPluralsResourceText,
        extractor: OmegaResourceExtractor
    ): CharSequence? {
//        TODO("Not yet implemented")
        return null
    }

}