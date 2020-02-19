package com.omega_r.libs.entities.text.array

import com.omega_r.libs.entities.text.OmegaText

class OmegaArrayText internal constructor(vararg val array: OmegaText) : OmegaText {

    constructor(list: List<OmegaText>) : this(*list.toTypedArray())

    override val isEmpty: Boolean
        get() = !array.any { !text.isEmpty }

    override fun plus(text: OmegaText): OmegaText {
        return OmegaArrayText(array = *arrayOf(*array, text))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as OmegaArrayText

        if (!array.contentEquals(other.array)) return false

        return true
    }

    override fun hashCode(): Int {
        return array.contentHashCode()
    }
}