package com.omega_r.libs.entities.images

import com.omega_r.libs.entities.processors.OmegaProcessor
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.Input
import kotlinx.cinterop.CValue
import kotlinx.cinterop.cValue
import kotlinx.cinterop.getRawPointer
import kotlinx.coroutines.CoroutineScope
import platform.CoreGraphics.CGRect
import platform.UIKit.UIColor
import platform.UIKit.UIEdgeInsets
import platform.UIKit.UIImage
import platform.UIKit.UIImageView
import platform.UIKit.UITextView
import platform.UIKit.UIView
import platform.UIKit.UIViewContentMode

actual interface OmegaImageProcessor<T : OmegaImage> : OmegaProcessor<T>, CoroutineScope {

    companion object {

        enum class ICON_POSITION {
            TOP, BOTTOM, LEFT, RIGHT
        }

        fun applyBackground(view: UIView, background: UIImage?) {
            background?.let { image ->
                view.backgroundColor = UIColor(patternImage = image)
            }
        }

        fun applyIconImage(uiTextView: UITextView, icon: UIImage, position: ICON_POSITION) {
            val imageView = UIImageView(image = icon)
            val computedRectangle = createRectangleByIndex(imageView, position)
            imageView.setFrame(computedRectangle)
            imageView.contentMode = UIViewContentMode.UIViewContentModeCenter
            uiTextView.addSubview(imageView)
            uiTextView.textContainerInset = // also compute
        }

        // https://stackoverflow.com/questions/47693654/uitextview-left-image-icon-swift
        private fun createRectangleByIndex(view: UIImageView, position: ICON_POSITION): CValue<CGRect> =
            cValue {
                CGRect(getRawPointer()).let {
                    it.origin.apply {
                        when (position) {
                            ICON_POSITION.TOP -> {
                                x = 0.0
                                y = 2.0
                                with()
                            }
                        }
                    }
                }
            }

    }


    actual suspend fun getInput(
        entity: T,
        extractor: OmegaResourceExtractor,
        format: OmegaImage.Format,
        quality: Int
    ): Input?

}