package com.omega_r.libs.entities.text

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect class Resource<T>(some: T) {
    val id: T
    fun isEmpty(): Boolean
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect class ResourceText(resource: Resource<*>) : Text {
    override val isEmpty: Boolean
}
