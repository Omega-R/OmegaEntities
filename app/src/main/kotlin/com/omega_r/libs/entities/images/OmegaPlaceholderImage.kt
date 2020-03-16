package com.omega_r.libs.entities.images

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.Input
import java.io.InputStream

open class OmegaPlaceholderImage(
        val placeholderImage: OmegaImage,
        val finalImage: OmegaImage
) : OmegaImage {

    override suspend fun getInput(
            holder: OmegaImageProcessorsHolder,
            extractor: OmegaResourceExtractor,
            format: OmegaImage.Format,
            quality: Int
    ): Input? = finalImage.getInput(holder, extractor, format, quality)

    class Processor : OmegaBaseImageProcessor<OmegaPlaceholderImage>() {

        override fun applyImage(
                image: OmegaPlaceholderImage,
                imageView: ImageView,
                holder: OmegaImageProcessorsHolder,
                extractor: OmegaResourceExtractor
        ) {
            apply({ image ->
                holder.getProcessor(image).applyImage(image, imageView, holder, extractor)
            }, image.placeholderImage, image.finalImage)
        }

        override fun applyBackground(
                image: OmegaPlaceholderImage,
                view: View,
                holder: OmegaImageProcessorsHolder,
                extractor: OmegaResourceExtractor
        ) {
            apply({ image ->
                holder.getProcessor(image).applyBackground(image, view, holder, extractor)
            }, image.placeholderImage, image.finalImage)
        }

        override fun applyCompoundImage(
                image: OmegaPlaceholderImage,
                index: Int,
                textView: TextView,
                holder: OmegaImageProcessorsHolder,
                extractor: OmegaResourceExtractor
        ) {
            apply({ image ->
                holder.getProcessor(image).applyCompoundImage(image, index, textView, holder, extractor)
            }, image.placeholderImage, image.finalImage)
        }

        override suspend fun getInputStream(
                entity: OmegaPlaceholderImage,
                extractor: OmegaResourceExtractor,
                format: OmegaImage.Format,
                quality: Int
        ): InputStream? {
            throw IllegalStateException("Can't resolve getInputStream for OmegaPlaceholderImage")
        }

        private fun apply(lambda: (image: OmegaImage)-> Unit, vararg images: OmegaImage) {
            images.forEach { lambda.invoke(it) }
        }

    }

}

fun OmegaImage.Companion.from(
        placeholderImage: OmegaImage,
        finalImage: OmegaImage
): OmegaImage = OmegaPlaceholderImage(placeholderImage, finalImage)

fun OmegaImage.Companion.from(
    @DrawableRes placeholderResId: Int,
    finalImage: OmegaImage
): OmegaImage = OmegaPlaceholderImage(OmegaImage.from(placeholderResId), finalImage)