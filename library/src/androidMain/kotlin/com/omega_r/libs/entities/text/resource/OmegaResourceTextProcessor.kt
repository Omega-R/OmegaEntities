package com.omega_r.libs.entities.text.resource

import android.content.res.Resources
import com.omega_r.libs.entities.text.plurals.OmegaPluralsText
import com.omega_r.libs.entities.text.OmegaText
import com.omega_r.libs.entities.text.OmegaTextProcessor

class OmegaResourceTextProcessor(private val resources: Resources) :
    OmegaTextProcessor<OmegaResourceText<Int>> {

    override fun OmegaResourceText<Int>.extract(): String? {
        val formattedArray = formatArgs.map {
            when (it) {
                is OmegaText -> it.getString()
                else -> it
            }
        }.toTypedArray()

        return when (this) {
            is OmegaPluralsText -> resources.getQuantityString(resource.id, quantity, *formatArgs)
            else -> resources.getString(resource.id, *formattedArray)
        }
    }

}