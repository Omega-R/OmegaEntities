package com.omega_r.libs.entities.files.java_file

import com.omega_r.libs.entities.files.OmegaFileProcessor
import com.omega_r.libs.entities.files.Type
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.core.Output
import io.ktor.utils.io.streams.asInput
import io.ktor.utils.io.streams.asOutput
import java.io.FileInputStream
import java.io.FileOutputStream

class OmegaJavaFileProcessor : OmegaFileProcessor<OmegaJavaFile> {

    override suspend fun OmegaJavaFile.exist(): Boolean? = file.exists()

    override suspend fun OmegaJavaFile.input(): Input? {
        if (exist() == false) return null
        return when (type) {
            Type.FILE -> FileInputStream(file).asInput()
            Type.FOLDER -> null
        }
    }

    override suspend fun OmegaJavaFile.output(): Output? {
        if (exist() == false) return null
        return when (type) {
            Type.FILE -> FileOutputStream(file).asOutput()
            Type.FOLDER -> null
        }
    }

}