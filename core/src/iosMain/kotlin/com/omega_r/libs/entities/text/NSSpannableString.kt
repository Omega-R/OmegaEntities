package com.omega_r.libs.entities.text

import platform.Foundation.NSAttributedString
import platform.Foundation.NSCoder
import platform.Foundation.create

class NSSpannableString : NSAttributedString(NSCoder()), CharSequence {

    companion object {
        fun create(text: CharSequence = ""): NSSpannableString {
            return NSAttributedString.create(text as String) as NSSpannableString
        }

        fun createFormattedString(source: CharSequence, args: Any?) = create(source).format(args)
    }

    override val length: Int
        get() = string().length

    override operator fun get(index: Int): Char = string()[index]

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence = string().subSequence(startIndex, endIndex)

    operator fun plus(another: CharSequence): NSSpannableString {
        return create(this.string.plus(another))
    }

    @Suppress("UNCHECKED_CAST")
    fun format(args: Any?): NSSpannableString = try {
        NSAttributedString.create(string = this.string, attributes = args as Map<Any?, *>) as NSSpannableString
    } catch (e: ClassCastException) {
        this
    }

}