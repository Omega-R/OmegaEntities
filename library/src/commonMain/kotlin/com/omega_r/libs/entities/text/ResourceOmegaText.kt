package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.OmegaIdentifiable

val CLASS_NAME = ResourceOmegaText::class.simpleName!!

class ResourceOmegaText private constructor(val resource: OmegaResource<*>) : OmegaText {

    constructor(string: String) : this(resource = StringNamedOmegaResource(string))
    constructor(intRes: Int) : this(resource = IntOmegaResource(intRes))

    override val isEmpty: Boolean = resource.isEmpty()
}

abstract class OmegaResource<T> : OmegaIdentifiable<T> {
    abstract fun isEmpty(): Boolean
}

class IntOmegaResource(override val id: Int) : OmegaResource<Int>() {
    override fun isEmpty(): Boolean = id <= 0
}

class StringNamedOmegaResource(override val id: String) : OmegaResource<String>() {
    override fun isEmpty(): Boolean = id.isEmpty()
}