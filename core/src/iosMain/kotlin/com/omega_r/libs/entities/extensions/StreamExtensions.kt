package com.omega_r.libs.entities.extensions

import com.omega_r.libs.entities.images.OmegaImage
import io.ktor.utils.io.core.ByteReadPacket
import io.ktor.utils.io.core.Input
import kotlinx.cinterop.CArrayPointer
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.cValue
import kotlinx.cinterop.cValuesOf
import kotlinx.cinterop.get
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.pointed
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.value
import platform.CoreFoundation.CFStringRef
import platform.CoreFoundation.CFTypeRef
import platform.CoreGraphics.CGDataProviderCopyData
import platform.CoreGraphics.CGImageGetDataProvider
import platform.CoreServices.UTTypeCopyPreferredTagWithClass
import platform.CoreServices.UTTypeCreatePreferredIdentifierForTag
import platform.CoreServices.kUTTagClassFilenameExtension
import platform.CoreServices.kUTTagClassMIMEType
import platform.Foundation.CFBridgingRelease
import platform.Foundation.NSData
import platform.Foundation.NSFileManager
import platform.Foundation.NSInputStream
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.pathExtension
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePNGRepresentation
import platform.darwin.NSUInteger
import platform.darwin.NSUIntegerVar
import platform.posix.id_t
import platform.posix.id_tVar
import platform.posix.uint8_tVar

fun String.getMimeType(): String {
    val pathExtension = NSURL(fileURLWithPath = this).pathExtension ?: ""

    // try to cast directly to CFStringRef
    UTTypeCreatePreferredIdentifierForTag(
        kUTTagClassFilenameExtension,
        (pathExtension as NSString) as CFStringRef,
        null
    )?.let { uti ->
        UTTypeCopyPreferredTagWithClass(
            uti,
            kUTTagClassMIMEType
        )?.let {
            // try to cast directly to String
            return (it as NSString) as String
        }
    }
    return "application/octet-stream"
}

@OptIn(ExperimentalUnsignedTypes::class)
fun String.toByteReadPacket(): ByteReadPacket? {
    val url = NSURL(fileURLWithPath = this)
    val stream = NSInputStream(uRL = url)
    val fileSize = NSFileManager().attributesOfItemAtPath(url.path ?: "", null)?.size ?: 0
    if (!stream.hasBytesAvailable) return null

    memScoped {
        val buffer: CArrayPointer<uint8_tVar> = nativeHeap.allocArray(fileSize)
        stream.read(buffer, fileSize as NSUInteger)

        return try {
            ByteReadPacket(
                (0 until fileSize).map { buffer[it] }.toUByteArray().toByteArray()
            )
        } catch (exc: Exception) {
            null
        }
    }
}

@OptIn(ExperimentalUnsignedTypes::class)
fun NSInputStream.asInput(): Input? {
    memScoped {
        val bufferRef = cValuesOf<uint8_tVar>()
        val bufferLengthRef = cValue<NSUIntegerVar>().also { it.getPointer(this).pointed.value = 1u }

        getBuffer(bufferRef.getPointer(this), bufferLengthRef.getPointer(this))
        val size = bufferLengthRef.getPointer(this).pointed.value
        val array = bufferRef.getPointer(this).pointed.value
        if (size < 1u || array == null) return null

        // might fail if elements are moved, not copied
        return ByteReadPacket(
            (0 until size.toInt()).map { array[it] }.toUByteArray().toByteArray()
        )
    }
}

fun UIImage.toInputStream(format: OmegaImage.Format, quality: Int): NSInputStream? {
    val data = when (format) {
        OmegaImage.Format.JPEG -> UIImageJPEGRepresentation(this, quality.toDouble())
        OmegaImage.Format.PNG -> UIImagePNGRepresentation(this)
        else -> {
            memScoped {
                val provider = CGImageGetDataProvider(this@toInputStream.CGImage)
                val pointer = CFBridgingRelease(CGDataProviderCopyData(provider)) as id_t
                val pointed = (pointer as CFTypeRef).reinterpret<id_tVar>().pointed
                pointed as NSData
            }
        }
    }
    return if (data != null) NSInputStream(data) else null
}