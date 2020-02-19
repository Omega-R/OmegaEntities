package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.OmegaEntity
import com.omega_r.libs.entities.OmegaResource
import com.omega_r.libs.entities.text.array.OmegaArrayText
import com.omega_r.libs.entities.text.plurals.OmegaPluralsText
import com.omega_r.libs.entities.text.resource.OmegaResourceText
import com.omega_r.libs.entities.text.string.OmegaStringText
import com.omega_r.libs.entities.text.styled.OmegaStyledText
import com.omega_r.libs.entities.text.styled.styles.OmegaTextStyle
import kotlin.jvm.JvmStatic

interface OmegaText : OmegaEntity, OmegaTextHolder {

    companion object {

        @JvmStatic
        val empty: OmegaText = OmegaStringText(null)

        @JvmStatic
        fun from(value: String): OmegaText =
            OmegaStringText(value)

        @JvmStatic
        fun from(sequence: CharSequence): OmegaText =
            OmegaStringText(sequence.toString())

        @JvmStatic
        fun from(holder: OmegaStringHolder): OmegaText =
            OmegaStringText(holder.string)

        @JvmStatic
        fun from(resource: OmegaResource<*>): OmegaText =
            OmegaResourceText(resource)

        @JvmStatic
        fun from(resource: OmegaResource<*>, vararg formatArgs: Any): OmegaText =
            OmegaResourceText(resource, *formatArgs)

        @JvmStatic
        fun fromPlurals(resource: OmegaResource<*>, quantity: Int, vararg formatArgs: Any): OmegaText
                =
            OmegaPluralsText(resource, quantity, *formatArgs)

        @JvmStatic
        fun from(vararg text: OmegaText): OmegaText =
            OmegaArrayText(*text)

        @JvmStatic
        fun from(list: List<OmegaText>): OmegaText =
            OmegaArrayText(list)

        @JvmStatic
        fun from(text: OmegaText, style: OmegaTextStyle): OmegaText =
            OmegaStyledText(text, style)

    }

    override val text: OmegaText
        get() = this

    val isEmpty: Boolean
        get() = getCharSequence().isNullOrEmpty()

    operator fun plus(text: OmegaText): OmegaText = from(this, text)

    operator fun plus(holder: OmegaTextHolder): OmegaText = this + holder.text

    operator fun plus(string: String): OmegaText = this + string.toText()

    operator fun plus(textStyle: OmegaTextStyle): OmegaText =
        OmegaStyledText(this, textStyle)

    fun getString(): String? = getCharSequence()?.toString()

    fun getCharSequence(): CharSequence? = with(TextProcessorsHolder) { extract() }

}

interface OmegaStringHolder {

    val string: String?

}

interface OmegaTextHolder {

    val text: OmegaText

}

fun String.toText(): OmegaText = OmegaText.from(this)