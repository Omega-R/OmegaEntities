package com.omega_r.libs.entities.images

import com.omega_r.libs.entities.extensions.size
import com.omega_r.libs.entities.extensions.toInputStream
import com.omega_r.libs.entities.resources.OmegaResource
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import platform.Foundation.NSInputStream
import platform.UIKit.UIImage
import platform.UIKit.UIImageView
import platform.UIKit.UITextView
import platform.UIKit.UIView
import kotlin.math.roundToInt

actual class OmegaResourceImage(actual val resource: OmegaResource.Image) : OmegaImage {

    companion object {
        init {
            OmegaImageProcessorsHolder.Default.addProcessor(OmegaResourceImage::class, Processor())
        }
    }

    constructor(
        name: String,
        type: OmegaResource.ImageResourceType = OmegaResource.ImageResourceType.EXTERNAL
    ) : this(OmegaResource.Image(name, type))

    class Processor : OmegaBaseImageProcessor<OmegaResourceImage>() {

        override suspend fun getInputStream(
            entity: OmegaResourceImage,
            extractor: OmegaResourceExtractor,
            format: OmegaImage.Format,
            quality: Int
        ): NSInputStream? = entity.toUIImage()?.toInputStream(format, quality)

        override fun applyImage(
            entity: OmegaResourceImage,
            imageView: UIImageView,
            holder: OmegaImageProcessorsHolder,
            extractor: OmegaResourceExtractor
        ) {
            imageView.setImage(entity.toUIImage())
        }

        override fun applyBackground(
            entity: OmegaResourceImage,
            view: UIView,
            holder: OmegaImageProcessorsHolder,
            extractor: OmegaResourceExtractor
        ) {
            val processor = OmegaImageProcessorsHolder.current.getProcessor(entity)
            processor.launch {
                withContext(Dispatchers.Main) {
                    OmegaImageProcessor.applyBackground(view, entity.toUIImage())
                }
            }
        }

        override fun applyCompoundImage(
            entity: OmegaResourceImage,
            index: Int,
            textView: UITextView,
            holder: OmegaImageProcessorsHolder,
            extractor: OmegaResourceExtractor
        ) {
            val processor = OmegaImageProcessorsHolder.current.getProcessor(entity)
            processor.launch {
                withContext(Dispatchers.Main) {
                    entity.toUIImage()?.let { img ->
                        OmegaImageProcessor.applyIconImage(
                            textView,
                            img,
                            textView.size().height.roundToInt(),
                            OmegaImageProcessor.Companion.IconPosition.values()[index]
                        )
                    }
                }
            }
        }

        private fun OmegaResourceImage.toUIImage(): UIImage? =
            if (this.resource.imageResourceType == OmegaResource.ImageResourceType.SYSTEM) {
                UIImage.systemImageNamed(this.resource.id)
            } else UIImage.imageNamed(this.resource.id)

    }

}