package com.omega_r.libs.entities.images

import com.omega_r.libs.entities.extensions.size
import com.omega_r.libs.entities.extensions.toInputStream
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import platform.CoreGraphics.CGImageRef
import platform.Foundation.NSInputStream
import platform.UIKit.UIImage
import platform.UIKit.UIImageView
import platform.UIKit.UITextView
import platform.UIKit.UIView
import kotlin.math.roundToInt

abstract class BaseBitmapImage : OmegaImage {

    abstract class Processor<B : BaseBitmapImage> : OmegaBaseImageProcessor<B>(), OmegaImageProcessor<B> {

        protected abstract suspend fun getBitmap(
            entity: B,
            extractor: OmegaResourceExtractor,
            width: Double? = null,
            height: Double? = null
        ): CGImageRef?

        override fun applyImage(
            entity: B,
            imageView: UIImageView,
            holder: OmegaImageProcessorsHolder,
            extractor: OmegaResourceExtractor
        ) {
            val width = imageView.size().width
            val height = imageView.size().height
            val processor = OmegaImageProcessorsHolder.current.getProcessor(entity)

            if (width > 0 && height > 0) {
                processor.launch {
                    val bitmap = getBitmap(entity, extractor, width, height)
                    withContext(Dispatchers.Main) {
                        imageView.setImage(
                            UIImage.imageWithCGImage(bitmap)
                        )
                    }
                }
            }
        }

        override fun applyBackground(
            entity: B,
            view: UIView,
            holder: OmegaImageProcessorsHolder,
            extractor: OmegaResourceExtractor
        ) {
            val processor = OmegaImageProcessorsHolder.current.getProcessor(entity)
            processor.launch {
                val bitmap = getBitmap(entity, extractor, view.size().width, view.size().height)
                withContext(Dispatchers.Main) {
                    OmegaImageProcessor.applyBackground(view, UIImage.imageWithCGImage(bitmap))
                }
            }
        }

        override fun applyCompoundImage(
            entity: B,
            index: Int,
            textView: UITextView,
            holder: OmegaImageProcessorsHolder,
            extractor: OmegaResourceExtractor
        ) {
            val processor = OmegaImageProcessorsHolder.current.getProcessor(entity)
            processor.launch {
                val bitmap = getBitmap(entity, extractor, textView.size().width, textView.size().height)
                val image = UIImage.imageWithCGImage(bitmap)
                withContext(Dispatchers.Main) {
                    OmegaImageProcessor.applyIconImage(
                        textView,
                        image,
                        textView.size().height.roundToInt(),
                        OmegaImageProcessor.Companion.IconPosition.values()[index]
                    )
                }
            }
        }

        // might fail, test it later
        override suspend fun getInputStream(
            entity: B,
            extractor: OmegaResourceExtractor,
            format: OmegaImage.Format,
            quality: Int
        ): NSInputStream? {
            val bitmap = getBitmap(entity, extractor)
            val image = UIImage.imageWithCGImage(bitmap)
            return image.toInputStream(format, quality)
        }

    }

}