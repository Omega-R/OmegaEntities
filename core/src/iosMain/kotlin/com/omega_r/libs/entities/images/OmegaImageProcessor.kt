package com.omega_r.libs.entities.images

import com.omega_r.libs.entities.extensions.mutableFrame
import com.omega_r.libs.entities.extensions.mutableSize
import com.omega_r.libs.entities.extensions.size
import com.omega_r.libs.entities.processors.OmegaProcessor
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.Input
import kotlinx.cinterop.CValue
import kotlinx.cinterop.cValue
import kotlinx.coroutines.CoroutineScope
import platform.CoreGraphics.CGRect
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSizeMake
import platform.UIKit.UIColor
import platform.UIKit.UIImage
import platform.UIKit.UIImageView
import platform.UIKit.UITextView
import platform.UIKit.UIView

actual interface OmegaImageProcessor<T : OmegaImage> : OmegaProcessor<T>, CoroutineScope {

    companion object {

        private const val IMAGE_PADDING: Int = 3
        private const val IMAGE_ARRAY_SIZE: Int = 4

        enum class IconPosition {
            LEFT, TOP, RIGHT, BOTTOM
        }

        fun applyBackground(view: UIView, background: UIImage?) {
            background?.let { image ->
                view.backgroundColor = UIColor(patternImage = image)
            }
        }

        fun applyIconImage(uiTextView: UITextView, image: UIImage, iconSize: Int, position: IconPosition) {
            val uiImageView = UIImageView(image = image)
            uiImageView.mutableFrame = computeRectangleByPosition(
                position,
                iconSize,
                uiImageView.size().height.toFloat(),
                uiImageView.size().width.toFloat()
            )
            uiTextView.addSubview(uiImageView)
            uiTextView.mutableSize = when (position) {
                IconPosition.TOP, IconPosition.BOTTOM -> CGSizeMake(
                    uiTextView.size().width,
                    uiTextView.size().height + iconSize + IMAGE_PADDING
                )

                IconPosition.LEFT, IconPosition.RIGHT -> CGSizeMake(
                    uiTextView.size().width + iconSize + IMAGE_PADDING,
                    uiTextView.size().height
                )
            }
            uiTextView.updateTextContainerInsets(iconSize, position)
        }

        fun applyIconImages(uiTextView: UITextView, images: Array<UIImage?>, iconSize: Int) {
            assert(images.size == IMAGE_ARRAY_SIZE)
            images.forEachIndexed { index, image ->
                if (image != null) {
                    applyIconImage(uiTextView, image, iconSize, IconPosition.values()[index])
                }
            }
        }

        private fun computeRectangleByPosition(
            position: IconPosition,
            iconSize: Int,
            textHeight: Float,
            textWidth: Float
        ): CValue<CGRect> =
            when (position) {
                IconPosition.TOP -> makeCGRect((textWidth / 2 - iconSize / 2), 0, iconSize, iconSize)
                IconPosition.BOTTOM -> makeCGRect((textWidth / 2 - iconSize / 2), textHeight - iconSize, iconSize, iconSize)
                IconPosition.LEFT -> makeCGRect(0, 0, iconSize, iconSize)
                IconPosition.RIGHT -> makeCGRect(textWidth + IMAGE_PADDING, 0, iconSize, iconSize)
            }

        private fun UITextView.updateTextContainerInsets(iconSize: Int, position: IconPosition) {
            textContainerInset = cValue {
                when (position) {
                    IconPosition.LEFT -> {
                        left = iconSize.toDouble() + IMAGE_PADDING
                    }

                    IconPosition.TOP -> {
                        top = iconSize.toDouble() + IMAGE_PADDING
                    }

                    IconPosition.BOTTOM -> {
                        bottom = iconSize.toDouble() + IMAGE_PADDING
                    }
                    IconPosition.RIGHT -> { }
                }
            }
        }

        private fun makeCGRect(x: Number, y: Number, width: Number, height: Number) =
            CGRectMake(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble())

    }

    actual suspend fun getInput(
        entity: T,
        extractor: OmegaResourceExtractor,
        format: OmegaImage.Format,
        quality: Int
    ): Input?

    fun applyImage(
        entity: T,
        imageView: UIImageView,
        holder: OmegaImageProcessorsHolder = OmegaImageProcessorsHolder.Default,
        extractor: OmegaResourceExtractor = OmegaResourceExtractor.Default
    )

    fun applyBackground(
        entity: T,
        view: UIView,
        holder: OmegaImageProcessorsHolder = OmegaImageProcessorsHolder.Default,
        extractor: OmegaResourceExtractor = OmegaResourceExtractor.Default
    )

    fun applyCompoundImage(
        entity: T,
        index: Int,
        textView: UITextView,
        holder: OmegaImageProcessorsHolder = OmegaImageProcessorsHolder.Default,
        extractor: OmegaResourceExtractor = OmegaResourceExtractor.Default
    )

    fun preload(entity: T, extractor: OmegaResourceExtractor)

}