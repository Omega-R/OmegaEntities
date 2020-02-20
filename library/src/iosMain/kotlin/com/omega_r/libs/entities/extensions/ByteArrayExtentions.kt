package com.omega_r.libs.entities.extensions

import io.ktor.utils.io.core.ByteReadPacket
import io.ktor.utils.io.core.Input

actual fun ByteArray.asInput(): Input? = ByteReadPacket(this)