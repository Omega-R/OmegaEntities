package com.omega_r.libs.entities.text

data class OmegaArrayText internal constructor(val list: List<OmegaText>) : OmegaText {

    internal constructor(vararg text: OmegaText) : this(text.toList())

    override val isEmpty: Boolean
        get() {
            if (list.isEmpty()) return true
            list.forEach { text ->
                if (!text.isEmpty) return false
            }
            return true
        }

}