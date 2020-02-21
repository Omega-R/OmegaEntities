package com.omega_r.libs.entities.images

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.ImageView
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.streams.asInput
import java.io.FileNotFoundException

data class OmegaUriImage(val uri: Uri) : OmegaImage {

    class OmegaUriImageProcessor(private val contentResolver: ContentResolver) : OmegaImageProcessor<OmegaUriImage> {

        constructor(context: Context): this(context.contentResolver)

        override suspend fun OmegaUriImage.input(): Input? {
            return try {
                contentResolver.openInputStream(uri).asInput()
            } catch (exc: FileNotFoundException) {
                null
            }
        }

        override fun OmegaUriImage.applyImage(imageView: ImageView, placeholderResId: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun OmegaUriImage.applyBackground(view: View, placeholderResId: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

}

fun OmegaImage.Companion.from(uri: Uri): OmegaImage = OmegaUriImage(uri)