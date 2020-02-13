package com.omega_r.libs.entities

import kotlin.jvm.JvmStatic

data class OmegaResource<T>(val id: T) {

    companion object {

        @JvmStatic
        fun from(id: Int): OmegaResource<Int> = OmegaResource(id)

    }

}