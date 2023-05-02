package com.omega_r.libs.entities.images

import com.omega_r.libs.entities.processors.OmegaProcessor
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import platform.CoreGraphics.CGImageRef
import platform.Foundation.NSData
import platform.Foundation.create
import platform.UIKit.UIImage

private const val COMMA = ","

actual class OmegaBase64Image(actual val base64String: String, val flags: Int) : OmegaImage, BaseBitmapImage() {

    companion object {
        init {
            OmegaImageProcessorsHolder.Default.addProcessor(OmegaBase64Image::class, Processor())
        }
    }

    class Processor : BaseBitmapImage.Processor<OmegaBase64Image>(), OmegaProcessor<OmegaBase64Image> {

        override suspend fun getBitmap(
            entity: OmegaBase64Image,
            extractor: OmegaResourceExtractor,
            width: Double?,
            height: Double?
        ): CGImageRef? = entity.toBitmap()

        private fun OmegaBase64Image.toBitmap(): CGImageRef? {
            val base64String = base64String
            val position = base64String.indexOf(COMMA)
            val data = if (position != -1) base64String.substring(position + 1) else base64String
            val nsData = NSData.create(data, flags.toULong())
            return if (nsData != null) UIImage.imageWithData(nsData)?.CGImage else null
        }

    }

}

fun OmegaImage.Companion.from(base64String: String, flags: Int): OmegaImage = OmegaBase64Image(base64String, flags)