package com.omega_r.libs.entities.extensions

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import com.omega_r.libs.entities.resources.OmegaResource
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import com.omega_r.libs.entities.text.OmegaText

fun OmegaText.Companion.from(@StringRes id: Int): OmegaText = from(OmegaResource.Text(id))

fun OmegaText.Companion.from(@StringRes id: Int, vararg formatArgs: Any): OmegaText =
    from(OmegaResource.Text(id), *formatArgs)

fun OmegaText.Companion.from(@StringRes id: Int, quantity: Int, vararg formatArgs: Any): OmegaText =
    from(OmegaResource.Plurals(id), quantity, *formatArgs)

fun TextView.setText(text: OmegaText) {
    this@setText.text = text.getCharSequence(extractor)
}

val Context.extractor: OmegaResourceExtractor
    get() = OmegaResourceExtractor.from(this)

val View.extractor: OmegaResourceExtractor
    get() = context.extractor

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