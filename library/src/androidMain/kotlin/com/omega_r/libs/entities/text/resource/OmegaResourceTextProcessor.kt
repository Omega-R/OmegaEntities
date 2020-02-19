package com.omega_r.libs.entities.text.resource

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import com.omega_r.libs.entities.OmegaResource.Text

actual object OmegaResourceTextProcessor : OmegaBaseResourceTextProcessor<OmegaResourceText>() {

    private lateinit var resources: Resources

    fun setResources(resources: Resources) {
        this.resources = resources
    }


    @Suppress("UNCHECKED_CAST")
    override fun extract(resource: Text): CharSequence? {
        return resources.getText(resource.id)
    }

    @Suppress("UNCHECKED_CAST")
    override fun extractWithArgs(resource: Text, formatArgs: Array<out Any>): CharSequence? {
        val formatString = resources.getText(resource.id)

        val htmlFormatString = if (formatString is Spanned) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.toHtml(formatString, Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE)
            } else {
                Html.toHtml(formatString)
            }
        } else {
            Html.escapeHtml(formatString)
        }
        val htmlResultString = Text.format(htmlFormatString, *formatArgs)

        // Convert back to a CharSequence, recovering any of the HTML styling.
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlResultString, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(htmlResultString)
        }
    }

    override fun mapFormatArg(any: Any?): Any {
        return when (val result = super.mapFormatArg(any)) {
            is Spanned -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.toHtml(result, Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE)
                } else {
                    Html.toHtml(result)
                }
            }
            is CharSequence -> {
                Html.escapeHtml(result)
            }
            else -> result
        }
    }




}