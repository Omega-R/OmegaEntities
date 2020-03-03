package com.omega_r.libs.entities.images

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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

        override fun applyImage(
                image: OmegaDrawableImage,
                imageView: ImageView,
                holder: OmegaImageProcessorsHolder,
                extractor: OmegaResourceExtractor
        ) {
            imageView.setImageDrawable(image.drawable)
        }

        override fun applyBackground(
                image: OmegaDrawableImage,
                view: View,
                holder: OmegaImageProcessorsHolder,
                extractor: OmegaResourceExtractor
        ) {
            OmegaImageProcessor.applyBackground(view, image.drawable)
        }

        override suspend fun getInputStream(
                entity: OmegaDrawableImage,
                extractor: OmegaResourceExtractor,
                format: OmegaImage.Format,
                quality: Int
        ): InputStream? = entity.drawable.toBitmapAndRecycle { toInputStream(format, quality) }

        override fun applyCompoundImage(
                image: OmegaDrawableImage,
                index: Int,
                textView: TextView,
                holder: OmegaImageProcessorsHolder,
                extractor: OmegaResourceExtractor
        ) {
            OmegaImageProcessor.applyCompoundDrawable(textView, image.drawable, index)
        }

    }

}

fun OmegaImage.Companion.from(drawable: Drawable): OmegaImage = OmegaDrawableImage(drawable)