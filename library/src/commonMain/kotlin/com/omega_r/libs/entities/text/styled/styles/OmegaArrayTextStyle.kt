package com.omega_r.libs.entities.text.styled.styles

class OmegaArrayTextStyle(vararg styles: OmegaTextStyle) : OmegaTextStyle {

    val array: Array<out OmegaTextStyle>

    init {
        array = if (!styles.any { it is OmegaArrayTextStyle }) {
            styles
        } else {
            val list = ArrayList<OmegaTextStyle>(styles.size + 1)

            styles.forEach {
                when (it) {
                    is OmegaArrayTextStyle -> list.addAll(it.array)
                    else -> list.add(it)
                }
            }
            list.toTypedArray()
        }
    }

    override fun plus(style: OmegaTextStyle): OmegaTextStyle {
        return OmegaArrayTextStyle(*array, style)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as OmegaArrayTextStyle

        if (!array.contentEquals(other.array)) return false

        return true
    }

    override fun hashCode(): Int {
        return array.contentHashCode()
    }

    override fun toString(): String {
        return "OmegaArrayTextStyle(array=${array.contentToString()})"
    }


}