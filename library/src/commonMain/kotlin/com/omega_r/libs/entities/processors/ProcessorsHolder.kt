package com.omega_r.libs.entities.processors

import com.omega_r.libs.entities.OmegaEntity
import kotlin.reflect.KClass

interface ProcessorsHolder<T : OmegaEntity, P : OmegaProcessor> {

    fun T.getProcessor(): P

    open class Default<T : OmegaEntity, P : OmegaProcessor> : ProcessorsHolder<T, P> {

        private val processorsMap: MutableMap<KClass<out T>, P> = mutableMapOf()

        open fun addProcessors(vararg processors: Pair<KClass<out T>, P>) {
            processors.forEach { addProcessor(it.first, it.second) }
        }

        open fun addProcessor(className: KClass<out T>, processor: P) {
            processorsMap[className] = processor
        }

        override fun T.getProcessor(): P =
                processorsMap[this::class] ?: throw IllegalArgumentException("Processor not found for class ${this::class}")

    }

}