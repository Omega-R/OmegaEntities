package com.omega_r.libs.entities

import com.omega_r.libs.entities.files.OmegaFile
import com.omega_r.libs.entities.files.Type
import com.omega_r.libs.entities.files.url.OmegaUrlFile
import com.omega_r.libs.entities.files.url.from
import kotlinx.coroutines.runBlocking
import org.junit.Test

private const val MIME_TYPE = "png"
private const val FILE_NAME = "1280px-Image_created_with_a_mobile_phone.$MIME_TYPE"
private const val FILE_URL =
    "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/$FILE_NAME"

class OmegaFileTests {

    @Test
    fun urlFileTest() {
        val file = OmegaFile.from(FILE_URL, MIME_TYPE, FILE_NAME)
        assert(file is OmegaUrlFile)
        assert(file.type == Type.FILE)
        runBlocking {
            assert(file.isExist() ?: false)
            assert(file.getOutput() == null)
        }
    }

}