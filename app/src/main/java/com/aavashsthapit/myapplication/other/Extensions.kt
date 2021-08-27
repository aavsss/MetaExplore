package com.aavashsthapit.myapplication.other

import android.graphics.Rect
import android.view.View
import androidx.core.widget.NestedScrollView

object Extensions {

    fun NestedScrollView.isViewVisible(view: View): Boolean {
        val scrollBounds = Rect()
        this.getDrawingRect(scrollBounds)
        var top = 0f
        var temp = view
        while (temp !is NestedScrollView) {
            top += (temp).y
            temp = temp.parent as View
        }
        val bottom = top + view.height
        return scrollBounds.top < top && scrollBounds.bottom > bottom
    }

    fun View.visible() {
        this.visibility = View.VISIBLE
    }

    fun View.invisible() {
        this.visibility = View.INVISIBLE
    }

    fun View.gone() {
        this.visibility = View.GONE
    }
}
