package com.omega_r.libs.entities

import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import com.omega_r.libs.entities.text.OmegaStringHolder
import com.omega_r.libs.entities.text.OmegaText
import com.omega_r.libs.entities.text.OmegaTextBuilder
import com.omega_r.libs.entities.text.array.OmegaArrayText
import com.omega_r.libs.entities.text.string.OmegaStringText
import org.junit.Test

private const val SIMPLE_TEXT = "HelloWorld"

class OmegaTextTests {

    private val stringHolder: OmegaStringHolder = object : OmegaStringHolder {

        override val string: String? = SIMPLE_TEXT

    }

    @Test
    fun stringTextTest() {
        val text = OmegaText.from(SIMPLE_TEXT)
        assert(text is OmegaStringText)

        val stringValue = text.getString(extractor = OmegaResourceExtractor.Default)
        assert(SIMPLE_TEXT == stringValue)

        assert(text == OmegaText.from(SIMPLE_TEXT as CharSequence))
        assert(text == OmegaText.from(stringHolder))
    }

    @Test
    fun arrayTextTest() {
        val text = OmegaText.from(OmegaText.from(SIMPLE_TEXT), OmegaText.from(SIMPLE_TEXT))
        assert(text is OmegaArrayText)
    }

    @Test
    fun builderTest() {
        val text = OmegaTextBuilder()
            .append(SIMPLE_TEXT)
            .toText()
        assert(text is OmegaArrayText)
    }

}