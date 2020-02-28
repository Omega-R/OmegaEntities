package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.OmegaEntity
import com.omega_r.libs.entities.resources.OmegaResource
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import com.omega_r.libs.entities.text.array.OmegaArrayText
import com.omega_r.libs.entities.text.resource.plurals.OmegaPluralsResourceText
import com.omega_r.libs.entities.text.resource.OmegaResourceText
import com.omega_r.libs.entities.text.resource.text.OmegaTextResourceText
import com.omega_r.libs.entities.text.string.OmegaStringText
import com.omega_r.libs.entities.text.styled.OmegaStyledText
import com.omega_r.libs.entities.text.styled.styles.OmegaTextStyle

interface OmegaText : OmegaEntity, OmegaTextHolder {

    companion object {

        val empty: OmegaText = OmegaStringText(null)

        fun from(value: String): OmegaText = OmegaStringText(value)

        fun from(sequence: CharSequence): OmegaText = OmegaStringText(sequence.toString())

        fun from(holder: OmegaStringHolder): OmegaText = OmegaStringText(holder.string)

        fun from(resource: OmegaResource.Text): OmegaText = OmegaTextResourceText(resource)

        fun from(resource: OmegaResource.Text, vararg formatArgs: Any): OmegaText = OmegaTextResourceText(resource, *formatArgs)

        fun from(resource: OmegaResource.Plurals, quantity: Int, vararg formatArgs: Any): OmegaText =
            OmegaPluralsResourceText(resource, quantity, *formatArgs)

        fun from(vararg text: OmegaText): OmegaText = OmegaArrayText(*text)

        fun from(list: List<OmegaText>): OmegaText = OmegaArrayText(list)

        fun from(text: OmegaText, style: OmegaTextStyle): OmegaText = OmegaStyledText(text, style)

    }

    override val text: OmegaText
        get() = this

    operator fun plus(text: OmegaText): OmegaText = from(this, text)

    operator fun plus(holder: OmegaTextHolder): OmegaText = this + holder.text

    operator fun plus(string: String): OmegaText = this + string.toText()

    operator fun plus(textStyle: OmegaTextStyle): OmegaText = OmegaStyledText(this, textStyle)

    fun getString(
        extractor: OmegaResourceExtractor,
        processorsHolder: OmegaTextProcessorsHolder = OmegaTextProcessorsHolder.current
    ): String? = getCharSequence(extractor, processorsHolder)?.toString()

    @Suppress("UNCHECKED_CAST")
    fun getCharSequence(
        extractor: OmegaResourceExtractor,
        processorsHolder: OmegaTextProcessorsHolder = OmegaTextProcessorsHolder.current
    ): CharSequence? = processorsHolder.extract(this, extractor)

}

fun String.toText(): OmegaText = OmegaText.from(this)