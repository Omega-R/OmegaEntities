package com.omega_r.libs.entities.text.processor

import com.omega_r.libs.entities.text.OmegaText
import kotlin.native.concurrent.SharedImmutable
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
@SharedImmutable
val textProcessors = OmegaTextProcessors

object OmegaTextProcessors {

    private val map: MutableMap<String, OmegaTextProcessor<*>> = mutableMapOf()

    fun <T : OmegaText> addProcessor(className: String, processor: OmegaTextProcessor<T>) {
        map[className] = processor
    }

    fun OmegaText.extract(): String? = with(getProcessor()) { extract() }

    private fun OmegaText.getProcessor(): OmegaTextProcessor<OmegaText> {
        @Suppress("UNCHECKED_CAST")
        return map[this::class.simpleName!!] as? OmegaTextProcessor<OmegaText>
                ?: throw IllegalArgumentException("TextProcessor not found for ${this::class}")
    }

}