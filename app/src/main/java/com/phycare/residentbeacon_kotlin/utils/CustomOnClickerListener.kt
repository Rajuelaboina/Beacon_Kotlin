package com.phycare.residentbeacon_kotlin.utils

import android.view.View

class CustomOnClickerListener : View.OnClickListener {
    override fun onClick(v: View?) {
        KeyBoardClose().closeKeyboard(v)
    }
}