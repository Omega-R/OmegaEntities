package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.Identifiable

abstract class Resource<T> : Identifiable<T> {
    abstract fun isEmpty(): Boolean
}

class IntResource(override val id: Int) : Resource<Int>() {
    override fun isEmpty(): Boolean = id <= 0
}

class StringNamedResource(override val id: String) : Resource<String>() {
    override fun isEmpty(): Boolean = id.isEmpty()
}

class ResourceText(val resource: Resource<*>) : Text {
    override val isEmpty: Boolean = resource.isEmpty()
}
