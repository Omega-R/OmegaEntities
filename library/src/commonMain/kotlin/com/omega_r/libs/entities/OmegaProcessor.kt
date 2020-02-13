package com.omega_r.libs.entities

interface OmegaProcessor<T, E> {

    fun T.extract(): E

}