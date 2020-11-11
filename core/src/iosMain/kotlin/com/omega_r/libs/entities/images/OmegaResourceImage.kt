package com.omega_r.libs.entities.images

import com.omega_r.libs.entities.resources.OmegaResource
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.*

//import platform.UIKit.UIImage

actual class OmegaResourceImage : OmegaImage {

//    TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    actual val resource: OmegaResource.Image
        get() = OmegaResource.Image("")

    override suspend fun getInput(
        holder: OmegaImageProcessorsHolder,
        extractor: OmegaResourceExtractor,
        format: OmegaImage.Format,
        quality: Int
    ): Input? = holder.getProcessor(this).getInput(this, extractor, format, quality)

}