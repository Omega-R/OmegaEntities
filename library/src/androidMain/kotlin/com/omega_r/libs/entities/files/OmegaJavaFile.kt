package com.omega_r.libs.entities.files

import android.webkit.MimeTypeMap
import com.omega_r.libs.entities.files.Type.FILE
import com.omega_r.libs.entities.files.Type.FOLDER
import kotlinx.io.core.Input
import kotlinx.io.core.Output
import kotlinx.io.streams.asInput
import kotlinx.io.streams.asOutput
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

private const val DEFAULT_MIME_TYPE = "*/*"

data class OmegaJavaFile(val file: java.io.File) : OmegaFile {

    companion object {
        init {
            FileProcessorsHolder.addProcessor(OmegaJavaFile::class, JavaFileInputProcessor())
            FileOutputProcessorsHolder.addProcessor(OmegaJavaFile::class, JavaFileOutputProcessor())
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

    class JavaFileInputProcessor : OmegaFileInputProcessor<OmegaJavaFile> {

        override fun OmegaJavaFile.extract(): Input? {
            if (!file.exists()) return null
            return when (type) {
                FILE -> FileInputStream(file).asInput()
                FOLDER -> null
            }
        }

    }

    class JavaFileOutputProcessor : OmegaFileOutputProcessor<OmegaJavaFile> {

        override fun OmegaJavaFile.extract(): Output? {
            if (!file.exists()) return null
            return when (type) {
                FILE -> FileOutputStream(file).asOutput()
                FOLDER -> null
            }
        }

    }

}

@Suppress("unused")
fun OmegaFile.Companion.from(file: java.io.File): OmegaFile = OmegaJavaFile(file)