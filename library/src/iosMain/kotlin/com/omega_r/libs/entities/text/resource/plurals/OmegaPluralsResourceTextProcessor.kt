package com.omega_r.libs.entities.text.resource.plurals

import com.omega_r.libs.entities.OmegaResource
import com.omega_r.libs.entities.text.resource.OmegaResourceTextProcessor

actual object OmegaPluralsResourceTextProcessor : OmegaResourceTextProcessor<OmegaPluralsResourceText, OmegaResource.Plurals>() {

    override fun extract(resource: OmegaResource.Plurals): CharSequence? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun extractWithArgs(resource: OmegaResource.Plurals, formatArgs: Array<out Any>): CharSequence? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}