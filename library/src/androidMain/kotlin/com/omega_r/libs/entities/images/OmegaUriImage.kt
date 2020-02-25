package com.omega_r.libs.entities.images

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import com.omega_r.libs.entities.decoders.toBitmap
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import java.io.InputStream

data class OmegaUriImage(val uri: Uri) : BaseBitmapImage(), OmegaImage {

    companion object {
        init {
            OmegaImageProcessorsHolder.Default.addProcessor(OmegaUriImage::class, Processor())
        }
    }

    class Processor : BaseBitmapImage.Processor<OmegaUriImage>(true) {

        override suspend fun OmegaUriImage.getBitmap(extractor: OmegaResourceExtractor, width: Int?, height: Int?): Bitmap? {
            when (val scheme = uri.scheme) {
                ContentResolver.SCHEME_ANDROID_RESOURCE, ContentResolver.SCHEME_FILE, ContentResolver.SCHEME_CONTENT -> {
                    val stream = extractor.contentResolver?.openInputStream(uri) ?: return null
                    return stream.toBitmap(width, height)
                }
                else -> throw IllegalArgumentException("Not supported uri scheme = $scheme")
            }
        }

        override suspend fun OmegaUriImage.getInputStream(
                extractor: OmegaResourceExtractor,
                format: OmegaImage.Format,
                quality: Int
        ): InputStream? = extractor.contentResolver?.openInputStream(uri)

    }

}

fun OmegaImage.Companion.from(uri: Uri): OmegaImage = OmegaUriImage(uri)