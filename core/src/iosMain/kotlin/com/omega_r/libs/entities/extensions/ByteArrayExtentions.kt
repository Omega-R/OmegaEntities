package com.omega_r.libs.entities.extensions

import io.ktor.utils.io.core.ByteReadPacket
import io.ktor.utils.io.core.Input
import kotlin.collections.toByteArray

actual fun ByteArray.asInput(): Input? = ByteReadPacket(this)

@OptIn(ExperimentalUnsignedTypes::class)
fun Collection<UByte>.toByteArray(): ByteArray = this.toUByteArray().toByteArray()