package com.omega_r.libs.entities.text.resource

import com.omega_r.libs.entities.OmegaResource.Text
import com.omega_r.libs.entities.text.OmegaText
import com.omega_r.libs.entities.text.OmegaTextProcessor

abstract class OmegaBaseResourceTextProcessor<T: OmegaResourceText> : OmegaTextProcessor<T> {

    companion object {
        private const val EMPTY = ""
    }

    override fun T.extract(): CharSequence? {
        return if (formatArgs.isEmpty()) extract(resource) else {
            val formattedArray = formatArgs
                .map(this@OmegaBaseResourceTextProcessor::mapFormatArg)
                .toTypedArray()
            extractWithArgs(resource, formattedArray)
        }
    }

    protected abstract fun extract(resource: Text) : CharSequence?

    protected abstract fun extractWithArgs(resource: Text, formatArgs: Array<out Any>) : CharSequence?

    protected open fun mapFormatArg(any: Any?): Any {
        return when (any) {
            null -> EMPTY
            is OmegaText -> any.getCharSequence() ?: EMPTY
            else -> any
        }
    }

}