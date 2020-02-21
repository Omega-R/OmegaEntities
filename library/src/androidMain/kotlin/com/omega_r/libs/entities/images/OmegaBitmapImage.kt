package com.omega_r.libs.entities.images

import android.content.Context
import android.graphics.Bitmap
import com.omega_r.libs.entities.extensions.toInputStream
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.streams.asInput

data class OmegaBitmapImage(val bitmap: Bitmap) : BaseBitmapImage() {

    class Processor : BaseBitmapImage.Processor<OmegaBitmapImage>(false) {

        override suspend fun OmegaBitmapImage.input(): Input? = bitmap.toInputStream().asInput()

        override suspend fun OmegaBitmapImage.getBitmap(context: Context, width: Int?, height: Int?): Bitmap? = bitmap

    }

}

fun OmegaImage.Companion.from(bitmap: Bitmap): OmegaImage = OmegaBitmapImage(bitmap)