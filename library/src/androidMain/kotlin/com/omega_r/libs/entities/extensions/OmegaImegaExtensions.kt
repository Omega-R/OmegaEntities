package com.omega_r.libs.entities.extensions

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.JELLY_BEAN_MR1
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.omega_r.libs.entities.images.OmegaImage
import com.omega_r.libs.entities.images.OmegaImage.Format.*
import com.omega_r.libs.entities.images.OmegaImageProcessor
import com.omega_r.libs.entities.images.OmegaImageProcessorsHolder
import com.omega_r.libs.entities.images.from
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import com.omega_r.libs.entities.text.extractor

val OmegaImage.Companion.NO_PLACEHOLDER_RES: Int
    get() = 0

fun OmegaImage.Format.toCompressFormat(): Bitmap.CompressFormat {
    return when (this) {
        JPEG -> Bitmap.CompressFormat.JPEG
        PNG -> Bitmap.CompressFormat.PNG
        WEBP -> Bitmap.CompressFormat.WEBP
    }
}

fun ImageView.setImage(
        image: OmegaImage?,
        placeholderResId: Int = OmegaImage.NO_PLACEHOLDER_RES,
        holder: OmegaImageProcessorsHolder = OmegaImageProcessorsHolder.current,
        extractor: OmegaResourceExtractor = OmegaResourceExtractor.Default
) {
    val finalImage = image.formatImage(placeholderResId)
    if (finalImage == null) {
        setImageDrawable(null)
    } else {
        holder.getProcessor(finalImage)
                .applyImage(finalImage, this, holder, extractor)
    }
}

@JvmOverloads
fun OmegaImage.preload(
        holder: OmegaImageProcessorsHolder = OmegaImageProcessorsHolder.current,
        extractor: OmegaResourceExtractor = OmegaResourceExtractor.Default
) {
    holder.getProcessor(this).preload(this, extractor)
}

@JvmOverloads
fun View.setBackground(
        image: OmegaImage?,
        placeholderResId: Int = OmegaImage.NO_PLACEHOLDER_RES,
        holder: OmegaImageProcessorsHolder = OmegaImageProcessorsHolder.current,
        extractor: OmegaResourceExtractor = OmegaResourceExtractor.Default
) {
    val finalImage = image.formatImage(placeholderResId)
    if (finalImage == null) {
        background = null
    } else {
        holder.getProcessor(finalImage)
                .applyBackground(finalImage, this, holder, extractor)
    }
}

private fun OmegaImage?.formatImage(placeholderResId: Int): OmegaImage? {
    val image = this
    return if (image == null) {
        if (placeholderResId == OmegaImage.NO_PLACEHOLDER_RES) null else OmegaImage.from(placeholderResId)
    } else {
        if (placeholderResId == OmegaImage.NO_PLACEHOLDER_RES) image else OmegaImage.from(placeholderResId, image)
    }
}


var TextView.imageStart: OmegaImage?
    get() = getImage(0)
    set(value) = setImage(0, value)

var TextView.imageEnd: OmegaImage?
    get() = getImage(2)
    set(value) = setImage(2, value)

var TextView.imageTop: OmegaImage?
    get() = getImage(1)
    set(value) = setImage(1, value)

var TextView.imageBottom: OmegaImage?
    get() = getImage(3)
    set(value) = setImage(3, value)

@SuppressLint("ObsoleteSdkInt")
private fun TextView.getImage(index: Int): OmegaImage? {
    val drawables = if (SDK_INT >= JELLY_BEAN_MR1) compoundDrawablesRelative else compoundDrawables
    return drawables[index]?.let { OmegaImage.from(it) }
}

@SuppressLint("ObsoleteSdkInt")
private fun TextView.setImage(index: Int, image: OmegaImage?) {
    if (image == null) {
        val drawables = if (SDK_INT >= JELLY_BEAN_MR1) compoundDrawablesRelative else compoundDrawables
        drawables[index] = null
        OmegaImageProcessor.applyCompoundDrawables(this, drawables)
    } else {
        val holder = OmegaImageProcessorsHolder.current
        holder.getProcessor(image).applyCompoundImage(image, index, this, holder, this.extractor)
    }
}