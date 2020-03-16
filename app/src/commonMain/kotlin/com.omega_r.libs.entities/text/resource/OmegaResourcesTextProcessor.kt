package com.omega_r.libs.entities.text.resource

import com.omega_r.libs.entities.resources.OmegaResource
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import com.omega_r.libs.entities.text.OmegaText
import com.omega_r.libs.entities.text.OmegaTextProcessor

abstract class OmegaResourceTextProcessor<T : OmegaResourceText<R>, R : OmegaResource> : OmegaTextProcessor<T> {

    companion object {
        private const val EMPTY = ""
    }

    override fun extract(entity: T, extractor: OmegaResourceExtractor): CharSequence? {
        return if (entity.formatArgs.isEmpty()) extractWithoutArgs(entity, extractor) else {
            val formattedArray = entity.formatArgs
                .map {
                    convertFormatArg(it, extractor)
                }
                .toTypedArray()
            extractWithArgs(entity, formattedArray, extractor)
        }
    }

    protected abstract fun extractWithoutArgs(entity: T, extractor: OmegaResourceExtractor): CharSequence?

    protected abstract fun extractWithArgs(
        entity: T,
        formatArgs: Array<out Any>,
        resourceExtractor: OmegaResourceExtractor
    ): CharSequence?

    protected open fun convertFormatArg(any: Any?, resourceExtractor: OmegaResourceExtractor): Any {
        return when (any) {
            null -> EMPTY
            is OmegaText -> any.getCharSequence(resourceExtractor) ?: EMPTY
            else -> any
        }
    }

}