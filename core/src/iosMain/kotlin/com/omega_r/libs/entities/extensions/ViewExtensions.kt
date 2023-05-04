package com.omega_r.libs.entities.extensions

import kotlinx.cinterop.CValue
import kotlinx.cinterop.readValue
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRect
import platform.CoreGraphics.CGSize
import platform.UIKit.UIView

var UIView.mutableFrame: CValue<CGRect>
    get() = this.frame
    set(value) {
        this.setFrame(value)
    }

var UIView.mutableSize: CValue<CGSize>
    get() = this.bounds.useContents {
        size.readValue()
    }
    set(value) {
        this.bounds.useContents self@{
            value.useContents value@{
                this@self.size.width = this@value.width
                this@self.size.height = this@value.height
            }
        }
    }

fun UIView.size(): CGSize = this.frame.useContents { size }