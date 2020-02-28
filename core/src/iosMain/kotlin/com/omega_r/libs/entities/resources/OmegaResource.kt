package com.omega_r.libs.entities.resources

actual sealed class OmegaResource {

    actual data class Text(val id: String) : OmegaResource()

    actual data class Plurals(val id: String) : OmegaResource()

    actual data class TextArray(val id: String) : OmegaResource()

    actual data class Color(val id: String) : OmegaResource()

    actual data class Image(val id: String) : OmegaResource()

}