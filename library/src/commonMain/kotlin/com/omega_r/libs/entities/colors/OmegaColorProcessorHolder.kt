package com.omega_r.libs.entities.colors

import com.omega_r.libs.entities.colors.resource.OmegaResourceColor
import com.omega_r.libs.entities.colors.resource.OmegaResourceColorProcessor
import com.omega_r.libs.entities.processors.ProcessorsHolder
import com.omega_r.libs.entities.text.OmegaText
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

interface OmegaColorProcessorsHolder : ProcessorsHolder<OmegaColor, Int?> {

    companion object {

        var current: OmegaColorProcessorsHolder = Default

    }

    object Default : ProcessorsHolder.Default<OmegaColor, Int?>(), OmegaColorProcessorsHolder {

        init {
            addProcessors(OmegaResourceColor::class to OmegaResourceColorProcessor)
        }

    }

}

