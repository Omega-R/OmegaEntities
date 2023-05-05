package com.omega_r.libs.entities.text

import platform.Foundation.NSAttributedString
import platform.Foundation.NSCoder
import platform.Foundation.create

class NSSpannableString : NSAttributedString() {

    companion object {
        fun create(text: CharSequence = ""): NSSpannableString {
            return NSAttributedString.create(text as String) as NSSpannableString
        }

        fun createAndFormat(source: CharSequence, args: Any?) = create(source).format(args)
    }

    operator fun get(index: Int): Char = string()[index]

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