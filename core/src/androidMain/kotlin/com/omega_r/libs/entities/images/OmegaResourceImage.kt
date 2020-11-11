package com.omega_r.libs.entities.images

import android.annotation.DrawableRes
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.omega_r.libs.entities.extensions.NO_PLACEHOLDER_RES
import com.omega_r.libs.entities.extensions.toBitmapAndRecycle
import com.omega_r.libs.entities.extensions.toInputStream
import com.omega_r.libs.entities.resources.OmegaResource
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.*
import java.io.InputStream

actual data class OmegaResourceImage(actual val resource: OmegaResource.Image) : OmegaImage {

    companion object {
        init {
            OmegaImageProcessorsHolder.Default.addProcessor(OmegaResourceImage::class, Processor())
        }
    }

    constructor(@DrawableRes drawableRes: Int) : this(OmegaResource.Image(drawableRes))

    class Processor : OmegaBaseImageProcessor<OmegaResourceImage>() {

        override fun applyImage(
                entity: OmegaResourceImage,
                imageView: ImageView,
                holder: OmegaImageProcessorsHolder,
                extractor: OmegaResourceExtractor
        ) {
            val id = entity.resource.id
            if (id == OmegaImage.NO_PLACEHOLDER_RES) {
                imageView.setImageDrawable(null)
            } else {
                imageView.setImageResource(id)
            }
        }

        override fun applyBackground(
                entity: OmegaResourceImage,
                view: View,
                holder: OmegaImageProcessorsHolder,
                extractor: OmegaResourceExtractor
        ) {
            val id = entity.resource.id
            if (id == OmegaImage.NO_PLACEHOLDER_RES) {
                view.background = null
            } else {
                view.setBackgroundResource(id)
            }
        }

        override fun applyCompoundImage(
                entity: OmegaResourceImage,
                index: Int,
                textView: TextView,
                holder: OmegaImageProcessorsHolder,
                extractor: OmegaResourceExtractor
        ) {
            val id = entity.resource.id
            val drawable = if (id == OmegaImage.NO_PLACEHOLDER_RES) null else extractor.getDrawable(entity.resource)
            OmegaImageProcessor.applyCompoundDrawable(textView, drawable, index)
        }

        override suspend fun getInputStream(
                entity: OmegaResourceImage,
                extractor: OmegaResourceExtractor,
                format: OmegaImage.Format,
                quality: Int
        ): InputStream? {
            return if (entity.resource.id == OmegaImage.NO_PLACEHOLDER_RES) {
                null
            } else extractor.getDrawable(entity.resource)?.toBitmapAndRecycle {
                toInputStream(format, quality)
            }
        }

    }

    override suspend fun getInput(
        holder: OmegaImageProcessorsHolder,
        extractor: OmegaResourceExtractor,
        format: OmegaImage.Format,
        quality: Int
    ): Input? = holder.getProcessor(this).getInput(this, extractor, format, quality)

}

fun OmegaImage.Companion.from(@DrawableRes drawableRes: Int): OmegaImage = OmegaResourceImage(drawableRes)

fun OmegaImage.Companion.from(resource: OmegaResource.Image): OmegaImage = OmegaResourceImage(resource)