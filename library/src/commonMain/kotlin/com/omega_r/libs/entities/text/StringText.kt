package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.text.processor.TextProcessor
import com.omega_r.libs.entities.text.processor.TextProcessors

data class StringText(val string: String?) : Text {

    companion object {
        init {
            TextProcessors.default.addProcessor(StringText::class, Processor())
        }
    }

    override val isEmpty: Boolean = string.isNullOrEmpty()

    class Processor : TextProcessor<StringText>() {
        override fun StringText.extract(): String? = string
    }

}