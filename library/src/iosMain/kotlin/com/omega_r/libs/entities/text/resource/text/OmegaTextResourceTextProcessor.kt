package com.omega_r.libs.entities.text.resource.text

import com.omega_r.libs.entities.OmegaResource
import com.omega_r.libs.entities.text.resource.OmegaResourceTextProcessor

actual object OmegaTextResourceTextProcessor :
    OmegaResourceTextProcessor<OmegaTextResourceText, OmegaResource.Text>() {
    override fun extract(resource: OmegaResource.Text): CharSequence? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun extractWithArgs(resource: OmegaResource.Text, formatArgs: Array<out Any>): CharSequence? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}