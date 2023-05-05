package com.omega_r.libs.entities.text.resource.text

import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import com.omega_r.libs.entities.text.NSSpannableString

actual object OmegaTextResourceTextProcessor : OmegaBaseTextResourceTextProcessor() {

    override fun extractWithArgs(
        entity: OmegaTextResourceText,
        formatArgs: Array<out Any>,
        resourceExtractor: OmegaResourceExtractor
    ): CharSequence? {

        val sourceString = resourceExtractor.getCharSequence(entity.resource) ?: return null
        return NSSpannableString.createAndFormat(sourceString, formatArgs[0]).string

    }

}