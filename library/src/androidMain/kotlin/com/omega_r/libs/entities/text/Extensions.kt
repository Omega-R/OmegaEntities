package com.omega_r.libs.entities.text

import android.annotation.StringRes
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.widget.TextView
import com.omega_r.libs.entities.OmegaResource

fun TextView.setText(text: OmegaText) {
    this@setText.text = text.getCharSequence()
}

fun OmegaText.Companion.from(@StringRes id: Int): OmegaText = from(OmegaResource.Text(id))

fun Spanned.toHtmlString(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.toHtml(this, Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE)
    } else {
        Html.toHtml(this)
    }
}

fun String.fromHtmlString(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}

fun CharSequence.toHtmlString(): String {
    return when (this) {
        is Spanned -> toHtmlString()
        else -> Html.escapeHtml(this)
    }
}
