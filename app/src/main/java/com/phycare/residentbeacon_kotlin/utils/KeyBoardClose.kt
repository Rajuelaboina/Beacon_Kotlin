package com.phycare.residentbeacon_kotlin.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class KeyBoardClose() {
    var view: View? = null
        fun closeKeyboard(rootView: View?) {
            view = rootView
            // this will give us the view
            // which is currently focus
            // in this layout


            // if nothing is currently
            // focus then this will protect
            // the app from crash
            if (view != null) {
                // now assign the system
                // service to InputMethodManager
                val manager =
                    view!!.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(view!!.windowToken, 0)
            }
        }

}
