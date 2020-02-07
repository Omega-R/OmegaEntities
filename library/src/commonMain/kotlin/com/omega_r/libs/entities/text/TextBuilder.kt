package com.omega_r.libs.entities.text

private const val DEFAULT_CAPACITY = 10

class TextBuilder(capacity: Int) {

    private val list: MutableList<OmegaText> = ArrayList(capacity)

    constructor() : this(DEFAULT_CAPACITY)

    constructor(list: List<OmegaText>) : this(list.size + DEFAULT_CAPACITY) {
        this.list.addAll(list)
    }

    constructor(vararg text: OmegaText) : this(text.size + DEFAULT_CAPACITY) {
        list.addAll(text)
    }

    fun append(text: OmegaText): TextBuilder {
        list += text
        return this
    }

    fun append(value: String) = append(
        OmegaText.from(
            value
        )
    )

    fun append(stringHolder: OmegaStringHolder) = append(
        OmegaText.from(stringHolder)
    )

    fun append(textHolder: OmegaTextHolder) = append(textHolder.text)

    fun append(vararg texts: OmegaText) = append(
        OmegaText.from(*texts)
    )

    fun insert(text: OmegaText, index: Int): TextBuilder {
        list.add(index, text)
        return this
    }

    fun toText(): OmegaText = OmegaText.from(list)

    fun build() = toText()

    class BuilderOmegaText(private val builder: TextBuilder) : OmegaText {

        constructor(text: OmegaText) : this(TextBuilder(text))

        constructor() : this(TextBuilder())

        override val text: OmegaText
            get() = builder.build()

        override val isEmpty: Boolean
            get() {
                val list = builder.list
                if (list.isEmpty()) return true
                list.forEach {
                    if (!it.isEmpty) return false
                }
                return true
            }

        fun insert(text: OmegaText, index: Int): BuilderOmegaText {
            builder.insert(text, index)
            return this
        }

        override operator fun plus(text: OmegaText): OmegaText {
            builder.append(text)
            return this
        }

        override operator fun plus(string: String): OmegaText {
            builder.append(string)
            return this
        }

    }

}