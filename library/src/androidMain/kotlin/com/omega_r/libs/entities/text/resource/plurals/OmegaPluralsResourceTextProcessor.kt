package com.omega_r.libs.entities.text.resource.plurals

import com.omega_r.libs.entities.OmegaResource
import com.omega_r.libs.entities.text.fromHtmlString
import com.omega_r.libs.entities.text.resource.OmegaResourceTextProcessor
import com.omega_r.libs.entities.text.resource.OmegaResources.resources
import com.omega_r.libs.entities.text.toHtmlString

actual object OmegaPluralsResourceTextProcessor : OmegaResourceTextProcessor<OmegaPluralsResourceText, OmegaResource.Plurals>() {

    @Suppress("UNCHECKED_CAST")
    override fun extract(entity: OmegaPluralsResourceText): CharSequence? {
        return resources.getQuantityText(entity.resource.id, entity.quantity)
    }

    @Suppress("UNCHECKED_CAST")
    override fun extractWithArgs(entity: OmegaPluralsResourceText, formatArgs: Array<out Any>): CharSequence? {
        // Next, get the format string, and do the same to that.
        val sourceHtmlString = resources
            .getQuantityText(entity.resource.id, entity.quantity)
            .toHtmlString()

        // Now apply the String.format
        val formattedHtmlString = String.format(sourceHtmlString, *formatArgs)

        // Convert back to a CharSequence, recovering any of the HTML styling.
        return formattedHtmlString.fromHtmlString()
    }

    override fun convertFormatArg(any: Any?): Any {
        return when (val result = super.convertFormatArg(any)) {
            is CharSequence -> {
                result.toHtmlString()
            }
            else -> result
        }
    }


}