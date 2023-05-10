package com.omega_r.libs.entities.extensions

import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.CoreGraphics.CGFloatVar
import platform.UIKit.UIColor

fun UIColor.toRgbInt(): Int? = memScoped {
    val red = allocWithInit<CGFloatVar>()
    val green = allocWithInit<CGFloatVar>()
    val blue = allocWithInit<CGFloatVar>()
    val alpha = allocWithInit<CGFloatVar>()
    return if (this@toRgbInt.getRed(red.ptr, green.ptr, blue.ptr, alpha.ptr)) {
        val redInt = (red.value * 255.0f).toInt()
        val greenInt = (green.value * 255.0f).toInt()
        val blueInt = (blue.value * 255.0f).toInt()
        val alphaInt = (alpha.value * 255.0f).toInt()
        (alphaInt shl 24) + (redInt shl 16) + (greenInt shl 8) + blueInt
    } else null
}

fun Int.toUIColor(): UIColor = memScoped {
    val red = allocWithInit<CGFloatVar> { ((this@toUIColor and 0xFF0000) shr 16).toDouble() }
    val green = allocWithInit<CGFloatVar> { ((this@toUIColor and 0x00FF00) shr 8).toDouble() }
    val blue = allocWithInit<CGFloatVar> { (this@toUIColor and 0x0000FF).toDouble() }
    val alpha = allocWithInit<CGFloatVar> { 1.0 }
    return UIColor(
        red = red.value / 0xFF,
        green = green.value / 0xFF,
        blue = blue.value / 0xFF,
        alpha = alpha.value
    )
}