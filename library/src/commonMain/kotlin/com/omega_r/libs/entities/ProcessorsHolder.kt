package com.omega_r.libs.entities

import com.omega_r.libs.entities.text.OmegaText

class ProcessorsHolder {

    private val processorsMap: MutableMap<String, OmegaProcessor<*, *>> = mutableMapOf()

    fun addProcessor(className: String, processor: OmegaProcessor<*, *>) {
        processorsMap[className] = processor
    }

    fun Processable.extract(): CharSequence? = with(getProcessor()) { extract() }

    private fun <T : Processable> T.getProcessor(): OmegaProcessor<T, *> {
        @Suppress("UNCHECKED_CAST")
        return processorsMap[this::class.simpleName!!] as? OmegaProcessor<T, *>
                ?: throw IllegalArgumentException("TextProcessor not found for ${this::class}")
    }

}