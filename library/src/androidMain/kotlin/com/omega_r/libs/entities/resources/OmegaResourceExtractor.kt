package com.omega_r.libs.entities.resources

import android.annotation.ColorInt
import android.app.ActivityThread
import android.content.ContentResolver
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import java.lang.ref.WeakReference
import java.util.*

actual open class OmegaResourceExtractor {

    companion object {

        private val cache = WeakHashMap<Context, OmegaResourceExtractor>()

        fun from(context: Context): OmegaResourceExtractor {
            return cache[context] ?: run {
                val newExtractor = Local(context)
                cache[context] = newExtractor
                newExtractor
            }

        }

    }

    protected open lateinit var contextReference: WeakReference<Context>

    protected open val context: Context?
        get() = contextReference.get()

    val contentResolver: ContentResolver?
        get() = context?.contentResolver

    actual open fun getCharSequence(resource: OmegaResource.Text): CharSequence? = context?.getText(resource.id)

    actual open fun getPluralsChatSequence(resource: OmegaResource.Plurals, quantity: Int): CharSequence? {
        return context?.resources?.getQuantityText(resource.id, quantity)
    }

    actual open fun getCharSequenceArray(resource: OmegaResource.TextArray): Array<CharSequence> {
        return context?.resources?.getTextArray(resource.id) ?: emptyArray()
    }

    @ColorInt
    actual open fun getColorInt(resource: OmegaResource.Color): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context?.getColor(resource.id) ?: 0
        } else {
            context?.resources?.getColor(resource.id) ?: 0
        }
    }

    open fun getDrawable(resource: OmegaResource.Image): Drawable? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.L) {
            context?.getDrawable(resource.id)
        } else {
            context?.resources?.getDrawable(resource.id)
        }
    }

    actual object Default : OmegaResourceExtractor() {

        override val context: Context?
            get() = ActivityThread.currentApplication()

    }

    private class Local(context: Context) : OmegaResourceExtractor() {

        init {
            contextReference = WeakReference(context)
        }

    }

}