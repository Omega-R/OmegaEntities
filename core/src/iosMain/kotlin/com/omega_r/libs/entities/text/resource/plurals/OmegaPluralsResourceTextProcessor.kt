package com.omega_r.libs.entities.text.resource.plurals

import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import com.omega_r.libs.entities.text.NSSpannableString

actual object OmegaPluralsResourceTextProcessor : OmegaBaseTextResourceTextProcessor() {

    override fun extractWithArgs(
        entity: OmegaPluralsResourceText,
        formatArgs: Array<out Any>,
        resourceExtractor: OmegaResourceExtractor
    ): CharSequence? {

        val sourceString = resourceExtractor.getPluralsChatSequence(entity.resource, entity.quantity) ?: return null
        return NSSpannableString.createAndFormat(sourceString, formatArgs[0]).string

    }

}