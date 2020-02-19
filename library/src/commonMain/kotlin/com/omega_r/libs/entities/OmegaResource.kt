package com.omega_r.libs.entities

expect sealed class OmegaResource {

    class Text: OmegaResource

    class Plurals: OmegaResource

    class TextArray: OmegaResource

}