package com.omega_r.libs.entities.files.java_file

import android.webkit.MimeTypeMap
import com.omega_r.libs.entities.files.OmegaFile
import com.omega_r.libs.entities.files.OmegaFileProcessorsHolder
import com.omega_r.libs.entities.files.Type
import com.omega_r.libs.entities.files.Type.FILE
import com.omega_r.libs.entities.files.Type.FOLDER
import java.util.*

private const val DEFAULT_MIME_TYPE = "*/*"

data class OmegaJavaFile(val file: java.io.File) : OmegaFile {

    companion object {
        init {
            OmegaFileProcessorsHolder.Default.addProcessor(OmegaJavaFile::class, OmegaJavaFileProcessor())
        }
    }

    override val type: Type
        get() = if (file.isFile) FILE else FOLDER

    override val mimeType: String
        get() = MimeTypeMap.getFileExtensionFromUrl(file.toString())
                ?.apply { MimeTypeMap.getSingleton().getMimeTypeFromExtension(toLowerCase(Locale.US)) }
                ?: DEFAULT_MIME_TYPE

    override val name: String
        get() = file.name

}

@Suppress("unused")
fun OmegaFile.Companion.from(file: java.io.File): OmegaFile = OmegaJavaFile(file)