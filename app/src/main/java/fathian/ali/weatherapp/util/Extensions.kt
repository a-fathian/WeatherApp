package fathian.ali.weatherapp.util

import android.view.View

fun View?.show() {
    this?.visibility = View.VISIBLE
}

fun View?.gone() {
    this?.visibility = View.GONE
}

fun View?.hide() {
    this?.visibility = View.INVISIBLE
}

fun View?.showGone(show: Boolean?) {
    if (show == null) return

    if (show) {
        this?.show()
    } else {
        this?.gone()
    }
}