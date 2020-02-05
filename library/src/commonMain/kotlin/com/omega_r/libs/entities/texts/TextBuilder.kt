package com.omega_r.libs.entities.texts

private const val DEFAULT_CAPACITY = 10

class TextBuilder(capacity: Int) {

    private val list: MutableList<Text> = ArrayList(capacity)

    constructor(): this(DEFAULT_CAPACITY)

    constructor(list: List<Text>): this(list.size + DEFAULT_CAPACITY) {
        this.list.addAll(list)
    }

    constructor(vararg text: Text): this(text.size + DEFAULT_CAPACITY) {
        list.addAll(text)
    }

    fun append(text: Text): TextBuilder {
        list += text
        return this
    }

    fun append(value: String) = append(Text.from(value))

    fun append(stringHolder: StringHolder) = append(Text.from(stringHolder))

    fun append(value: TextHolder) = append(value.getText())

    fun append(vararg texts: Text) = append(Text.from(*texts))

    fun insert(text: Text, index: Int): TextBuilder {
        list.add(index, text)
        return this
    }

    fun toText(): Text = Text.from(list)

    fun build() = toText()

    class BuilderText(private val builder: TextBuilder) : Text {

        constructor(text: Text): this(TextBuilder(text))

        constructor(): this(TextBuilder())

        override val isEmpty: Boolean
            get() {
                val list = builder.list
                if (list.isEmpty()) return true
                list.forEach {
                    if (!it.isEmpty) return false
                }
                return true
            }

        fun insert(text: Text, index: Int): BuilderText {
            builder.insert(text, index)
            return this
        }

        override operator fun plus(text: Text): Text {
            builder.append(text)
            return this
        }

        override operator fun plus(string: String): Text {
            builder.append(string)
            return this
        }

        override fun getText(): Text = builder.build()

    }

}