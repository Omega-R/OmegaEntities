package com.omega_r.libs.entities.text

import kotlin.jvm.JvmOverloads

private const val DEFAULT_CAPACITY = 10

class OmegaTextBuilder @JvmOverloads constructor(capacity: Int = DEFAULT_CAPACITY) {

    private val list: MutableList<OmegaText> = ArrayList(capacity)

    constructor(list: List<OmegaText>) : this(list.size + DEFAULT_CAPACITY) {
        this.list.addAll(list)
    }

    constructor(vararg text: OmegaText) : this(text.size + DEFAULT_CAPACITY) {
        list.addAll(text)
    }

    fun append(text: OmegaText): OmegaTextBuilder = apply {
        list += text
    }

    fun append(value: String) = append(OmegaText.from(value))

    fun append(stringHolder: OmegaStringHolder) = append(OmegaText.from(stringHolder))

    fun append(textHolder: OmegaTextHolder) = append(textHolder.text)

    fun append(vararg texts: OmegaText) = append(OmegaText.from(*texts))

    fun insert(text: OmegaText, index: Int): OmegaTextBuilder {
        list.add(index, text)
        return this
    }

    fun toText(): OmegaText = OmegaText.from(list)

    fun build() = toText()

}