package com.omega_r.libs.entities

actual sealed class OmegaResource {

    actual data class Text(val id: String) : OmegaResource()

    actual data class Plurals(val id: String) : OmegaResource()

    actual data class TextArray(val id: String) : OmegaResource()

    actual data class Colour(val id: String) : OmegaResource()

}