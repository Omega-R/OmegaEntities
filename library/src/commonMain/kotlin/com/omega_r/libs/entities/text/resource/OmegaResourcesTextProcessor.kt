package com.omega_r.libs.entities.text.resource

import com.omega_r.libs.entities.OmegaEntity
import com.omega_r.libs.entities.OmegaResource
import com.omega_r.libs.entities.text.OmegaText
import com.omega_r.libs.entities.text.OmegaTextProcessor

abstract class OmegaResourceTextProcessor<T: OmegaResourceText<R>, R: OmegaResource> : OmegaTextProcessor<T> {

    companion object {
        private const val EMPTY = ""
    }

    override fun T.extract(): CharSequence? {
        return if (formatArgs.isEmpty()) extract(this) else {
            val formattedArray = formatArgs
                .map(this@OmegaResourceTextProcessor::convertFormatArg)
                .toTypedArray()
            extractWithArgs(this, formattedArray)
        }
    }

    protected abstract fun extract(entity: T) : CharSequence?

    protected abstract fun extractWithArgs(entity: T, formatArgs: Array<out Any>) : CharSequence?

    protected open fun convertFormatArg(any: Any?): Any {
        return when (any) {
            null -> EMPTY
            is OmegaText -> any.getCharSequence() ?: EMPTY
            else -> any
        }
    }

}