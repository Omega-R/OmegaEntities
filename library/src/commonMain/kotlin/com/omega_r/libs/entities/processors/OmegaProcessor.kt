package com.omega_r.libs.entities.processors

import com.omega_r.libs.entities.OmegaEntity

interface OmegaProcessor<T : OmegaEntity, Result> {

    fun T.extract(): Result

}