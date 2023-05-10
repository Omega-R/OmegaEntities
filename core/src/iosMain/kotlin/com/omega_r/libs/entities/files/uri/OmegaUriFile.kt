package com.omega_r.libs.entities.files.uri

import com.omega_r.libs.entities.extensions.getMimeType
import com.omega_r.libs.entities.extensions.toByteReadPacket
import com.omega_r.libs.entities.files.OmegaFile
import com.omega_r.libs.entities.files.OmegaFileProcessor
import com.omega_r.libs.entities.files.OmegaFileProcessorsHolder
import com.omega_r.libs.entities.files.Type
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.BytePacketBuilder
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.core.Output
import platform.Foundation.NSInputStream
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.lastPathComponent

class OmegaUriFile(val uri: String, name: String? = null, mimeType: String? = null) : OmegaFile {

    companion object {
        init {
            OmegaFileProcessorsHolder.Default.addProcessor(OmegaUriFile::class, Processor())
        }
    }

    override val type: Type = Type.FILE

    override val mimeType: String = mimeType ?: uri.getMimeType()

    override val name: String = name ?: (NSURL(fileURLWithPath = uri)).lastPathComponent ?: ""

    class Processor : OmegaFileProcessor<OmegaUriFile> {

        override suspend fun isExist(entity: OmegaUriFile, extractor: OmegaResourceExtractor): Boolean =
            NSInputStream(uRL = NSURL(fileURLWithPath = entity.uri)).hasBytesAvailable

        override suspend fun getInput(entity: OmegaUriFile, extractor: OmegaResourceExtractor): Input? =
            entity.uri.toByteReadPacket()

        override suspend fun getOutput(entity: OmegaUriFile, extractor: OmegaResourceExtractor): Output? {
            return entity.uri.toByteReadPacket()?.let {
                val builder = BytePacketBuilder()
                builder.writePacket(it)
                builder
            }
        }
    }
}

fun OmegaFile.Companion.from(uri: String, name: String? = null, mimeType: String? = null) = OmegaUriFile(uri, name, mimeType)