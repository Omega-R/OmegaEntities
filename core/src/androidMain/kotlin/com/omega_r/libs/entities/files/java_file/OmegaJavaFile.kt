package com.omega_r.libs.entities.files.java_file

import android.webkit.MimeTypeMap
import com.omega_r.libs.entities.files.OmegaFile
import com.omega_r.libs.entities.files.OmegaFileProcessor
import com.omega_r.libs.entities.files.OmegaFileProcessorsHolder
import com.omega_r.libs.entities.files.Type
import com.omega_r.libs.entities.files.Type.FILE
import com.omega_r.libs.entities.files.Type.FOLDER
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.core.Output
import io.ktor.utils.io.streams.asInput
import io.ktor.utils.io.streams.asOutput
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

private const val DEFAULT_MIME_TYPE = "*/*"

data class OmegaJavaFile(val file: java.io.File) : OmegaFile {

    companion object {
        init {
            OmegaFileProcessorsHolder.Default.addProcessor(OmegaJavaFile::class, Processor())
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

    class Processor : OmegaFileProcessor<OmegaJavaFile> {

        override suspend fun isExist(entity: OmegaJavaFile, extractor: OmegaResourceExtractor): Boolean? = entity.file.exists()

        override suspend fun getInput(entity: OmegaJavaFile, extractor: OmegaResourceExtractor): Input? {
            if (isExist(entity, extractor) == false) return null
            return when (entity.type) {
                Type.FILE -> FileInputStream(entity.file).asInput()
                Type.FOLDER -> null
            }
        }

        override suspend fun getOutput(entity: OmegaJavaFile, extractor: OmegaResourceExtractor): Output? {
            if (isExist(entity, extractor) == false) return null
            return when (entity.type) {
                Type.FILE -> FileOutputStream(entity.file).asOutput()
                Type.FOLDER -> null
            }
        }

    }

}

@Suppress("unused")
fun OmegaFile.Companion.from(file: java.io.File): OmegaFile = OmegaJavaFile(file)