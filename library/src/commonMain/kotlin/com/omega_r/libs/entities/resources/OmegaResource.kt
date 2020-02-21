package com.omega_r.libs.entities.resources

expect sealed class OmegaResource {

    class Text: OmegaResource

    class Plurals: OmegaResource

    class TextArray: OmegaResource

    class Colour: OmegaResource

    class Image: OmegaResource

}