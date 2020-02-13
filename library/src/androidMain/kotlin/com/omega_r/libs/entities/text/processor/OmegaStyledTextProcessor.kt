package com.omega_r.libs.entities.text.processor

import android.content.Context
import android.text.SpannableString
import com.omega_r.libs.entities.text.StyledOmegaText

class OmegaStyledTextProcessor(private val context: Context) : OmegaTextProcessor<StyledOmegaText> {

    override fun StyledOmegaText.extract(): String? {
        val charSequence = sourceText.getCharSequence()
        return charSequence.toString()
    }

    private fun StyledOmegaText.applyStyle() {



    }

    private fun StyledOmegaText.getSpannableString(): SpannableString? {
        val sourceString = sourceText.getCharSequence() ?: return null
        if (sourceString is SpannableString) return sourceString
        return SpannableString(sourceString)
    }

}