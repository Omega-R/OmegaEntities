package com.omega_r.libs.entities.extensions

import io.ktor.utils.io.core.Input
import io.ktor.utils.io.streams.asInput
import java.io.ByteArrayInputStream

actual fun ByteArray.asInput(): Input? = ByteArrayInputStream(this).asInput()