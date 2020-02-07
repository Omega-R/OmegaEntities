package com.omega_r.libs.entities.text

import android.content.res.Resources
import com.omega_r.libs.entities.text.processor.OmegaTextProcessor

class IntResOmegaTextProcessor(private val resources: Resources): OmegaTextProcessor<ResourceOmegaText>() {

    override fun ResourceOmegaText.extract(): String? {
        // TODO: implement StringNamedResource
        return (this.resource as? IntOmegaResource)?.let {
            try {
                resources.getString(it.id)
            } catch (ex: Resources.NotFoundException) {
                null
            }
        }
    }

}