package com.omega_r.libs.entities.text.processor

import com.omega_r.libs.entities.text.Text
import kotlin.reflect.KClass

abstract class TextProcessors {
    companion object {
        val default = Default()

        var current: TextProcessors = default
    }

    abstract fun Text.extract(): String?

    class Default : TextProcessors() {

        private val map: MutableMap<KClass<out Text>, TextProcessor<*>> = mutableMapOf()

        fun <T : Text> addProcessor(textClass: KClass<T>, processor: TextProcessor<T>) {
            map[textClass] = processor
        }

        override fun Text.extract(): String? = with(getProcessor()) { extract() }

        private fun Text.getProcessor(): TextProcessor<Text> {
            @Suppress("UNCHECKED_CAST")
            return map[this::class] as? TextProcessor<Text>
                ?: default.map[this::class] as? TextProcessor<Text>
                ?: throw IllegalArgumentException("TextProcessor not found for ${this::class}")
        }

    }
}