package com.omega_r.libs.entities.extensions

import com.omega_r.libs.entities.images.OmegaImage
import io.ktor.utils.io.core.ByteReadPacket
import io.ktor.utils.io.core.Input
import kotlinx.cinterop.CArrayPointer
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.cValuesOf
import kotlinx.cinterop.get
import kotlinx.cinterop.getBytes
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.value
import platform.CoreFoundation.CFStringRef
import platform.CoreGraphics.CGDataProviderCopyData
import platform.CoreGraphics.CGImageGetDataProvider
import platform.CoreServices.UTTypeCopyPreferredTagWithClass
import platform.CoreServices.UTTypeCreatePreferredIdentifierForTag
import platform.CoreServices.kUTTagClassFilenameExtension
import platform.CoreServices.kUTTagClassMIMEType
import platform.Foundation.CFBridgingRelease
import platform.Foundation.CFBridgingRetain
import platform.Foundation.NSData
import platform.Foundation.NSFileManager
import platform.Foundation.NSInputStream
import platform.Foundation.NSURL
import platform.Foundation.pathExtension
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePNGRepresentation
import platform.UniformTypeIdentifiers.UTType
import platform.darwin.NSUIntegerVar
import platform.posix.id_t
import platform.posix.uint8_tVar

fun String.toByteReadPacket(): ByteReadPacket? {
    val url = NSURL(fileURLWithPath = this)
    val stream = NSInputStream(uRL = url)
    val fileSize = NSFileManager().attributesOfItemAtPath(url.path ?: "", null)?.size ?: 0
    if (!stream.hasBytesAvailable) return null

    val buffer: CArrayPointer<uint8_tVar> = nativeHeap.allocArray(fileSize)
    stream.read(buffer, fileSize.toULong())

    return try {
        ByteReadPacket(
            (0 until fileSize).map { buffer[it] }.toByteArray()
        )
    } catch (exc: Exception) {
        null
    }
}

fun NSInputStream.asInput(): Input? = memScoped {

    val bufferRef = cValuesOf<uint8_tVar>()
    val bufferLengthRef = allocWithInit<NSUIntegerVar> { 1u }

    getBuffer(bufferRef.getPointer(this), bufferLengthRef.ptr)
    val size = bufferLengthRef.value
    val array = bufferRef.getPointer(this).pointed.value
    if (size < 1u || array == null) return null

    return ByteReadPacket(bufferRef.getBytes())
}

fun UIImage.toInputStream(format: OmegaImage.Format, quality: Int): NSInputStream? {
    val data = when (format) {
        OmegaImage.Format.JPEG -> UIImageJPEGRepresentation(this, quality.toDouble())
        OmegaImage.Format.PNG -> UIImagePNGRepresentation(this)
        else -> {
            val provider = CGImageGetDataProvider(this@toInputStream.CGImage)
            val dataReference = CFBridgingRetain(CFBridgingRelease(CGDataProviderCopyData(provider)) as id_t)
            CFBridgingRelease(dataReference) as NSData
        }
    }
    return if (data != null) NSInputStream(data) else null
}