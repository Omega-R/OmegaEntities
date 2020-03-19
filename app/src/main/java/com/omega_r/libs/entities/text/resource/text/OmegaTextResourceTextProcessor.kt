package com.omega_r.libs.entities.text.resource.text

import com.omega_r.libs.entities.extensions.fromHtmlString
import com.omega_r.libs.entities.extensions.toHtmlString
import com.omega_r.libs.entities.resources.OmegaResourceExtractor

actual object OmegaTextResourceTextProcessor : OmegaBaseTextResourceTextProcessor() {

    override fun extractWithArgs(
        entity: OmegaTextResourceText,
        formatArgs: Array<out Any>,
        resourceExtractor: OmegaResourceExtractor
    ): CharSequence? {
        // Next, get the format string, and do the same to that.
        val sourceHtmlString = resourceExtractor
            .getCharSequence(entity.resource)
            ?.toHtmlString() ?: return null

        // Now apply the String.format
        val formattedHtmlString = String.format(sourceHtmlString, *formatArgs)

        // Convert back to a CharSequence, recovering any of the HTML styling.
        return formattedHtmlString.fromHtmlString()
    }

    override fun convertFormatArg(any: Any?, resourceExtractor: OmegaResourceExtractor): Any {
        return when (val result = super.convertFormatArg(any, resourceExtractor)) {
            is CharSequence -> {
                result.toHtmlString()
            }
            else -> result
        }
    }

}