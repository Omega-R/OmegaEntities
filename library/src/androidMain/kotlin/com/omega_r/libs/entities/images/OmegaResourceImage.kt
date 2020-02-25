package com.omega_r.libs.entities.images

import android.annotation.DrawableRes
import android.view.View
import android.widget.ImageView
import com.omega_r.libs.entities.extensions.toBitmapAndRecycle
import com.omega_r.libs.entities.extensions.toInputStream
import com.omega_r.libs.entities.resources.OmegaResource
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import java.io.InputStream

actual data class OmegaResourceImage(actual val resource: OmegaResource.Image) : OmegaImage {

    companion object {
        init {
            OmegaImageProcessorsHolder.Default.addProcessor(OmegaResourceImage::class, Processor())
        }
    }

    constructor(@DrawableRes drawableRes: Int): this(OmegaResource.Image(drawableRes))

    class Processor : OmegaBaseImageProcessor<OmegaResourceImage>() {

        override fun OmegaResourceImage.applyImageInner(imageView: ImageView, placeholderResId: Int, extractor: OmegaResourceExtractor) {
            imageView.setImageResource(resource.id)
        }

        override fun OmegaResourceImage.applyBackgroundInner(view: View, placeholderResId: Int, extractor: OmegaResourceExtractor) {
            view.setBackgroundResource(resource.id)
        }

        override suspend fun OmegaResourceImage.getInputStream(extractor: OmegaResourceExtractor, format: OmegaImage.Format, quality: Int): InputStream? {
            return extractor.getDrawable(resource)?.toBitmapAndRecycle {
                toInputStream(format, quality)
            }
        }

    }

}

fun OmegaImage.Companion.from(@DrawableRes drawableRes: Int): OmegaImage = OmegaResourceImage(drawableRes)