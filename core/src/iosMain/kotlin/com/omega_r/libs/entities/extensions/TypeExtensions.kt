package com.omega_r.libs.entities.extensions

import kotlinx.cinterop.AutofreeScope
import kotlinx.cinterop.BooleanVarOf
import kotlinx.cinterop.ByteVarOf
import kotlinx.cinterop.CPrimitiveVar
import kotlinx.cinterop.DoubleVarOf
import kotlinx.cinterop.FloatVarOf
import kotlinx.cinterop.IntVarOf
import kotlinx.cinterop.LongVarOf
import kotlinx.cinterop.ShortVarOf
import kotlinx.cinterop.UByteVarOf
import kotlinx.cinterop.UIntVarOf
import kotlinx.cinterop.ULongVarOf
import kotlinx.cinterop.UShortVarOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.value

inline fun <reified T: CPrimitiveVar> AutofreeScope.allocWithInit(noinline init: (() -> Comparable<*>)? = null) = alloc<T> {
    when(this) {
        is BooleanVarOf<*> -> this.value = if (init != null) { init() as Boolean } else false
        is ByteVarOf<*> -> this.value =  if (init != null) { init() as Byte } else 0
        is DoubleVarOf<*> -> this.value =  if (init != null) { init() as Double } else 0.0
        is FloatVarOf<*> -> this.value =  if (init != null) { init() as Float } else 0f
        is IntVarOf<*> -> this.value = if (init != null) { init() as Int } else 0
        is LongVarOf<*> -> this.value = if (init != null) { init() as Long } else 0L
        is ShortVarOf<*> -> this.value = if (init != null) { init() as Short } else 0
        is UByteVarOf<*> -> this.value =  if (init != null) { init() as UByte } else 0u
        is ULongVarOf<*> -> this.value =  if (init != null) { init() as ULong } else 0u
        is UIntVarOf<*> -> this.value = if (init != null) { init() as UInt } else 0u
        is UShortVarOf<*> -> this.value =  if (init != null) { init() as UShort } else 0u
    }
}