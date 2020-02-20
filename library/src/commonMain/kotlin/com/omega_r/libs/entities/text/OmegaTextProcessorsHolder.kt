package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.processors.ProcessorsHolder
import com.omega_r.libs.entities.text.array.OmegaArrayText
import com.omega_r.libs.entities.text.array.OmegaArrayTextProcessor
import com.omega_r.libs.entities.text.resource.OmegaResourceText
import com.omega_r.libs.entities.text.resource.OmegaResourceTextProcessor
import com.omega_r.libs.entities.text.styled.OmegaDefaultStyledTextProcessor
import com.omega_r.libs.entities.text.styled.OmegaStyledText

interface OmegaTextProcessorsHolder : ProcessorsHolder<OmegaText, OmegaTextProcessor<out OmegaText>> {

    companion object {

        var current: OmegaTextProcessorsHolder = Default

    }

    object Default : ProcessorsHolder.Default<OmegaText, OmegaTextProcessor<out OmegaText>>(), OmegaTextProcessorsHolder {

        init {
            addProcessors(
                OmegaStyledText::class to OmegaDefaultStyledTextProcessor,
                OmegaArrayText::class to OmegaArrayTextProcessor,
                OmegaResourceText::class to OmegaResourceTextProcessor
//                OmegaPluralsText::class to OmegaResourceTextProcessor
            )
        }

    }

}