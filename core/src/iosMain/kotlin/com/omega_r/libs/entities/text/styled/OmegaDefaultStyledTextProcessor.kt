package com.omega_r.libs.entities.text.styled

import com.omega_r.libs.entities.extensions.toUIColor
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import com.omega_r.libs.entities.text.NSSpannableString
import com.omega_r.libs.entities.text.styled.styles.OmegaArrayTextStyle
import com.omega_r.libs.entities.text.styled.styles.OmegaColorTextStyle
import com.omega_r.libs.entities.text.styled.styles.OmegaDecorationTextStyle
import com.omega_r.libs.entities.text.styled.styles.OmegaFontStyleTextStyle
import com.omega_r.libs.entities.text.styled.styles.OmegaFontTextStyle
import com.omega_r.libs.entities.text.styled.styles.OmegaTextStyle
import platform.Foundation.attribute
import platform.UIKit.NSFontAttributeName
import platform.UIKit.NSForegroundColorAttributeName
import platform.UIKit.NSStrikethroughStyleAttributeName
import platform.UIKit.NSUnderlineStyleAttributeName
import platform.UIKit.NSUnderlineStyleSingle
import platform.UIKit.UIFont
import platform.UIKit.UIFontWeightRegular

actual object OmegaDefaultStyledTextProcessor : OmegaStyledTextProcessor {

    actual override fun extract(
        entity: OmegaStyledText,
        extractor: OmegaResourceExtractor
    ): CharSequence? =
        entity.sourceText.getCharSequence(extractor)
            ?.applyStyle(
                entity.styles,
                extractor
            )

    private fun CharSequence.applyStyle(
        styles: Array<out OmegaTextStyle>,
        extractor: OmegaResourceExtractor
    ): CharSequence {
        // rewrite as in androidMain if it won't affect the result
        var originalString = NSSpannableString.create(this)
        styles.forEach { style ->
            originalString = originalString.applyStyle(style, extractor)
        }
        return originalString.string()
    }

    // https://www.hackingwithswift.com/articles/113/nsattributedstring-by-example parsing html string
    private fun NSSpannableString.applyStyle(
        style: OmegaTextStyle,
        extractor: OmegaResourceExtractor
    ): NSSpannableString {
        var finalString = this
        when (style) {
            is OmegaArrayTextStyle -> {
                style.array.forEach { applyStyle(it, extractor) }
            }

            is OmegaColorTextStyle -> {
                val colorInt = style.color.getColorInt(extractor = extractor)
                finalString = colorSpan(colorInt)
            }

            is OmegaDecorationTextStyle -> {
                style.styles.forEach { decorationTextStyle ->
                    when (decorationTextStyle) {
                        OmegaDecorationTextStyle.DecorationStyle.UNDERLINE -> underlineSpan()
                        OmegaDecorationTextStyle.DecorationStyle.STRIKETHROUGH -> strikeThroughSpan()
                    }
                }
            }

            is OmegaFontStyleTextStyle -> {
                if (style.styles.isEmpty()) {
                    normalFontStyleSpan()
                } else {
                    val isBold = OmegaFontStyleTextStyle.Style.BOLD in style.styles
                    val isItalic = OmegaFontStyleTextStyle.Style.ITALIC in style.styles
                    when {
                        isBold && isItalic -> boldItalicFontStyleSpan()
                        isBold -> boldFontStyleSpan()
                        isItalic -> italicFontStyleSpan()
                    }
                }
            }

            is OmegaFontTextStyle -> style.fontName.getString(extractor)?.let { fontName ->
                setTypefaceSpan(fontName)
            }
        }

        return finalString
    }

    private fun NSSpannableString.colorSpan(color: Int): NSSpannableString =
        this.format(
            mapOf(NSForegroundColorAttributeName to color.toUIColor())
        )

    private fun NSSpannableString.underlineSpan(): NSSpannableString =
        this.format(
            mapOf(NSUnderlineStyleAttributeName to NSUnderlineStyleSingle)
        )

    private fun NSSpannableString.strikeThroughSpan(): NSSpannableString =
        this.format(
            mapOf(NSStrikethroughStyleAttributeName to NSUnderlineStyleSingle)
        )

    private fun NSSpannableString.normalFontStyleSpan(): NSSpannableString =
        this.format(
            mapOf(
                NSFontAttributeName to UIFont.systemFontOfSize(
                    (this.attribute(NSFontAttributeName, 0u, null) as UIFont).pointSize,
                    UIFontWeightRegular
                )
            )
        )

    private fun NSSpannableString.boldItalicFontStyleSpan(): NSSpannableString {
        val fontSize = (this.attribute(NSFontAttributeName, 0u, null) as UIFont).pointSize
        return this.format(
            mapOf(NSFontAttributeName to UIFont.boldSystemFontOfSize(fontSize))
        ).format(
            mapOf(NSFontAttributeName to UIFont.italicSystemFontOfSize(fontSize))
        )
    }

    private fun NSSpannableString.boldFontStyleSpan(): NSSpannableString {
        val fontSize = (this.attribute(NSFontAttributeName, 0u, null) as UIFont).pointSize
        return this.format(
            mapOf(NSFontAttributeName to UIFont.boldSystemFontOfSize(fontSize))
        )
    }

    private fun NSSpannableString.italicFontStyleSpan(): NSSpannableString {
        val fontSize = (this.attribute(NSFontAttributeName, 0u, null) as UIFont).pointSize
        return this.format(
            mapOf(NSFontAttributeName to UIFont.italicSystemFontOfSize(fontSize))
        )
    }

    private fun NSSpannableString.setTypefaceSpan(fontName: String): NSSpannableString {
        val fontSize = (this.attribute(NSFontAttributeName, 0u, null) as UIFont).pointSize
        return this.format(
            mapOf(NSFontAttributeName to UIFont.fontWithName(fontName, fontSize))
        )
    }


}