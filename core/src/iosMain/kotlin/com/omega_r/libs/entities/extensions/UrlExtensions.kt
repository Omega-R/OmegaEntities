package com.omega_r.libs.entities.extensions

import io.ktor.utils.io.core.ByteReadPacket
import kotlinx.cinterop.CArrayPointer
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.get
import kotlinx.cinterop.nativeHeap
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
