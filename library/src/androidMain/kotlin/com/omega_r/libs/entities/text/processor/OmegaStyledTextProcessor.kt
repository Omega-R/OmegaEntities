package com.omega_r.libs.entities.text.processor

import android.content.Context
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.text.style.UnderlineSpan
import com.omega_r.libs.entities.text.StyledOmegaText
import com.omega_r.libs.entities.text.styles.*

class OmegaStyledTextProcessor(private val context: Context) : OmegaTextProcessor<StyledOmegaText> {

    override fun StyledOmegaText.extract(): CharSequence? = sourceText.getCharSequence()?.let { applyStyle(it, style) }

    private fun applyStyle(charSequence: CharSequence, style: OmegaTextStyle): CharSequence {
        return charSequence
                .getSpannableString()
                .applyStyle(style)
    }

    private fun CharSequence.getSpannableString(): SpannableString {
        if (this is SpannableString) return this
        return SpannableString(this)
    }

    private fun SpannableString.applyStyle(style: OmegaTextStyle): SpannableString {
        when (style) {
            is OmegaFontTextStyle -> setSpan(StyleSpan(style.typeFaceStyle))
            is UnderlineTextStyle -> setSpan(UnderlineSpan())
            is StrikeThroughTextStyle -> setSpan(StrikethroughSpan())
            is NameFontTextStyle -> setSpan(TypefaceSpan(style.fontName.getString()))
            is OmegaArrayTextStyle -> {
                style.styles.forEach {
                    applyStyle(it)
                }
            }
        }
        return this
    }

    private fun SpannableString.setSpan(span: Any) {
        setSpan(span, 0, length, 0)
    }

}