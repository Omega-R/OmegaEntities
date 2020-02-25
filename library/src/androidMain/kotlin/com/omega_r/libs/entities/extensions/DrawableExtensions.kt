package com.omega_r.libs.entities.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

inline fun <R> Drawable.toBitmap(converter: Bitmap.() -> R): R = with(this) {
    if (this is BitmapDrawable) {
        return converter(bitmap)
    }

    val newBitmap = if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
        Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)!!
    } else {
        Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)!!
    }

    try {
        val oldBounds = copyBounds()
        setBounds(0, 0, newBitmap.width, newBitmap.height)

        draw(Canvas(newBitmap))

        bounds = oldBounds
        return converter(newBitmap)
    } finally {
        newBitmap.recycle()
    }
}