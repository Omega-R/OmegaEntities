package com.omega_r.libs.entities.extensions

import kotlinx.cinterop.cValue
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import kotlinx.cinterop.value
import platform.CoreGraphics.CGFloatVar
import platform.UIKit.UIColor

fun UIColor.toRgbInt(): Int? {
    memScoped {
        val red = cValue<CGFloatVar>()
        val green = cValue<CGFloatVar>()
        val blue = cValue<CGFloatVar>()
        val alpha = cValue<CGFloatVar>()
        return if (
            this@toRgbInt.getRed(
                red.getPointer(this),
                green.getPointer(this),
                blue.getPointer(this),
                alpha.getPointer(this)
            )
        ) {
            val redInt = (red.getPointer(this).pointed.value * 255.0f).toInt()
            val greenInt = (green.getPointer(this).pointed.value * 255.0f).toInt()
            val blueInt = (blue.getPointer(this).pointed.value * 255.0f).toInt()
            val alphaInt = (alpha.getPointer(this).pointed.value * 255.0f).toInt()

            alphaInt.shl(24) + redInt.shl(16) + greenInt.shl(8) + blueInt

        } else null
    }
}

fun Int.toUIColor(): UIColor {
    memScoped {
        val red = cValue<CGFloatVar>()
        val green = cValue<CGFloatVar>()
        val blue = cValue<CGFloatVar>()
        val alpha = cValue<CGFloatVar>()

        red.getPointer(this).pointed.value = (this@toUIColor and 0xFF0000).shr(16).toDouble()
        green.getPointer(this).pointed.value = (this@toUIColor and 0x00FF00).shr(8).toDouble()
        blue.getPointer(this).pointed.value = (this@toUIColor and 0x0000FF).toDouble()
        alpha.getPointer(this).pointed.value = 1.0

        return UIColor(
            red = red.getPointer(this).pointed.value / 0xFF.toDouble(),
            green = green.getPointer(this).pointed.value / 0xFF.toDouble(),
            blue = blue.getPointer(this).pointed.value / 0xFF.toDouble(),
            alpha = alpha.getPointer(this).pointed.value
        )
    }
}