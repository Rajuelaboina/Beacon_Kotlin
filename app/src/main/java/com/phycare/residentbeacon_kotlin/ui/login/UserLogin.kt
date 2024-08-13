package com.phycare.residentbeacon_kotlin.ui.login

import android.text.TextUtils
import androidx.databinding.BaseObservable
import java.util.regex.Pattern

data class UserLogin(var email: String?, var password: String?) : BaseObservable() {

   fun isValidUser():Int{
       return if (TextUtils.isEmpty(email)) {
           0 // username is empty
       } else if (!isVaildEmail()) {
           1 // username is incorrect
       } else if (TextUtils.isEmpty(password)) {
           2  // password is incorrect
       } /*else if (getPassword().length() <= 4){
           return 3;
       }*/ else {
           -1 //success
       }

    }
    fun isVaildEmail(): Boolean {
        val emailPattern = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        val pattern = Pattern.compile(emailPattern)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
}