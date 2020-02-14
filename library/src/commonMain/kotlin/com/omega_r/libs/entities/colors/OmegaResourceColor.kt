package com.omega_r.libs.entities.colors

import com.omega_r.libs.entities.OmegaResource

data class OmegaResourceColor<T>(val resource: OmegaResource<T>) : OmegaColor {

    override val colorInt: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

}