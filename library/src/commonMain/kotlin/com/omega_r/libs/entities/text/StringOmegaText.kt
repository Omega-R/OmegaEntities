package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.text.processor.OmegaTextProcessor
import com.omega_r.libs.entities.text.processor.OmegaTextProcessors

data class StringOmegaText(val value: String?) : OmegaText {

    companion object {
        init {
            OmegaTextProcessors.addProcessor(StringOmegaText::class.simpleName!!, ProcessorOmega())
        }
    }

    override val isEmpty: Boolean = value.isNullOrEmpty()

    class ProcessorOmega : OmegaTextProcessor<StringOmegaText>() {
        override fun StringOmegaText.extract(): String? = value
    }

}