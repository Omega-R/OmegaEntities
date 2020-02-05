package com.omega_r.libs.entities.texts

internal data class ArrayText internal constructor(private val list: List<Text>) : Text {

    internal constructor(vararg text: Text): this(text.toList())

    override val isEmpty: Boolean
        get() {
            if (list.isEmpty()) return true
            list.forEach { text ->
                if (!text.isEmpty) return false
            }
            return true
        }

}