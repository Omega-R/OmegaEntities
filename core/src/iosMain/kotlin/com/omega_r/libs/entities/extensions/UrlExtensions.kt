package com.omega_r.libs.entities.extensions

import io.ktor.utils.io.core.ByteReadPacket
import io.ktor.utils.io.core.Input
import kotlinx.cinterop.CArrayPointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CPointerVar
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.get
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.pointed
import kotlinx.cinterop.value
import platform.CoreFoundation.CFStringRef
import platform.CoreServices.UTTypeCopyPreferredTagWithClass
import platform.CoreServices.UTTypeCreatePreferredIdentifierForTag
import platform.CoreServices.kUTTagClassFilenameExtension
import platform.CoreServices.kUTTagClassMIMEType
import platform.Foundation.NSFileManager
import platform.Foundation.NSInputStream
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.pathExtension
import platform.darwin.NSUInteger
import platform.darwin.NSUIntegerVar
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

// likely it won't do anything useful, re-check execution
fun NSInputStream.asInput(): Input? {
    memScoped {
        var bufferRef: CPointer<CPointerVar<uint8_tVar>>? = null
        var bufferLengthRef: CPointer<NSUIntegerVar>? = null
        bufferLengthRef?.getPointer(this)?.pointed?.value = 1u
        getBuffer(bufferRef, bufferLengthRef)
        val value = bufferLengthRef?.getPointer(this)?.pointed?.value ?: 0u
        val array = bufferRef?.getPointer(this)?.pointed?.value
        this@asInput.read(array, value)
        if (value < 1u) return null
        ByteReadPacket(
            (0 until value.toInt()).map { array[it] }.toUByteArray().toByteArray()
        )
    }

}
