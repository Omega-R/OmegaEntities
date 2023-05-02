package com.omega_r.libs.entities.files.url

import com.omega_r.libs.entities.extensions.getMimeType
import com.omega_r.libs.entities.files.OmegaFile
import com.omega_r.libs.entities.files.Type
import platform.CoreFoundation.CFStringRef
import platform.CoreServices.UTTypeCopyPreferredTagWithClass
import platform.CoreServices.UTTypeCreatePreferredIdentifierForTag
import platform.CoreServices.kUTTagClassFilenameExtension
import platform.CoreServices.kUTTagClassMIMEType
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.lastPathComponent
import platform.Foundation.pathExtension

actual class OmegaUrlFile(
    actual val url: String,
    mimeType: String? = null,
    name: String? = null
) : OmegaFile {

    override val type: Type = Type.FILE

    override val mimeType: String = mimeType ?: url.getMimeType()

    // not sure whether it will work
    override val name: String = name ?: (NSURL(fileURLWithPath = url) as NSString).lastPathComponent

}

fun OmegaFile.Companion.from(url: String, mimeType: String? = null, name: String? = null): OmegaFile =
    OmegaUrlFile(url, name, mimeType)