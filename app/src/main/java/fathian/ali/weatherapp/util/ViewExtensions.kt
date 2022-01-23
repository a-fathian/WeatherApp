package fathian.ali.weatherapp.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View?.hideKeyboard() {
    try {
        val imm = this?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(this?.windowToken, 0)
    } catch (ignored: Exception) {
    }
}

fun View?.openKeyboard() {
    try {
        val imm = this?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        this?.requestFocus()

        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

    } catch (ignored: Exception) {
    }
}