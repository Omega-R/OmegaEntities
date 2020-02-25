package com.omega_r.libs.entities.images

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.omega_r.libs.entities.extensions.toBitmapAndRecycle
import com.omega_r.libs.entities.extensions.toInputStream
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import java.io.InputStream

data class OmegaDrawableImage(val drawable: Drawable) : BaseBitmapImage(), OmegaImage {

    companion object {
        init {
            OmegaImageProcessorsHolder.Default.addProcessor(OmegaDrawableImage::class, Processor())
        }
    }

    class Processor : OmegaBaseImageProcessor<OmegaDrawableImage>() {

        override fun OmegaDrawableImage.applyImageInner(
                imageView: ImageView,
                placeholderResId: Int,
                extractor: OmegaResourceExtractor
        ) {
            imageView.setImageDrawable(drawable)
        }

        override fun OmegaDrawableImage.applyBackgroundInner(
                view: View,
                placeholderResId: Int,
                extractor: OmegaResourceExtractor
        ) {
            OmegaImageProcessor.applyBackground(view, drawable)
        }

        override suspend fun OmegaDrawableImage.getInputStream(
                extractor: OmegaResourceExtractor,
                format: OmegaImage.Format,
                quality: Int
        ): InputStream? = drawable.toBitmapAndRecycle { toInputStream(format, quality) }

    }

}

fun OmegaImage.Companion.from(drawable: Drawable): OmegaImage = OmegaDrawableImage(drawable)