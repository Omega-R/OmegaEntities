package com.omega_r.libs.entities.resources

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.pm.ProviderInfo
import android.database.Cursor
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.annotation.ColorInt
import com.omega_r.libs.entities.extensions.NO_PLACEHOLDER_RES
import com.omega_r.libs.entities.images.OmegaImage
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

    open val context: Context?
        get() = contextReference.get()

    val contentResolver: ContentResolver?
        get() = context?.contentResolver

    actual open fun getCharSequence(resource: OmegaResource.Text): CharSequence? =
        context?.getText(resource.id)

    actual open fun getPluralsChatSequence(
        resource: OmegaResource.Plurals,
        quantity: Int
    ): CharSequence? {
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
        if (resource.id == OmegaImage.NO_PLACEHOLDER_RES) return null
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context?.getDrawable(resource.id)
        } else {
            context?.resources?.getDrawable(resource.id)
        }
    }

    actual object Default : OmegaResourceExtractor() {

        override var context: Context? = null

    }

    private class Local(context: Context) : OmegaResourceExtractor() {

        init {
            contextReference = WeakReference(context)
        }

    }

    class ResourceExtractorContentProvider : ContentProvider() {

        override fun attachInfo(context: Context?, info: ProviderInfo?) {
            super.attachInfo(context, info)
            Default.context = context
        }

        override fun insert(uri: Uri, values: ContentValues?): Uri? = throw IllegalAccessException()

        override fun query(
            uri: Uri,
            projection: Array<String>?,
            selection: String?,
            selectionArgs: Array<String>?,
            sortOrder: String?
        ): Cursor? = throw IllegalAccessException()

        override fun onCreate(): Boolean = throw IllegalAccessException()

        override fun update(
            uri: Uri,
            values: ContentValues?,
            selection: String?,
            selectionArgs: Array<String>?
        ): Int = throw IllegalAccessException()

        override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int =
            throw IllegalAccessException()

        override fun getType(uri: Uri): String? = throw IllegalAccessException()

    }

}