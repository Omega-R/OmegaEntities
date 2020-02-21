package com.omega_r.libs.entities.tools

import android.view.View
import android.view.ViewTreeObserver
import java.lang.ref.WeakReference
import java.util.*

class ImageSizeExtractor<V : View>(target: V, callback: suspend (view: V) -> Unit) : ViewTreeObserver.OnPreDrawListener,
        View.OnAttachStateChangeListener {

    companion object {

        private val imageSizeExtractors = WeakHashMap<View, ImageSizeExtractor<*>>()


    }

    private val target: WeakReference<V> = WeakReference(target)
    private var callback: (suspend (view: V) -> Unit)? = callback

    init {
        imageSizeExtractors[target]?.cancel()
        imageSizeExtractors[target] = this

        target.addOnAttachStateChangeListener(this)
        if (target.windowToken != null) {
            onViewAttachedToWindow(target)
        }
    }

    override fun onPreDraw(): Boolean {
        val target = target.get() ?: return true
        val vto = target.viewTreeObserver
        if (!vto.isAlive) {
            return true
        }
        val width = target.width
        val height = target.height

        if (width <= 0 || height <= 0) {
            return true
        }

        target.removeOnAttachStateChangeListener(this)
        vto.removeOnPreDrawListener(this)
        this.target.clear()

//        ImageProcessors.current.launch {
//            callback?.invoke(target)
//        }

        return true
    }

    override fun onViewAttachedToWindow(view: View) {
        view.viewTreeObserver.addOnPreDrawListener(this)
    }

    override fun onViewDetachedFromWindow(view: View) {
        view.viewTreeObserver.removeOnPreDrawListener(this)
    }

    fun cancel() {
        callback = null

        val target = target.get() ?: return
        this.target.clear()

        target.removeOnAttachStateChangeListener(this)

        val vto = target.viewTreeObserver
        if (vto.isAlive) {
            vto.removeOnPreDrawListener(this)
        }

    }


}