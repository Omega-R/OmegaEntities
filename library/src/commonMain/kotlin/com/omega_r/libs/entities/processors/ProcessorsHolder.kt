package com.omega_r.libs.entities.processors

import com.omega_r.libs.entities.OmegaEntity
import com.omega_r.libs.entities.text.OmegaText
import com.omega_r.libs.entities.text.OmegaTextProcessor
import kotlin.reflect.KClass

interface ProcessorsHolder<T : OmegaEntity, P : OmegaProcessor<T>> {

    fun getProcessor(entity: T): P

    open class Default<T : OmegaEntity, P> where P : OmegaProcessor<out T> {

        private val processorsMap: MutableMap<KClass<out T>, P> = mutableMapOf()

        open fun <TT: T, PP: OmegaProcessor<TT>> addProcessor(className: KClass<TT>, processor: PP)  {
            @Suppress("UNCHECKED_CAST")
            processorsMap[className] = processor as P
        }
        fun getProcessor(entity: T): P = processorsMap[entity::class]
            ?: throw IllegalArgumentException("Processor not found for class ${this::class}")

    }

}