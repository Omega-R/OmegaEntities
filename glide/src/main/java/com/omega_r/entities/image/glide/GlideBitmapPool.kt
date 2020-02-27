package com.omega_r.entities.image.glide

import android.graphics.Bitmap
import com.omega_r.libs.entities.decoders.SimpleBitmapDecoders

class GlideBitmapPool(private val bitmapPool: com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool) : SimpleBitmapDecoders.BitmapPool {

    override fun getBitmap(outWidth: Int, outHeight: Int, inPreferredConfig: Bitmap.Config?): Bitmap? {
        return bitmapPool.get(outWidth, outHeight, inPreferredConfig)
    }

    override fun putBitmap(bitmap: Bitmap) {
        return bitmapPool.put(bitmap)
    }

    override fun clearMemory() {
        bitmapPool.clearMemory()
    }

    override fun trimMemory(level: Int) {
        bitmapPool.trimMemory(level)
    }

}