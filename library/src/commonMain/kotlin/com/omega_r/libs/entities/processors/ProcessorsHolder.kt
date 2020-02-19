package com.omega_r.libs.entities.processors

import com.omega_r.libs.entities.OmegaEntity
import kotlin.reflect.KClass

interface ProcessorsHolder<T : OmegaEntity, Result> {

    fun T.extract(): Result

    open class Default<T : OmegaEntity, Result>: ProcessorsHolder<T, Result> {

        private val processorsMap: MutableMap<KClass<*>, OmegaProcessor<*, *>> = mutableMapOf()

        open fun <E: T> addProcessors(vararg processors: Pair<KClass<out E>, OmegaProcessor<out E, Result>>) {
            processors.forEach { addProcessor(it.first, it.second) }
        }

        open fun <E: T> addProcessor(className: KClass<out E>, processor: OmegaProcessor<out E, Result>) {
            processorsMap[className] = processor
        }

        override fun T.extract(): Result {
            return with(getProcessor()) {
                extract()
            }
        }

        protected open fun T.getProcessor(): OmegaProcessor<T, Result> {
            @Suppress("UNCHECKED_CAST")
            return processorsMap[this::class] as? OmegaProcessor<T, Result>
                ?: throw IllegalArgumentException("Processor not found for ${this::class}")
        }

    }

}