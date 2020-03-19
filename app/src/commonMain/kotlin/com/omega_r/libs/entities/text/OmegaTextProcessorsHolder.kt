package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.processors.ProcessorsHolder
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import com.omega_r.libs.entities.text.array.OmegaArrayText
import com.omega_r.libs.entities.text.array.OmegaArrayTextProcessor
import com.omega_r.libs.entities.text.resource.plurals.OmegaPluralsResourceText
import com.omega_r.libs.entities.text.resource.OmegaResourceText
import com.omega_r.libs.entities.text.resource.OmegaResourceTextProcessor
import com.omega_r.libs.entities.text.resource.plurals.OmegaPluralsResourceTextProcessor
import com.omega_r.libs.entities.text.resource.text.OmegaTextResourceText
import com.omega_r.libs.entities.text.resource.text.OmegaTextResourceTextProcessor
import com.omega_r.libs.entities.text.styled.OmegaDefaultStyledTextProcessor
import com.omega_r.libs.entities.text.styled.OmegaStyledText
import kotlin.reflect.KClass

interface OmegaTextProcessorsHolder : ProcessorsHolder<OmegaText, OmegaTextProcessor<OmegaText>> {

    companion object {

        var current: OmegaTextProcessorsHolder = Default

    }

    fun extract(text: OmegaText, resourceExtractor: OmegaResourceExtractor): CharSequence? {
        return getProcessor(text)
            .extract(text, resourceExtractor)
    }

    object Default : ProcessorsHolder.Default<OmegaText, OmegaTextProcessor<OmegaText>>(), OmegaTextProcessorsHolder {

        init {
            addProcessor(OmegaStyledText::class, OmegaDefaultStyledTextProcessor)
            addProcessor(OmegaArrayText::class, OmegaArrayTextProcessor)
            addProcessor(OmegaTextResourceText::class, OmegaTextResourceTextProcessor)
            addProcessor(OmegaPluralsResourceText::class, OmegaPluralsResourceTextProcessor)
        }

    }

}