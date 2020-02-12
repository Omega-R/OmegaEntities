package com.omega_r.libs.entities.text

import android.text.SpannableString
import com.omega_r.libs.entities.text.processor.OmegaTextProcessor

class OmegaStyleTextProcessor : OmegaTextProcessor<StyledOmegaText>() {

    override fun StyledOmegaText.extract(): String? = sourceText.getString()

    fun StyledOmegaText.extractWithStyle(): CharSequence? {

        when(sourceText) {
            is OmegaArrayText -> {
                sourceText.list.map {
                    if (it is StyledOmegaText) it.extractWithStyle()
                    SpannableString(it.getString())
                }
            }
            else -> {
                // TODO apply style
            }
        }
        return null
    }

    private fun StyledOmegaText.getSpannableString(): SpannableString? {
        val sourceString = sourceText.getString() as? CharSequence ?: return null
        if (sourceString is SpannableString) return sourceString
        return SpannableString(sourceString)
    }

}