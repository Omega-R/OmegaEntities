package com.omega_r.libs.entities.processors

import com.omega_r.libs.entities.OmegaEntity
import kotlin.reflect.KClass

open class ProcessorsHolder<T : OmegaEntity, Result> {

    private val processorsMap: MutableMap<KClass<*>, OmegaProcessor<*, *>> = mutableMapOf()

    open fun <E: T> addProcessors(vararg processors: Pair<KClass<E>, OmegaProcessor<E, Result>>) {
        processors.forEach { addProcessor(it.first, it.second) }
    }

    open fun <E: T> addProcessor(className: KClass<E>, processor: OmegaProcessor<E, Result>) {
        processorsMap[className] = processor
    }

    fun T.extract(): Result {
        val entity = this
        return with(entity.getProcessor()) {
            entity.extract()
        }
    }

    private fun T.getProcessor(): OmegaProcessor<T, Result> {
        @Suppress("UNCHECKED_CAST")
        return processorsMap[this::class] as? OmegaProcessor<T, Result>
                ?: throw IllegalArgumentException("TextProcessor not found for ${this::class}")
    }

}