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

        override fun applyImageInner(
                entity: OmegaDrawableImage,
                imageView: ImageView,
                placeholderResId: Int,
                extractor: OmegaResourceExtractor
        ) {
            imageView.setImageDrawable(entity.drawable)
        }

        override fun applyBackgroundInner(
                entity: OmegaDrawableImage,
                view: View,
                placeholderResId: Int,
                extractor: OmegaResourceExtractor
        ) {
            OmegaImageProcessor.applyBackground(view, entity.drawable)
        }

        override suspend fun getInputStream(
                entity: OmegaDrawableImage,
                extractor: OmegaResourceExtractor,
                format: OmegaImage.Format,
                quality: Int
        ): InputStream? = entity.drawable.toBitmapAndRecycle { toInputStream(format, quality) }

    }

}

fun OmegaImage.Companion.from(drawable: Drawable): OmegaImage = OmegaDrawableImage(drawable)