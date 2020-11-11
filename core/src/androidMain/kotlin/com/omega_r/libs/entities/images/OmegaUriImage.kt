package com.omega_r.libs.entities.images

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import com.omega_r.libs.entities.decoders.toBitmap
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.*
import java.io.InputStream

data class OmegaUriImage(val uri: Uri) : BaseBitmapImage(), OmegaImage {

    companion object {
        init {
            OmegaImageProcessorsHolder.Default.addProcessor(OmegaUriImage::class, Processor())
        }
    }

    class Processor : BaseBitmapImage.Processor<OmegaUriImage>(true) {

        override fun applyImage(
                entity: OmegaUriImage,
                imageView: ImageView,
                holder: OmegaImageProcessorsHolder,
                extractor: OmegaResourceExtractor
        ) {
            imageView.setImageURI(entity.uri)
        }

        override suspend fun getBitmap(
                entity: OmegaUriImage,
                extractor: OmegaResourceExtractor,
                width: Int?,
                height: Int?
        ): Bitmap? {
            when (val scheme = entity.uri.scheme) {
                ContentResolver.SCHEME_ANDROID_RESOURCE, ContentResolver.SCHEME_FILE, ContentResolver.SCHEME_CONTENT -> {
                    val stream = extractor.contentResolver?.openInputStream(entity.uri) ?: return null
                    return stream.toBitmap(width, height)
                }
                else -> throw IllegalArgumentException("Not supported uri scheme = $scheme")
            }
        }

        override suspend fun getInputStream(
                entity: OmegaUriImage,
                extractor: OmegaResourceExtractor,
                format: OmegaImage.Format,
                quality: Int
        ): InputStream? = extractor.contentResolver?.openInputStream(entity.uri)

    }

    override suspend fun getInput(
        holder: OmegaImageProcessorsHolder,
        extractor: OmegaResourceExtractor,
        format: OmegaImage.Format,
        quality: Int
    ): Input? = holder.getProcessor(this).getInput(this, extractor, format, quality)

}

fun OmegaImage.Companion.from(uri: Uri): OmegaImage = OmegaUriImage(uri)