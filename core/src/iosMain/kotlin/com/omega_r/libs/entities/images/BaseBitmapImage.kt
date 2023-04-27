package com.omega_r.libs.entities.images

abstract class BaseBitmapImage: OmegaImage {

    abstract class Processor<B: BaseBitmapImage> : OmegaBaseImageProcessor<B>(), OmegaImageProcessor<B> {

    }

}