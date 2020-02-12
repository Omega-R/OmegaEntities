package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.text.processor.OmegaTextProcessor
import com.omega_r.libs.entities.text.processor.OmegaTextProcessors

internal data class OmegaArrayText internal constructor(val list: List<OmegaText>) : OmegaText {

    companion object {

        init {
            OmegaTextProcessors.addProcessor(OmegaArrayText::class.simpleName!!, OmegaArrayTextTextProcessor())
        }

    }

    internal constructor(vararg text: OmegaText) : this(text.toList())

    override val isEmpty: Boolean
        get() {
            if (list.isEmpty()) return true
            list.forEach { text ->
                if (!text.isEmpty) return false
            }
            return true
        }


    class OmegaArrayTextTextProcessor : OmegaTextProcessor<OmegaArrayText>() {

        override fun OmegaArrayText.extract(): String? = list.joinToString(separator = "") { it.getString() ?: "" }

    }

}